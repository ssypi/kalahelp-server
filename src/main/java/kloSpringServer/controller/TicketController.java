package kloSpringServer.controller;

import kloSpringServer.data.TicketDao;
import kloSpringServer.model.ApiResult;
import kloSpringServer.model.SupportTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private TicketDao ticketDao;

    @RequestMapping(value = "/{ticketId}",  method = RequestMethod.GET, headers="Accept=application/json")
    public @ResponseBody
    ApiResult getTicketById(@PathVariable String ticketId) {
        return new ApiResult(ticketDao.getTicketById(ticketId));
    }

    @RequestMapping(value = "/latest/{count}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ApiResult getLatestTickets(@PathVariable int count) {
        return new ApiResult(ticketDao.getLatestTickets(count));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, headers="Accept=application/json")
    public @ResponseBody
    ApiResult saveTicket(@RequestBody SupportTicket ticket) {
        ticket.validate();
        ticketDao.addTicket(ticket);
        return new ApiResult(null, ApiResult.STATUS_OK);
    }


}
