package com.syit.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.syit.service.TicketService;

@Component
public class ScheduledTasks {

	@Autowired
    private TicketService ticketService;

    @Scheduled(cron = "0 0 * * * ?") // Example: Run every day at midnight, 1st * means every hour
                                     // ? means any day of the week (0~6)
    public void checkTicketsPendingForMoreThanSevenDays() {
        ticketService.checkPendingTickets();
        System.out.println("check for 7 days.");
    }    
}
