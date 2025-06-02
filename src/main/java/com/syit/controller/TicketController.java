package com.syit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syit.domain.Ticket;
import com.syit.domain.TicketHistory;
import com.syit.model.EmailRequest;
import com.syit.service.EmailService;
import com.syit.service.TicketService;

import jakarta.persistence.EntityNotFoundException;

@RestController    //if using @fController -->spring-circular-view-path-error on path /hotelPost
@RequestMapping(value="/ticket")
public class TicketController {

	@Value("${pdf.save.path}")
	private String pdfSavePath;
	
    @Autowired
	TicketService ticketService;
    
    @Autowired
	EmailService emailService;
	
	@RequestMapping(value="/ticketPost", method=RequestMethod.POST)
	public ResponseEntity<String> hotelResultPost(@RequestBody JsonNode node) {

		System.out.println("TicketMicro-TicketController->node: " + node);
		EmailRequest er = new EmailRequest();
		er.setRecipientEmail("silviajava4@gmail.com");  //assume it's manager's email
		er.setSubject("New ticket need to be approved.") ;
		er.setMessage("Please approve the ticket.");
	
		ticketService.save(node);
		emailService.sendTextEmail(er);
		
		//ObjectNode resNode = objMapper.createObjectNode();
		//resNode.put("UniqueId", 101);
		
		System.out.println(node.get("title"));
		System.out.println(node.get("description"));
		System.out.println(node.get("category"));
		

		return ResponseEntity.ok("Ticket is posted successfully");
	}

	
	@RequestMapping(value="/ticketGetByName/{name}", method=RequestMethod.GET)
	public List<Ticket> ticketGetByCreatedBy(@PathVariable String name){
		return ticketService.findTicketsByCreatedBy(name);
	}
	
	@RequestMapping(value="/ticketGetAll", method=RequestMethod.GET)
	public List<Ticket> ticketGetAll() {	
		return ticketService.findAll();
	}
	
	
	@RequestMapping(value="/ticketGetByStatus/{status}", method=RequestMethod.GET)
	public List<Ticket> ticketGetByStatus(@PathVariable String status){
		return ticketService.findByStatus(status);
	}

	@RequestMapping(value="/ticketGetById/{id}", method=RequestMethod.GET)
	public Ticket ticketGetById(@PathVariable long id) {
		return ticketService.findById(id);
	}
	
	
	@RequestMapping(value="/ticketGet/{title}", method=RequestMethod.GET)
	public Ticket ticketGet(@PathVariable String title) {	
		return ticketService.findByTitle(title);
	}
	
	/*
	@RequestMapping(value="/updateTicket", method=RequestMethod.PUT)
	public ResponseEntity<?> updateTicketById(@RequestBody Ticket updateTicket) {
		try {
			Ticket result = ticketService.updateTicket(updateTicket);
			return ResponseEntity.ok(result);
		}catch(EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating entity");
        }
	}
	*/
	
	@RequestMapping(value="/updateTicket", method=RequestMethod.POST)
	public ResponseEntity<String> updateTicketById(@RequestBody JsonNode node) {
		try {
			ticketService.updateTicket(node);
			ticketService.updateTicketHistory(node);
			
			String status = node.get("status").asText();
			EmailRequest er = new EmailRequest();
			if(status.equals("APPROVED")) {	
				
				er.setRecipientEmail("silviajava4@gmail.com");  //assume admin's email
				er.setSubject("New approved ticket need to be assigned.") ;
				er.setMessage("Please assign the ticket.");
				emailService.sendTextEmail(er);
			}else if(status.equals("REJECTED")) {
				
				er.setRecipientEmail("silviamatthews2018@gmail.com");  //assume user's email
				er.setSubject("The ticket was rejected.") ;
				er.setMessage("Sorry, your ticket was rejected. Please read the information, then post it again.");
				emailService.sendTextEmail(er);
			}else if(status.equals("RESOLVED")) {
				
				er.setRecipientEmail("silviajava4@gmail.com");  //assume user's email
				er.setSubject("The ticket was resolved.") ;
				er.setMessage("Please check the ticket in detail.");
				
				try {
					//Generate pdf and send it
					String result = emailService.sendResolutionWithPdf(node.get("id").asLong(), er, pdfSavePath);
					System.out.println("Result: "+result);
				}catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
				
			}
			//emailService.sendTextEmail(er);
	
			return ResponseEntity.ok("Ticket is updated.");
		}catch(EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } 
		//catch (Exception e) {
        //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating entity");
        //}
	}
	
	@RequestMapping(value="/deleteTicketById/{id}", method=RequestMethod.DELETE)
	public void deleteTicketById(@PathVariable long id) {
		ticketService.deleteById(id);
	}
	
	@RequestMapping(value="/ticketHistoryPost", method=RequestMethod.POST)
	public ResponseEntity<String> ticketHistoryPost(@RequestBody long id) {	
		List<TicketHistory> allTicketHistory = ticketService.getTicketHistoryById(id);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String json = objectMapper.writeValueAsString(allTicketHistory);
			return ResponseEntity.ok(json);
		} catch (Exception e) {
			return ResponseEntity.noContent().build();
		}
	}
	
	@RequestMapping(value="/ticketHistoryGetById/{id}", method=RequestMethod.GET)
	public ResponseEntity<List<TicketHistory>> ticketHistoryGet(@PathVariable long id) {	
		List<TicketHistory> allTicketHistory = ticketService.getTicketHistoryById(id);
		return ResponseEntity.ok(allTicketHistory);
	}
	
}
//https://www.baeldung.com/java-jsonnode-astext-vs-tostring
/* In Spring Boot, JsonNode and ResponseEntity<String> serve different purposes when handling HTTP responses.

1.JsonNode:
   Represents a tree-like structure of a JSON document. It is useful for parsing and manipulating JSON data 
    when the structure is unknown or dynamic. When returned from a controller method, Spring will serialize
    the JsonNode to JSON in the response body. It offers flexibility in handling varied JSON structures 
    but lacks control over the HTTP status code and headers.

2.ResponseEntity<String>:
   Encapsulates the entire HTTP response, including the body, headers, and status code. The <String> type 
    parameter indicates that the response body is a string, often containing JSON. Using ResponseEntity provides 
    fine-grained control over the response, allowing you to set custom status codes (e.g., 200 OK, 404 Not Found), 
    add headers, and return a string representation of JSON.

In essence, JsonNode is for handling JSON data, while ResponseEntity<String> is for constructing 
complete HTTP responses with control over status and headers. When you need to customize the HTTP response 
beyond just the JSON body, ResponseEntity<String> is the preferred choice.

*/


