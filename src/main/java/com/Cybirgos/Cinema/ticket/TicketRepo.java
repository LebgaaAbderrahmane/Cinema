package com.Cybirgos.Cinema.ticket;

import com.Cybirgos.Cinema.diffusion.Diffusion;
import com.Cybirgos.Cinema.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepo extends JpaRepository<Ticket,Integer> {
    List<Ticket> findByUser (User user);
    List<Ticket> findByDiffusion (Diffusion diffusion);
}
