package com.Cybirgos.Cinema.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingRequest {
    //private double price;
    private int seatNumber;
    private int diffusionId;
}
