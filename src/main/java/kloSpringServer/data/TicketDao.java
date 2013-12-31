package kloSpringServer.data;

import kloSpringServer.model.SupportTicket;

import java.util.List;

public interface TicketDao {
    public SupportTicket getTicketById(String ticketId);
    public void addTicket(SupportTicket ticket);
    List<SupportTicket> getLatestTickets(int count);

    void updateTicket(SupportTicket ticket, int ticketId);
}
