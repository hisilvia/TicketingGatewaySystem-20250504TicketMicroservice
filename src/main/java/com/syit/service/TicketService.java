package com.syit.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.syit.domain.Ticket;
import com.syit.domain.TicketHistory;
import com.syit.model.EmailRequest;
import com.syit.repository.TicketHistoryRepository;
import com.syit.repository.TicketRepository;

import jakarta.transaction.Transactional;


@Service
public class TicketService {

	@Autowired
	TicketRepository ticketRepo;
	    
	@Autowired
	TicketHistoryRepository ticketHistoryRepo;
	
	@Autowired
	EmailService emailService;
	
	public void save(JsonNode node) {
		Ticket ticket = new Ticket();
		
		//The asText() method returns the text value of a JsonNode as a String
		ticket.setTitle(node.get("title").asText());
		ticket.setDescription(node.get("description").asText());
		ticket.setCategory(node.get("category").asText().toUpperCase());	
		ticket.setPriority(node.get("priority").asText().toUpperCase());
		ticket.setStatus(node.get("status").asText());
		ticket.setCreatedBy(node.get("createdBy").asText());
		ticket.setAssignee(node.get("assignee").asText());
		ticket.setAssignee(null);
		
		
		//set current date and time
		ticket.setCreationDate(new Date());
		TicketHistory tHistory = new TicketHistory(); 
		tHistory.setActionDate(new Date());
		/*
		// Set specific date and time using SimpleDateFormat
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        
        try {
        	Date date = formatter.parse(node.get("creationDate").asText());
            ticket.setCreationDate(date);
            tHistory.setActionDate(date);
            System.out.println(ticket.getCreationDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        */
		
		
		
		//check file path
		if(node.has("fileAttachementPath") && node.get("fileAttachementPath").isArray()) {
			List<String> filePaths = new ArrayList<>();
			node.get("fileAttachementPath").forEach(nd -> filePaths.add(nd.asText()));
			ticket.setFileAttachementPath(filePaths);
		}
		
		ticketRepo.save(ticket);
		
		tHistory.setTicket(ticket);
		tHistory.setAction("CREATED");
		tHistory.setActionBy(node.get("createdBy").asText());
		tHistory.setComments("user created a ticket.");
		ticketHistoryRepo.save(tHistory);
	
	}
	
	public List<Ticket> findAll(){
		return ticketRepo.findAll();
	}
	
	public List<Ticket> findTicketsByCreatedBy(String name){
		return ticketRepo.findByCreatedBy(name);
	}
	
	public List<Ticket> findByStatus(String status){
		return ticketRepo.findByStatus(status);
	}
	
	public Ticket findById(long tId) {
		Optional<Ticket> ticket = ticketRepo.findById(tId);
		
		if(ticket.isPresent())
			return ticket.get();
		else
			throw new RuntimeException("Did not find Ticket_id - " + tId);	
	}
	
	public Ticket findByTitle(String title) {
		return ticketRepo.findByTitle(title);
	}
	
	public String deleteById(long tId) {
		ticketRepo.deleteById(tId);
		return "Deleted ticketId: "+tId;
	}
	
	//update
	/*
	public Ticket updateTicket(Ticket ticket) {
		Ticket existingTicket = ticketRepo.findById(ticket.getId()).orElseThrow(() -> new RuntimeException("Ticket not found"));
		
		//update -->must have several set
		existingTicket.setTitle(ticket.getTitle());
		existingTicket.setDescription(ticket.getDescription());
		existingTicket.setCategory(ticket.getCategory());
		existingTicket.setPriority(ticket.getPriority());
		existingTicket.setStatus(ticket.getStatus());
		existingTicket.setAssignee(ticket.getAssignee());
		return ticketRepo.save(existingTicket);
	}
	*/
	
	public void updateTicket(JsonNode node) {
		Ticket existingTicket = ticketRepo.findById(node.get("id").asLong()).orElseThrow(() -> new RuntimeException("Ticket not found"));
	
		//existingTicket.setPriority(node.get("priority").asText().toUpperCase());
		existingTicket.setStatus(node.get("status").asText());
		if(node.get("assignee")==null)
			existingTicket.setAssignee(null);
		else 
			existingTicket.setAssignee(node.get("assignee").asText());
	
		ticketRepo.save(existingTicket);
		
	}

	public void updateTicketHistory(JsonNode node) {
		Ticket ticket = ticketRepo.findById(node.get("id").asLong()).orElseThrow(() -> new RuntimeException("Ticket not found"));
		
		//Add update ticket info to TicketHistory
		TicketHistory history = new TicketHistory();
		history.setTicket(ticket);
	
		String status = node.get("status").asText().toUpperCase();
		history.setAction(status);
		
		if(node.get("actionBy") == null)
			history.setActionBy(null);
		else
			history.setActionBy(node.get("actionBy").asText());
		
		history.setActionDate(new Date());
		//Add comments
		if(status.equals("APPROVED"))
			history.setComments("Ticket " + ticket.getId() +" was approved by manager.");
				
		if(status.equals("REJECTED"))
			history.setComments("Ticket " + ticket.getId() +" was  rejected by manager");
		
		if(status.equals("RESOLVED"))
			history.setComments("Ticket " + ticket.getId() +" was resolved by admin");
		
		if(status.equals("REOPENED"))
			history.setComments("Ticket " + ticket.getId() +" was reopended by user");
		
		if(status.equals("CLOSED"))
			history.setComments("Ticket " + ticket.getId() +" was closed");
		
		if(status.equals("ASSIGNED"))
			history.setComments("Ticket " + ticket.getId() +" was assigned");
		
		ticketHistoryRepo.save(history);
	
	}
	
	public List<TicketHistory> getTicketHistoryById(long id){
		return ticketHistoryRepo.findByTicketId(id);
	}
	
	//do CRON job: Check tickets pending >7 days==>I check CREATE instead of pending
	public void checkPendingTickets() {
		LocalDateTime sevenDaysAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
		List<Ticket> pendingTickets = ticketRepo.findByStatusAndCreationDateBefore("CREATE", sevenDaysAgo);
		
		EmailRequest er = new EmailRequest();
		if(!pendingTickets.isEmpty()) {
			//send an email to manager	
			er.setRecipientEmail("silviajava4@gmail.com");  //assume manager's email
			er.setSubject("Pending ticket over 7 days.") ;
			er.setMessage("Please take care of pending ticket soon.");
			emailService.sendTextEmail(er);
	
			System.out.println("Found " + pendingTickets.size() + " pending tickets older than 7 days.");
		}
	}
	
	//do CRON job: Check action and actionDate in ticketHistory->auto-close if
	@Transactional   //==>allow lazy collection to be retrieved from the db, otherwise have to keep the session open till the end of the execution.
	public void checkActionDateFiveDaysAgo() {
		
		LocalDate fiveDaysAgo = LocalDate.now().minusDays(5);
		List<Ticket> resolvedTickets = ticketRepo.findByStatus("RESOLVED");
		//System.out.println("resolvedTickets: "+resolvedTickets);
		EmailRequest er = new EmailRequest();
		TicketHistory tt = new TicketHistory();
		if(!resolvedTickets.isEmpty()) {
			for(int i=0; i<resolvedTickets.size(); i++) {
				List<TicketHistory> th = resolvedTickets.get(i).getHistory();
				System.out.println("resolvedTickets.get("+i +").getId(): "+resolvedTickets.get(i).getId());
				//System.out.println("th.getLast()"+th.g);

				for(TicketHistory t : th) {
					
					
					if(!t.getAction().contains("AUTO-CLOSE")) {
						//Need to convert Date to LocalDate
						System.out.println("t.getActionDate: "+t.getActionDate());
						
						//1.Convert Date to Instance
						Instant instant = t.getActionDate().toInstant();
						
						//2.Convert Instant to ZonedDateTime using the system's default time zone
				        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());

				        //3.Extract LocalDate from ZonedDateTime
				        LocalDate localDate = zdt.toLocalDate();
				        
						if(t.getAction().contains("RESOLVED") && localDate.isBefore(fiveDaysAgo)) {
							System.out.println("Ticket ID: "+resolvedTickets.get(i).getId()+"TicketHistory ID"+t.getId());
							System.out.println("The target date is exactly 5 days ago.");
							//Set the status to close
							resolvedTickets.get(i).setStatus("AUTO-CLOSE");
							tt.setTicket(resolvedTickets.get(i));
							tt.setAction("AUTO-CLOSE");
							tt.setActionDate(new Date());
							tt.setComments("Ticket "+resolvedTickets.get(i).getId()+" was closed automatically.");
							tt.setActionBy(t.getActionBy());
							
							
							ticketHistoryRepo.save(tt);
							//Send an email
							er.setRecipientEmail("silviajava4@gmail.com");  //assume manager's email
							er.setSubject("Resolved Ticket is closed automatically.") ;
							er.setMessage("The resolved ticket id: " + resolvedTickets.get(i).getId() + " closed automatically");
							emailService.sendTextEmail(er);
					
							//System.out.println("Found " + resolvedTickets.size() + " resolved-tickets closed automatically.");
						}
					}
					
				}
			}
			
		}
		
		
		//List<TicketHistory> resolvedTickets = ticketHistoryRepo.findByActionAndActionDateBefore("RESOLVED", fiveDaysAgo);
		/*
		System.out.println(resolvedTickets.get(0));
		EmailRequest er = new EmailRequest();
		if(!resolvedTickets.isEmpty()) {
			System.out.println("resolvedTickets.get(0): "+ resolvedTickets.get(0));
			//send an email to manager	
			er.setRecipientEmail("silviajava4@gmail.com");  //assume manager's email
			er.setSubject("Resolved Ticket is closed automatically.") ;
			er.setMessage("Please take care of pending ticket soon.");
			emailService.sendTextEmail(er);
	
			System.out.println("Found " + resolvedTickets.size() + " resolved-tickets closed automatically.");
		}
		*/
	}
	
	
	
	
	
}

/*JsonNode provides numerous methods for inspecting and extracting data.
1.isXxx() methods:
  These methods check the node type, such as isObject(), isArray(), isTextual(), isNumber(), isNull(), 
  ->isMissingNode() and isBinary().

2.get(index/fieldName):
  Retrieves a child node by index (for arrays) or field name (for objects). It returns null 
  ->if the child does not exist or a "missing node" if using path().

3.path(index/fieldName):
  Similar to get(), but returns a "missing node" instead of null if the child is not found. 
  ->This enables safe chaining of calls.

4.has(fieldName) / hasNonNull(fieldName):
  Checks if an object node contains a field, with hasNonNull also ensuring the value is not null.

5.size():
  Returns the number of child nodes (elements in an array or fields in an object).

6.asText(), asInt(), asLong(), asDouble(), asBoolean():
  Attempts to convert the node's value to the specified data type. They may have default value options.

7.findValue(fieldName):
  Searches for a specific field and returns the associated JsonNode or null if not found.
*/