package com.syit.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.syit.domain.Ticket;
import com.syit.domain.TicketHistory;
import com.syit.repository.TicketHistoryRepository;
import com.syit.repository.TicketRepository;


@Service
public class TicketService {

	@Autowired
	TicketRepository ticketRepo;
	
	@Autowired
	TicketHistoryRepository ticketHistoryRepo;
	/*
	public Ticket save(Ticket t) {
		return ticketRepo.save(t);
	}
	*/
	public void save(JsonNode node) {
		Ticket ticket = new Ticket();
		
		//The asText() method returns the text value of a JsonNode as a String
		ticket.setTitle(node.get("title").asText());
		ticket.setDescription(node.get("description").asText());
		ticket.setCreatedBy(node.get("createdBy").asText());
		ticket.setAssignee(node.get("assignee").asText());
		ticket.setPriority(node.get("priority").asText().toUpperCase());
		ticket.setStatus("OPEN");
		
		//set current date and time
		//ticket.setCreationDate(new Date());
		
		// Set specific date and time using SimpleDateFormat
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        TicketHistory tHistory = new TicketHistory(); 
        try {
        	Date date = formatter.parse(node.get("creationDate").asText());
            ticket.setCreationDate(date);
            tHistory.setActionDate(date);
            System.out.println(ticket.getCreationDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
		
		ticket.setCategory(node.get("category").asText().toUpperCase());
		
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
	
	public Ticket findById(long tId) {
		Optional<Ticket> ticket = ticketRepo.findById(tId);
		
		if(ticket.isPresent()) {
			return ticket.get();
		}else
			throw new RuntimeException("Did not find Ticket_id - " + tId);
		
	}
	
	public Ticket findByTitle(String title) {
		return ticketRepo.findByTitle(title);
	}
	
	public String deleteById(long tId) {
		ticketRepo.deleteById(tId);
		return "Deleted ticketId: "+tId;
	}
}

/*

*/
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