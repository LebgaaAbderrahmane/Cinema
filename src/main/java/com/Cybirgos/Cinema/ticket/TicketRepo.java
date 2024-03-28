package com.Cybirgos.Cinema.ticket;

import com.Cybirgos.Cinema.diffusion.Diffusion;
import com.Cybirgos.Cinema.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepo extends JpaRepository<Ticket,Integer> {

    Optional<List<Ticket>>  findByUser (User user);
    Optional<List<Ticket>>  findByDiffusion (Diffusion diffusion);
}
