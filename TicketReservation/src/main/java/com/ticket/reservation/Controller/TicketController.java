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
    @PostMapping
    public ResponseEntity<Ticket> buyTicket(@RequestBody Ticket ticket){
        return ResponseEntity.ok(ticketService.buyTicket(ticket));
    }
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
    @GetMapping(value="/{id}")
    public ResponseEntity<?> getTicketbyId(@PathVariable long id){
        Ticket ticket = ticketService.getTicketById(id);
        if (ticket==null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ticket);

    }
    @GetMapping(value="section/{section}")
    public ResponseEntity<?> getTicketsBySection(@PathVariable String section){
        List<Ticket> tickets =  ticketService.getTicketsBySection(Section.valueOf(section));
        if(tickets.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tickets);

    }
    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> removeUser(@PathVariable long id){
        if(!ticketService.deleteTickets(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }


}
