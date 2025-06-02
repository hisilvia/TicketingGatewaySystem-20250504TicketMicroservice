package com.syit.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syit.model.EmailRequest;
import com.syit.repository.EmailRepository;

import jakarta.mail.MessagingException;


@Service
public class EmailService {

	@Autowired
	EmailRepository emailRepo;
	
	public String sendTextEmail(EmailRequest request) {
		emailRepo.sendSimpleEmail(request);
		
		return "Send the text body successfully.";
	}
	
	public String sendResolutionWithPdf(long id, EmailRequest er, String pdfSavePath) throws IOException, MessagingException {
		return emailRepo.generateAndSendWithPdf(id, er, pdfSavePath);
			 
	}
}
