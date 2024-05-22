package com.ticket.reservation.Controller;

import com.ticket.reservation.Enums.Section;
import com.ticket.reservation.Repository.TicketRepository;
import com.ticket.reservation.Service.TicketService;
import com.ticket.reservation.model.ModifySeat;
import com.ticket.reservation.model.RemoveUser;
import com.ticket.reservation.model.Ticket;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    TicketService ticketService;
    @GetMapping(value = "/health")
    public String health(){
        return "health is success";
    }

    /**
     * Creates a new ticket reservation.
     *
     * @param ticket The ticket information to be saved. (RequestBody)
     * @return A ResponseEntity containing the created ticket object and HTTP status code. (OK)
     */
    @PostMapping
    public ResponseEntity<Ticket> buyTicket(@RequestBody Ticket ticket){
        return ResponseEntity.ok(ticketService.buyTicket(ticket));
    }

    /**
     * Modifies the seat of an existing ticket reservation.
     *
     * This method allows users to change the seat for a ticket they have already purchased.
     *
     * @param seat: A {ModifySeat} object containing the ticket ID and new desired seat number.
     * @return {ResponseEntity} object:
     *     * Status code 200 (OK) with the modified {@link Ticket} object in the body if the seat change is successful.
     *     * Status code 400 (Bad Request) with an error message in the body if the seat cannot be changed (e.g., already booked or ticket ID not found).
     * @throws Exception If an unexpected error occurs during seat modification.
     */
    @PutMapping
    public ResponseEntity<?> modifySeat(@RequestBody ModifySeat seat)
    {
        Ticket modifiedTicket = ticketService.changeSeat(seat);
        if(modifiedTicket == null)
        {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Seat cannot be changed, since its already booked/ ticket id not exist!");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        return ResponseEntity.ok(modifiedTicket);
    }

    /**
     * Retrieves a ticket by its ID.
     *
     * This endpoint fetches a ticket from the database based on the provided ID.
     *
     * @param id The unique identifier of the ticket (long).
     * @return A `ResponseEntity` object:
     *   * Status 200 (OK) with the retrieved `Ticket` object in the body if found.
     *   * Status 404 (Not Found) if no ticket is found with the provided ID.
     *
     * @throws Exception If an error occurs while retrieving the ticket.
     */
    @GetMapping(value="/{id}")
    public ResponseEntity<?> getTicketbyId(@PathVariable long id){
        Ticket ticket = ticketService.getTicketById(id);
        if (ticket==null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ticket);

    }

    /**
     * Retrieves a ticket by its Section(A,B).
     *
     * This endpoint fetches a ticket from the database based on the provided ID.
     *
     * @param Section The identifier of the compartment either A or B.
     * @return A `ResponseEntity` object:
     *   * Status 200 (OK) with the retrieved `Ticket` object in the body if found.
     *   * Status 404 (Not Found) if no ticket is found with the provided ID.
     *
     * @throws Exception If an error occurs while retrieving the ticket.
     */
    @GetMapping(value="section/{section}")
    public ResponseEntity<?> getTicketsBySection(@PathVariable String section){
        List<Ticket> tickets =  ticketService.getTicketsBySection(Section.valueOf(section));
        if(tickets.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tickets);

    }

    /**
     * Deletes all tickets associated with the user identified by the provided ID.
     *
     * @param id The user ID (long).
     * @return A ResponseEntity object:
     *     * Status Code: 200 (OK) - User's tickets deleted successfully.
     *     * Status Code: 404 (Not Found) - User not found or no tickets to delete.
     * @throws Exception
     */
    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> removeUser(@PathVariable long id){
        if(!ticketService.deleteTickets(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }


}
