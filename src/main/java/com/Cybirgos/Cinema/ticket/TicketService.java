package com.Cybirgos.Cinema.ticket;

import com.Cybirgos.Cinema.config.JwtService;
import com.Cybirgos.Cinema.diffusion.Diffusion;
import com.Cybirgos.Cinema.diffusion.DiffusionRepo;
import com.Cybirgos.Cinema.room.SeatRepo;
import com.Cybirgos.Cinema.user.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    TicketRepo ticketRepo;
    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepo userRepo;
    @Autowired
    DiffusionRepo diffusionRepo;
    @Autowired
    SeatRepo seatRepo;

    public ResponseEntity<String> bookTicket(BookingRequest bookingRequest, HttpServletRequest request) {
        // TODO check if there is still some seats available
        Diffusion savedDiffusion = diffusionRepo.findById(bookingRequest.getDiffusionId()).orElseThrow();
        if (savedDiffusion.getNbTicketSold() < savedDiffusion.getRoom().getCapacity()){

            var token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
            var username = jwtService.extractUsername(token);
            var user = userRepo.findByUsername(username).orElseThrow();
            var bookedSeat = seatRepo.findBySeatNb(bookingRequest.getSeatNumber());
            var savedTicket = Ticket.builder()
                    .user(user)
                    .price(bookingRequest.getPrice())
                    .createdOn(LocalDateTime.now())
                    .seat(bookedSeat)
                    .diffusion(savedDiffusion)
                    .build();
            ticketRepo.save(savedTicket);
            // TODO decrement number of seats available
            return new ResponseEntity<>("Ticket booked", HttpStatus.OK);
        }
        return new ResponseEntity<>("room full!",HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<List<Ticket>> getAllUserBookings(HttpServletRequest request) {
        var token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        var username = jwtService.extractUsername(token);
        var user = userRepo.findByUsername(username).orElseThrow();
        return new ResponseEntity<>(ticketRepo.findByUser(user),HttpStatus.OK) ;
    }

    public ResponseEntity<List<Ticket>> getBookingsByDiffusion(Integer id) {
        var diffusion = diffusionRepo.findById(id).orElseThrow();
        return new ResponseEntity<>(ticketRepo.findByDiffusion(diffusion),HttpStatus.OK);
    }

    public ResponseEntity<String> cancelBooking(Integer id) {
        // TODO increment number of seats available
        ticketRepo.deleteById(id);
        return new ResponseEntity<>("Canceled ",HttpStatus.OK);
    }
}
