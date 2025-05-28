package com.syit.service;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.syit.domain.Ticket;

@Service
public class RestTemplateService {

	private static final String getAllTicketsURL = "http://localhost:8383/ticket/ticketGetAll";
	private static final String createTicketURL = "http://localhost:8383/ticket/ticketPost";
	RestTemplate restTemplate = new RestTemplate();
	
	//Method to get all tickets
	public ResponseEntity<String> allTickets(){
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		//if you're using spring security or token
		//headers.add("Authorization", headerValue);
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<String> response =restTemplate.exchange(getAllTicketsURL, HttpMethod.GET, entity, String.class);
		
		return response;
	}
	
	public ResponseEntity<Ticket> createTicket(Ticket ticket){
		
		return restTemplate.postForEntity(createTicketURL, ticket, Ticket.class);
		
	}
}
