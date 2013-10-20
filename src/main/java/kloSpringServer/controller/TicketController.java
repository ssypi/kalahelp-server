package kloSpringServer.controller;

import kloSpringServer.data.TicketDao;
import kloSpringServer.model.ApiResult;
import kloSpringServer.model.SupportTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 9.10.2013
 * Time: 1:29
 */
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
}
