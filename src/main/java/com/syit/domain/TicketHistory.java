package com.syit.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="ticket_history")
public class TicketHistory {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne    //one ticket can have multiple histories.
	@JoinColumn(name = "ticket_id")
	@JsonBackReference   //Important->without it, there are many ticket.history in table ticket;
	private Ticket ticket;
	private String action;  //CREATED, APPROVED, REJECTED, ASSIGNED, RESOLVED, CLOSED, REOPENED
	//private Employee actionBy;
	private String actionBy;
	
	@Column(name = "action_date", nullable = false, updatable = false)
	private Date actionDate;
	private String comments;
	
	public TicketHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TicketHistory(long id, Ticket ticket, String action, String actionBy, Date actionDate, String comments) {
		super();
		this.id = id;
		this.ticket = ticket;
		this.action = action;
		this.actionBy = actionBy;
		this.actionDate = actionDate;
		this.comments = comments;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
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

	@Override
	public String toString() {
		return "TicketHistory [id=" + id + ", ticket=" + ticket + ", action=" + action + ", actionBy=" + actionBy
				+ ", actionDate=" + actionDate + ", comments=" + comments + "]";
	}
	
	
	
}
