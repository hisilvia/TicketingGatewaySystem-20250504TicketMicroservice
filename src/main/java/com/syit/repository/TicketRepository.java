package com.syit.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.syit.domain.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{

	Ticket findByTitle(String title);
	List<Ticket> findByCreatedBy(String name);
	List<Ticket> findByStatus(String status);
	//Ticket updateTicket(long id, Ticket ticket);
	
	
	@Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM Ticket t WHERE t.id = :id")
	public String deleteById(long id);
	
	//List<Ticket> findByStatusAndAssignee(String status, String name);

	List<Ticket> findByStatusAndCreationDateBefore(String status, LocalDateTime cutoff);
	
}
