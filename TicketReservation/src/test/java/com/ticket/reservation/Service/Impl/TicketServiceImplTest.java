package com.ticket.reservation.Service.Impl;

import com.ticket.reservation.Enums.Section;
import com.ticket.reservation.Repository.TicketRepository;
import com.ticket.reservation.Repository.UserRepository;
import com.ticket.reservation.model.Ticket;
import com.ticket.reservation.model.User;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class TicketServiceImplTest {
    @Mock
    private TicketRepository ticketRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testBuyTicket_Success() throws Exception {
        Ticket ticket = createTestTicket();
        User user = createTestUser("user1@example.com");
        ticket.setUser(user);
        Mockito.when(userRepositoryMock.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(ticketRepositoryMock.save(ticket)).thenReturn(ticket);

        Ticket savedTicket = ticketService.buyTicket(ticket);

        assertNotNull(savedTicket);
        assertEquals(user.getEmail(), savedTicket.getUser().getEmail());
    }

    @Test
    void getTicketById() {
        Ticket ticket = createTestTicket();
        Mockito.when(ticketRepositoryMock.findById(1L)).thenReturn(Optional.of(ticket));
        Ticket savedTicket = ticketService.getTicketById(1L);
        assertNotNull(savedTicket);
    }
    @Test
    void getTicketById_failure() {
        Mockito.when(ticketRepositoryMock.findById(1L)).thenReturn(Optional.empty());
        Ticket savedTicket = ticketService.getTicketById(1L);
        assertNull(savedTicket);
    }

    @Test
    void deleteTickets() {
        Ticket ticket = createTestTicket();
        Mockito.when(ticketRepositoryMock.findById(1L)).thenReturn(Optional.of(ticket));
        boolean flag = ticketService.deleteTickets(1L);
        assertTrue(flag);
    }
    @Test
    void deleteTickets_failure() {
        Mockito.when(ticketRepositoryMock.findById(1L)).thenReturn(Optional.empty());
        boolean flag = ticketService.deleteTickets(1L);
        assertFalse(flag);
    }
    @Test
    void changeSeat() {
    }

    @Test
    void getTicketsBySection() {
    }

    @Test
    void getTicket() {
    }

    private Ticket createTestTicket() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("sample");
        user.setLastName("lastname");
        user.setEmail("xxx@yyy.com");

        Ticket ticket = new Ticket();
        ticket.setTicketId(1L);
        ticket.setUser(user);
        ticket.setFrom("Mumbai");
        ticket.setTo("Delhi");
        ticket.setPrice(1000.0);
        ticket.setSection(Section.A);
        ticket.setSeat(10);
        user.setTickets(List.of(ticket));
        return ticket;
    }
        private User createTestUser(String email) {
        User user = new User();
        user.setEmail(email);
        return user;
    }
}