package kloSpringServer.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 20.12.2013
 * Time: 23:10
 */
@Controller
@RequestMapping("/")
public class HelpController {
    private static final Logger logger = Logger.getLogger(HelpController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(Model model) {
        logger.info("index: " + model.asMap());
        return new ModelAndView("index", model.asMap());
    }
}
