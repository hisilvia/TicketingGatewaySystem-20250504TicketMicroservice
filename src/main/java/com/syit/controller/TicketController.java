package com.syit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.syit.service.TicketService;

import jakarta.persistence.EntityNotFoundException;

@RestController    //if using @fController -->spring-circular-view-path-error on path /hotelPost
@RequestMapping(value="/ticket")
public class TicketController {

    @Autowired
	TicketService ticketService;
	
	@RequestMapping(value="/ticketPost", method=RequestMethod.POST)
	public ResponseEntity<String> hotelResultPost(@RequestBody JsonNode node) {

		System.out.println("TicketMicro-TicketController->node: " + node);
		
		ticketService.save(node);
		
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


