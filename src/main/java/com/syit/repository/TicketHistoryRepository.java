package com.syit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.syit.domain.TicketHistory;

@Repository
public interface TicketHistoryRepository extends JpaRepository<TicketHistory, Long>{

	@Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM TicketHistory t WHERE t.id = :id")
	//@Query("DELETE FROM ticket_gateway.ticket_history t WHERE t.id = :id")  not right.
	public String deleteById(long id);
	
	@Query("SELECT tHis FROM TicketHistory tHis WHERE tHis.ticket.id = :ticketId")
	List<TicketHistory> findByTicketId(@Param("ticketId") Long ticketId);
}

