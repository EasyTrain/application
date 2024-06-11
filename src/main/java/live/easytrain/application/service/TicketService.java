package live.easytrain.application.service;

import live.easytrain.application.entity.Ticket;
import live.easytrain.application.repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService implements TicketServiceInterface{

    private TicketRepo ticketRepo;

    @Autowired
    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    @Override
    public Ticket createTicket(Ticket ticket) {
        return ticketRepo.save(ticket);
    }
}
