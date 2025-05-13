package com.syit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.syit.domain.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{

	Ticket findByTitle(String title);
}
