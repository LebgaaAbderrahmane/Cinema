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
        Diffusion savedDiffusion = diffusionRepo.findById(bookingRequest.getDiffusionId()).orElseThrow();
        if (savedDiffusion.getNbTicketSold() < savedDiffusion.getRoom().getCapacity()){

            var token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
            var username = jwtService.extractUsername(token);
            var user = userRepo.findByUsername(username).orElseThrow();
            var bookedSeat = seatRepo.findBySeatNumber(bookingRequest.getSeatNumber());
            bookedSeat.setReserved(true);
            double price = savedDiffusion.getPrice();
            if(bookedSeat.isVip()){
                price = savedDiffusion.getVipPrice();
            }
            var savedTicket = Ticket.builder()
                    .user(user)
                    .price(price)
                    .createdOn(LocalDateTime.now())
                    .seat(bookedSeat)
                    .diffusion(savedDiffusion)
                    .build();
            ticketRepo.save(savedTicket);
            savedDiffusion.setNbTicketSold(savedDiffusion.getNbTicketSold()+1);

            return new ResponseEntity<>("Ticket booked", HttpStatus.OK);
        }
        return new ResponseEntity<>("room full!",HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<List<Ticket>> getAllUserBookings(HttpServletRequest request) {
        var token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        var username = jwtService.extractUsername(token);
        if (userRepo.findByUsername(username).isPresent()) {
            var user = userRepo.findByUsername(username).get();
            if(ticketRepo.findByUser(user).isPresent()){
                return new ResponseEntity<>(ticketRepo.findByUser(user).get(),HttpStatus.OK) ;
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Ticket>> getBookingsByDiffusion(Integer id) {
        Diffusion diffusion = null;
        if (diffusionRepo.findById(id).isPresent()) {
            diffusion = diffusionRepo.findById(id).get();
        }
        if(ticketRepo.findByDiffusion(diffusion).isPresent()){
            return new ResponseEntity<>(ticketRepo.findByDiffusion(diffusion).get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> cancelBooking(Integer id) {
        Ticket booking = null;
        if (ticketRepo.findById(id).isPresent()) {
            booking = ticketRepo.findById(id).get();
            booking.getDiffusion().setNbTicketSold(booking.getDiffusion().getNbTicketSold()-1);
            return new ResponseEntity<>("Canceled ",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
