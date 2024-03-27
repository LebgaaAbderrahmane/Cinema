package com.Cybirgos.Cinema.ticket;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
@CrossOrigin
@RequiredArgsConstructor
public class TicketController {  // TODO test all endpoints
    @Autowired
    TicketService ticketService;
    @PostMapping("/bookTicket")
    public ResponseEntity<String> bookTicket (@RequestBody BookingRequest bookingRequest, HttpServletRequest request){
        return ticketService.bookTicket(bookingRequest,request);
    }
    @GetMapping("/getAllUserBookings")
    public ResponseEntity<List<Ticket>> getAllUserBookings (HttpServletRequest request){
       return ticketService.getAllUserBookings(request);
    }
    @GetMapping("/getBookingsByDiffusion/{id}")
    public ResponseEntity<List<Ticket>> getBookingsByDiffusion (@PathVariable Integer id){
        return ticketService.getBookingsByDiffusion(id);
    }
    @DeleteMapping("/cancelBooking/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Integer id){
        return ticketService.cancelBooking(id);
    }

}
