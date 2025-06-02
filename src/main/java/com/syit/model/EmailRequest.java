package com.syit.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//sender, recipient, message, subject
	private String recipientEmail;
	private String message;
	private String subject;
	
}
