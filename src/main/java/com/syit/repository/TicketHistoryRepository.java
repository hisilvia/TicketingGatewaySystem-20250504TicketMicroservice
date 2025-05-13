package com.syit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.syit.domain.TicketHistory;

@Repository
public interface TicketHistoryRepository extends JpaRepository<TicketHistory, Long>{

}
