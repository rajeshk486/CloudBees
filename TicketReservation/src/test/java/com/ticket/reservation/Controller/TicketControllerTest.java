package com.ticket.reservation.Controller;

import com.ticket.reservation.Enums.Section;
import com.ticket.reservation.Service.TicketService;
import com.ticket.reservation.model.ModifySeat;
import com.ticket.reservation.model.Ticket;
import com.ticket.reservation.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TicketControllerTest {

    @Mock
    private TicketService ticketServiceMock;

    @InjectMocks
    private TicketController ticketController;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testHealth() throws Exception {
        String response = ticketController.health();
        assertEquals("health is success", response);
    }
    @Test
    public void testBuyTicket() throws Exception {
        Ticket ticket = createTestTicket();
        Mockito.when(ticketServiceMock.buyTicket(ticket)).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.buyTicket(ticket);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }
    @Test
    public void testGetTicketById_Success() throws Exception {
        long id = 1L;
        Ticket ticket = createTestTicket();
        Mockito.when(ticketServiceMock.getTicketById(id)).thenReturn(ticket);

        ResponseEntity<?> response = ticketController.getTicketbyId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }

    @Test
    public void testGetTicketById_Fail() throws Exception {
        long id = 1L;
        Ticket ticket = createTestTicket();
        Mockito.when(ticketServiceMock.getTicketById(id)).thenReturn(null);

        ResponseEntity<?> response = ticketController.getTicketbyId(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testModifySeat_Success() throws Exception {
        ModifySeat modifySeat = new ModifySeat();
        modifySeat.setTicketId(1L);
        modifySeat.setSeat(12);

        Ticket modifiedTicket = createTestTicket();
        modifiedTicket.setSeat(12);
        Mockito.when(ticketServiceMock.changeSeat(modifySeat)).thenReturn(modifiedTicket);

        ResponseEntity<?> response = ticketController.modifySeat(modifySeat);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testModifySeat_Failure_TicketNotFound() throws Exception {
        ModifySeat modifySeat = new ModifySeat();
        modifySeat.setTicketId(1L);
        modifySeat.setSeat(12);

        Mockito.when(ticketServiceMock.changeSeat(modifySeat)).thenReturn(null);

        ResponseEntity<?> response = ticketController.modifySeat(modifySeat);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Assert the specific error message in the response body
        Map<String, String> expectedError = new HashMap<>();
        expectedError.put("message", "Seat cannot be changed, since its already booked/ ticket id not exist!");
        assertEquals(expectedError, response.getBody());
    }

    @Test
    public void testGetTicketsBySection_Success() throws Exception {
        String section = "A";
        List<Ticket> tickets = List.of(createTestTicket());

        Mockito.when(ticketServiceMock.getTicketsBySection(Section.valueOf(section))).thenReturn(tickets);

        ResponseEntity<?> response = ticketController.getTicketsBySection(section);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tickets, response.getBody());
    }

    @Test
    public void testGetTicketsBySection_NotFound() throws Exception {
        String section = "A"; // Non-existent section
        Mockito.when(ticketServiceMock.getTicketsBySection(Section.valueOf(section))).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = ticketController.getTicketsBySection(section);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRemoveUser_Success() throws Exception {
        long userId = 1L;
        Mockito.when(ticketServiceMock.deleteTickets(userId)).thenReturn(true);

        ResponseEntity<?> response = ticketController.removeUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody()); // No content expected in the body
    }

    @Test
    public void testRemoveUser_NotFound() throws Exception {
        long userId = 1L;
        Mockito.when(ticketServiceMock.deleteTickets(userId)).thenReturn(false);

        ResponseEntity<?> response = ticketController.removeUser(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
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
}
