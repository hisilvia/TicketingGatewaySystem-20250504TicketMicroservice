package com.syit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.syit.domain.TicketHistory;

@Repository
public interface TicketHistoryRepository extends JpaRepository<TicketHistory, Long>{

	@Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM TicketHistory t WHERE t.id = :id")
	public String deleteById(long id);
}
