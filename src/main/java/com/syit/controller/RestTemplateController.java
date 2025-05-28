package com.syit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syit.domain.Ticket;
import com.syit.service.RestTemplateService;

@RestController
@RequestMapping("/restTemplate")
public class RestTemplateController {
	
	@Autowired
	RestTemplateService restTemplateService;
	
	@GetMapping("/getAllTickets")
	public ResponseEntity<String> getAllTickets(){	
		return restTemplateService.allTickets();	
	}
	
	@PostMapping("/addTicket")
	public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket){
		
		return restTemplateService.createTicket(ticket);
		
	}

}
