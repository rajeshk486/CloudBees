package com.ticket.reservation.Service.Impl;

import com.ticket.reservation.Enums.Section;
import com.ticket.reservation.Repository.TicketRepository;
import com.ticket.reservation.Repository.UserRepository;
import com.ticket.reservation.Service.TicketService;
import com.ticket.reservation.model.ModifySeat;
import com.ticket.reservation.model.RemoveUser;
import com.ticket.reservation.model.Ticket;
import com.ticket.reservation.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public Ticket buyTicket(Ticket ticket) {
        return getTicket(ticket, ticket.getUser());
    }

    @Override
    public Ticket getTicketById(Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if(ticket.isPresent())
            return ticket.get();
        return null;
    }

    @Override
    public boolean deleteTickets(long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if(ticket.isPresent())
        {
            ticketRepository.deleteById(id);
            return  true;
        }
        return false;
    }

/*    @Override
    public boolean deleteTickets(RemoveUser removeUser) {
        Optional<User> user = userRepository.findByEmail(removeUser.getEmail());
        //this will delete only the ticket
        if(Optional.ofNullable(removeUser.getTicketId()).isPresent())
        {
            ticketRepository.deleteById(removeUser.getTicketId());
        }
        //this will delete al the tickets reserved by the user
        if(user.isPresent()) {
            user.get().getTickets().stream().forEach(ticket -> ticketRepository.deleteById(ticket.getTicketId()));
            userRepository.delete(user.get());
            return true;
        }
        return false;
    }*/


    @Override
    public Ticket changeSeat(ModifySeat seat) {
        Optional<Ticket> ticket = ticketRepository.findById(seat.getTicketId());
        if(ticket.isEmpty() ||
                ticketRepository.findBySectionAndSeat(seat.getSection(), seat.getSeat())!=null)
            return null;
        Ticket modifiedTicket = ticket.get();
        modifiedTicket.setSection(seat.getSection());
        modifiedTicket.setSeat(seat.getSeat());
        return ticketRepository.save(modifiedTicket);
    }

    @Override
    public List<Ticket> getTicketsBySection(Section section) {
        return ticketRepository.findBySection(section);
    }

    Ticket getTicket(Ticket ticket, User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if(optionalUser.isEmpty())
            user = userRepository.save(user);
        else
            user = optionalUser.get();
        ticket.setUser(user);
        int seat = getSeatNumber();
        Section section = getSection();
        ticket.setSeat(seat);
        ticket.setSection(section);
        return ticketRepository.save(ticket);
    }
    private Section getSection() {
        List<String> sections = new ArrayList<>();
        for (Section section : Section.values()) {
            sections.add(section.name());
        }
        if (sections == null || sections.isEmpty()) {
            throw new IllegalArgumentException("Sections list cannot be null or empty");
        }
        Random random = new Random();
        int randomIndex = random.nextInt(sections.size());
        return Section.valueOf(sections.get(randomIndex));
    }
    private int getSeatNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000) + 1; // Ensures range 1-100 (inclusive)
        return randomNumber;
    }
}
