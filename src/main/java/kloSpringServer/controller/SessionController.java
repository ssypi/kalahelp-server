package kloSpringServer.controller;

import kloSpringServer.model.ApiResult;
import kloSpringServer.model.Session;
import kloSpringServer.model.SessionHelper;
import kloSpringServer.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 20.10.2013
 * Time: 0:52
 */
@Controller
@RequestMapping("/session")
public class SessionController {

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ApiResult newSessionForUser(HttpServletRequest request, @RequestBody User user) {
        SessionHelper helper = new SessionHelper();
        String ip = request.getRemoteAddr();
        Session newSession = helper.createSessionForIp(ip);
        helper.saveSession(newSession);
        return new ApiResult(newSession);
    }
}
