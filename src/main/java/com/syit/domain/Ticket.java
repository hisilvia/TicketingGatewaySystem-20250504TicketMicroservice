package com.syit.domain;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="tickets")
public class Ticket {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String title;
	private String description;
	//private Employee createdBy;
	//private Employee assignee;
	
	private String createdBy;
	private String assignee;
	private String priority;     // LOW, MEDIUM, HIGH
	private String status;       //OPEN, PENDING_APPROVAL, APPROVED, REJECTED, 
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date creationDate;
	private String category;
	private List<String> fileAttachementPath;
	
	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
	private List<TicketHistory> history;
	
	public Ticket() {
		super();
	}

	public Ticket(long id, String title, String description, String createdBy, String assignee, String priority,
			String status, Date creationDate, String category, List<String> fileAttachementPath,
			List<TicketHistory> history) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.createdBy = createdBy;
		this.assignee = assignee;
		this.priority = priority;
		this.status = status;
		this.creationDate = creationDate;
		this.category = category;
		this.fileAttachementPath = fileAttachementPath;
		this.history = history;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getFileAttachementPath() {
		return fileAttachementPath;
	}

	public void setFileAttachementPath(List<String> fileAttachementPath) {
		this.fileAttachementPath = fileAttachementPath;
	}

	public List<TicketHistory> getHistory() {
		return history;
	}

	public void setHistory(List<TicketHistory> history) {
		this.history = history;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", title=" + title + ", description=" + description + ", createdBy=" + createdBy
				+ ", assignee=" + assignee + ", priority=" + priority + ", status=" + status + ", creationDate="
				+ creationDate + ", category=" + category + ", fileAttachementPath=" + fileAttachementPath
				+ ", history=" + history + "]";
	}
	
	
	
}
