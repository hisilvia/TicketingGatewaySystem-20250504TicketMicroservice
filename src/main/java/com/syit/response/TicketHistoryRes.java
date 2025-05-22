package com.syit.response;

import java.util.Date;

public class TicketHistoryRes {

	private long id;
	private TicketRes ticket;
	private String action;  //CREATED, APPROVED, REJECTED, ASSIGNED, RESOLVED, CLOSED, REOPENED
	//private Employee actionBy;
	private String actionBy;
	private Date actionDate;
	private String comments;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TicketRes getTicket() {
		return ticket;
	}

	public void setTicket(TicketRes ticket) {
		this.ticket = ticket;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActionBy() {
		return actionBy;
	}

	public void setActionBy(String actionBy) {
		this.actionBy = actionBy;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	
	
	
}
