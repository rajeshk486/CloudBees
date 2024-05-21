package com.ticket.reservation.model;

import com.ticket.reservation.Enums.Section;
import lombok.Data;

@Data
public class ModifySeat {
    Long ticketId;
    Section section;
    int seat;
}
