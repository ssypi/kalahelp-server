package kloSpringServer.data;

import kloSpringServer.controller.ControllerTest;
import kloSpringServer.model.SupportTicket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Joona
 * Date: 17.12.2013
 * Time: 19:17
 * To change this template use File | Settings | File Templates.
 */
public class JdbcTicketDaoTest extends ControllerTest {
    @Autowired
    public TicketDao ticketDao;

    @Test
    public void testUpdateTicket() throws Exception {
        SupportTicket ticket = new SupportTicket();
        ticket.setTicketNumber(1);
        String replyBy = "Kalamisen kaverri";
        String reply = "Kalamiehen kaverin vastaus";
        ticket.setReplyBy(replyBy);
        ticket.setReply(reply);
        ticket.setStatus("Closed");

        ticketDao.updateTicket(ticket, 1);
        SupportTicket updatedTicket = ticketDao.getTicketById("1");
        assertEquals(reply, updatedTicket.getReply());
        assertEquals(replyBy, updatedTicket.getReplyBy());
        assertEquals("Closed", updatedTicket.getStatus());
        assertNotNull(updatedTicket.getReplyDate());
    }
}
