package com.ticket.reservation.Repository;

import com.ticket.reservation.Enums.Section;
import com.ticket.reservation.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findBySectionAndSeat(Section section, int seat);
    Ticket findByTicketIdAndFromAndTo(long ticketId, String from, String to);
    Ticket findByFromAndTo(String from, String to);
    List<Ticket> findBySection(Section section);
}