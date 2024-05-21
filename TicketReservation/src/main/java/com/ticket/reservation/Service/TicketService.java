package com.ticket.reservation.Service;

import com.ticket.reservation.Enums.Section;
import com.ticket.reservation.model.ModifySeat;
import com.ticket.reservation.model.RemoveUser;
import com.ticket.reservation.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketService {
    Ticket buyTicket(Ticket ticket);
    Ticket getTicketById(Long id);

    boolean deleteTickets(long id);
    Ticket changeSeat(ModifySeat seat);

    List<Ticket> getTicketsBySection(Section valueOf);
}
