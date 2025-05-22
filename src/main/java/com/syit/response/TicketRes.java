package com.syit.response;

import java.util.Date;
import java.util.List;



public class TicketRes {
	
	private long id;
	private String title;
	private String description;
	
	//private Employee createdBy;
	//private Employee assignee;
	private String createdBy;
	private String assignee;
	
	private String priority;     // LOW, MEDIUM, HIGH
	private String status;       //OPEN, PENDING_APPROVAL, APPROVED, REJECTED, 
	
	
	private Date creationDate;
	private String category;
	private List<String> fileAttachementPath;
	
	
	
	private List<TicketHistoryRes> history;
	
	
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

	public List<TicketHistoryRes> getHistory() {
		return history;
	}

	public void setHistory(List<TicketHistoryRes> history) {
		this.history = history;
	}

	/*
	@Override
	public String toString() {
		return "Ticket [title=" + title + ", description=" + description + ", createdBy=" + createdBy
				+ ", assignee=" + assignee + ", priority=" + priority + ", status=" + status + ", creationDate="
				+ creationDate + ", category=" + category + ", fileAttachementPath=" + fileAttachementPath
				+ ", history=" + history + "]";
	}
	*/
	
	
}
