package kloSpringServer.controller;

import kloSpringServer.data.TicketDao;
import kloSpringServer.model.ApiResult;
import kloSpringServer.model.SupportTicket;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/ticket")
public class TicketController extends ControllerBase {
    private final static Logger logger = Logger.getLogger(TicketController.class);
    @Autowired
    private TicketDao ticketDao;

    @RequestMapping(value = "/{ticketId}",  method = RequestMethod.GET, headers="Accept=application/json")
    public @ResponseBody
    ApiResult<SupportTicket> getTicketById(@PathVariable String ticketId) {
        return new ApiResult<>(ticketDao.getTicketById(ticketId));
    }

    @RequestMapping(value = "/latest/{count}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ApiResult<List<SupportTicket>> getLatestTickets(@PathVariable int count) {
        return new ApiResult<>(ticketDao.getLatestTickets(count));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, headers="Accept=application/json")
    public @ResponseBody
    ApiResult saveTicket(@RequestBody SupportTicket ticket) {
        ticket.validate();
        ticketDao.addTicket(ticket);
        return new ApiResult(ApiResult.STATUS_OK);
    }

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody
    ApiResult updateTicket(@RequestBody SupportTicket ticket, @PathVariable int ticketId) {
        logger.info("Updating ticket " + ticketId);
        ticket.validate();
        ticketDao.updateTicket(ticket, ticketId);
        return new ApiResult(ApiResult.STATUS_OK);
    }


}
