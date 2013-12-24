package kloSpringServer.controller;

import kloSpringServer.data.TicketDao;
import kloSpringServer.model.ApiResult;
import kloSpringServer.model.SupportTicket;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

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


//    @RequestMapping(value = "/latest/{count}", method = RequestMethod.GET, produces = "application/json")
//    public @ResponseBody
//    ApiResult<List<SupportTicket>> getLatestTickets(@PathVariable int count) {
//        return new ApiResult<>(ticketDao.getLatestTickets(count));
//    }

    @RequestMapping(value = "/latest/{count}",
            method = RequestMethod.GET)
    public ModelAndView listTickets(@PathVariable int count) {
//        Map<String, List<SupportTicket>> model = new HashMap<>();
//        model.put("ticketList", ticketDao.getLatestTickets(count));
//        logger.info("Tickets: " + model.asMap().get("ticketList"));
        return new ModelAndView("ticket", "ticketList", ticketDao.getLatestTickets(count));
    }

    @RequestMapping(value = "/new/", method = RequestMethod.GET)
    public ModelAndView newTicket() {
        ModelAndView mav = new ModelAndView("/ticket", "ticket", new SupportTicket());
//        mav.addObject("subTitle", "Supporttu Tickettoo");
        List<String> categoryList = new ArrayList<>();
        categoryList.add("Other");
        categoryList.add("Billing");
        mav.addObject("categoryList", categoryList);
        return mav;
    }

    /**
     * <p>Because {@code @RequestBody} cannot be used with {@code application/x-www-form-urlencoded}
     * we have to define identical method for it with @ModelAttribute.</p>
     * <p>This method merely redirects the call to the {@link #saveTicket(kloSpringServer.model.SupportTicket, org.springframework.web.servlet.mvc.support.RedirectAttributes)}
     * method.</p>
     */
    @RequestMapping(value = "/", method = RequestMethod.POST,
    consumes = "application/x-www-form-urlencoded")
    public String saveTicket(Model model, @Valid @ModelAttribute("ticket") SupportTicket ticket, BindingResult result) {
        if (result.hasErrors()) {
            logger.warn("Ticket with errors! " +result.getErrorCount());
//            m.addAttribute(result);
            return "/ticket";
        }
        logger.info("Ticket no errors.");
        return saveTicket(ticket, null);
    }

    /**
     * <p>Saves the {@link kloSpringServer.model.SupportTicket} in the request body to database
     * and redirects the request to {@link kloSpringServer.controller.HelpController#index(org.springframework.ui.Model)}
     * with "message" flash attribute to indicate success or failure.</p>
     *
     * @throws kloSpringServer.ValidationException if the provided SupportTicket is not valid.
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.POST,
    consumes = "application/json")
    public String saveTicket(@RequestBody SupportTicket ticket, RedirectAttributes redirectAttr) {
        logger.info(ticket.toString());
        ticket.validate();
        ticketDao.addTicket(ticket);
        redirectAttr.addFlashAttribute("message", "Your ticket was saved successfully.");
        UriComponents uriComponents = MvcUriComponentsBuilder
                .fromMethodCall(on(HelpController.class)
                        .index(redirectAttr)).buildAndExpand();
        URI uri = uriComponents.encode().toUri();

        return "redirect:" + uri.toString();
    }


//    @RequestMapping(value = "/", method = RequestMethod.POST, headers = "Accept=application/json")
//    public
//    @ResponseBody
//    ApiResult saveTicket(@RequestBody SupportTicket ticket) {
//        ticket.validate();
//        ticketDao.addTicket(ticket);
//        return new ApiResult(ApiResult.STATUS_OK);
//    }

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody
    ApiResult updateTicket(@RequestBody SupportTicket ticket, @PathVariable int ticketId) {
        logger.info("Updating ticket " + ticketId);
        ticket.validate();
        ticketDao.updateTicket(ticket, ticketId);
        return new ApiResult(ApiResult.STATUS_OK);
    }


}
