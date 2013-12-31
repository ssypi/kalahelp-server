package kloSpringServer.controller;

import kloSpringServer.data.CategoryDao;
import kloSpringServer.data.TicketDao;
import kloSpringServer.model.ApiResult;
import kloSpringServer.model.SupportTicket;
import kloSpringServer.service.EmailSender;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketController extends ControllerBase {
    private final static Logger logger = Logger.getLogger(TicketController.class);
    @Autowired
    private TicketDao ticketDao;

    @Autowired
    @Qualifier("emailSender")
    private EmailSender emailSender;

    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value = "/{ticketId}",  method = RequestMethod.GET, headers="Accept=application/json")
    public @ResponseBody
    ApiResult<SupportTicket> getTicketById(@PathVariable String ticketId) {
        return new ApiResult<>(ticketDao.getTicketById(ticketId));
    }


    @RequestMapping(value = "/latest/{count}",
            method = RequestMethod.GET)
    public ModelAndView listTickets(@PathVariable int count) {
        return new ModelAndView("ticket", "result", ticketDao.getLatestTickets(count));
    }

    @RequestMapping(value = "/new/", method = RequestMethod.GET)
    public ModelAndView newTicket() {
        ModelAndView mav = new ModelAndView("/ticket", "ticket", new SupportTicket());
//        mav.addObject("subTitle", "Supporttu Tickettoo");
        List<String> categoryList = categoryDao.getCategories();
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
    public String saveTicket(Model model, @Valid @ModelAttribute("ticket") SupportTicket ticket, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.warn("Ticket with errors! " +result.getErrorCount());
            List<String> categoryList = categoryDao.getCategories();
            model.addAttribute("categoryList", categoryList);
            model.addAttribute(result);
            return "/ticket";
        }
        logger.info("Ticket no errors.");
        return saveTicket(ticket, redirectAttributes);
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
        if (redirectAttr == null) {
            redirectAttr = new RedirectAttributesModelMap();
        }

        redirectAttr.addFlashAttribute("message", "Your ticket was saved successfully.");

        return "redirect:/";
    }


    @RequestMapping(value = "/{ticketId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody
    ApiResult updateTicket(@RequestBody SupportTicket ticket, @PathVariable int ticketId) {
        logger.info("Updating ticket " + ticketId);
        ticket.validate();
        ticket.setStatus("Closed");
        ticketDao.updateTicket(ticket, ticketId);
        emailSender.sendTicket(ticket);
        return new ApiResult(ApiResult.STATUS_OK);
    }


}
