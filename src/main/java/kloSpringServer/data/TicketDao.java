package kloSpringServer.data;

import kloSpringServer.model.SupportTicket;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 9.10.2013
 * Time: 1:35
 */
public interface TicketDao {
    public SupportTicket getTicketById(String ticketId);
    public void addTicket(SupportTicket ticket);
    List<SupportTicket> getLatestTickets(int count);
}
