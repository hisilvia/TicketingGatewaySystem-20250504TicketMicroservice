package com.syit.repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.syit.domain.Ticket;
import com.syit.domain.TicketHistory;
import com.syit.model.EmailRequest;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Repository
public class EmailRepository {

	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
	TicketRepository ticketRepo;
	
	@Autowired
	TicketHistoryRepository ticketHistoryRepo;
	
	@Value("${spring.mail.username}")
	private String senderEmail;
	
	//Only send simple text
	public void sendSimpleEmail(EmailRequest request) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(senderEmail);
		message.setTo(request.getRecipientEmail());
		message.setSubject(request.getSubject());
		message.setText(request.getMessage());
		emailSender.send(message);
		//log.info("{} is expecting your email.");
	}
	
	//*******************************
	//Send email with PDF ->Call #1 and #2
	public String generateAndSendWithPdf(long id, EmailRequest er, String pdfSavePath) throws IOException, MessagingException {
		
		String filePath = pdfSavePath + "/ticket_history_pdf.pdf";
		
		generateResolutionAndSavePdf(id, filePath);
		
		sendEmailWithAttachment(er, filePath);
		
		return "PDF generated and email sent successfully!";
	}
	
	//1.GeneratePDFAndSaveIt
	public String generateResolutionAndSavePdf(long id, String filePath) throws IOException {
		Document document = new Document();
		FileOutputStream fileOutputStream = new FileOutputStream(filePath);
		
		Optional<Ticket> ticket = ticketRepo.findById(id);
		//ticket.get().getHistory();
		//List<TicketHistory> ticketHis = ticketHistoryRepo.findByTicketId(id);
		
		
		if(ticket.isPresent()) {
			try {
				PdfWriter.getInstance(document, fileOutputStream);
				
				document.addSubject("This is a resolved ticket.");
				document.open();
				
				//Add Ticket Information
				document.add(new Paragraph("Ticket ID: "+ticket.get().getId()));
				document.add(new Paragraph("Title: "+ticket.get().getTitle()));
				document.add(new Paragraph("Description: "+ticket.get().getDescription()));
				document.add(new Paragraph("CreatedBy: "+ticket.get().getCreatedBy()));
				document.add(new Paragraph("Priority: "+ticket.get().getPriority()));
				document.add(new Paragraph("Category: "+ticket.get().getCategory()));
				document.add(new Paragraph("CreationDate: "+ticket.get().getCreationDate()));
				document.add(new Paragraph("\n\n"));
				
				
				int i=0;
				document.add(new Paragraph("Ticket History: "));
				document.add(new Paragraph("\n"));
				//Add Ticket History information in a table
				
				if(ticket.get().getHistory() != null && !ticket.get().getHistory().isEmpty()) {
					PdfPTable table = new PdfPTable(4);   //4 is a number of columns
					tableHeader(table);
					
					for(TicketHistory ts : ticket.get().getHistory()) {
						table.addCell(ts.getAction());
						table.addCell(ts.getActionBy());
						table.addCell(String.valueOf(ts.getActionDate()));
						table.addCell(ts.getComments());
					}
					document.add(table);
				}else {
		            document.add(new Paragraph("No specific history found for this ticket."));
		        }
				
				document.close();   //Must close it. Otherwise not able to open it since the file is already open it!
				fileOutputStream.close();
				
			}catch(DocumentException e) {
				e.printStackTrace();
			}
		}
		
		
		return filePath;
	}

	private void tableHeader(PdfPTable table) {
		Stream.of("Action", "ActionBy", "ActionDate", "Comments").forEach(title->{
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.CYAN);
			header.setBorderWidth(1);
			header.setPhrase(new Phrase(title));
			table.addCell(header);
		});
		
	}
	
	//2.Send the email with the generated PDF as attachment
	public void sendEmailWithAttachment(EmailRequest er, String filePath) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true); //have to set true or wont send it

	    // Set email details
	    helper.setFrom(senderEmail);
	    helper.setTo(er.getRecipientEmail());
	    helper.setSubject(er.getSubject());
	    helper.setText(er.getMessage());

	    // Attach the PDF file
	    File pdfFile = new File(filePath);
	    helper.addAttachment(pdfFile.getName(), pdfFile);

	    emailSender.send(message);
	}

	
}
