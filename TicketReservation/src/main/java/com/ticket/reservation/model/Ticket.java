package com.ticket.reservation.model;

import com.ticket.reservation.Enums.Section;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tickets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name ="fromStation")
    private String from;
    @Column(name ="toStation")
    private String to;
    private double price;
    private Section section;
    private int seat;

}
