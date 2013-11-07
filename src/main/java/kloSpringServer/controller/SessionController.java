package kloSpringServer.controller;

import kloSpringServer.data.SessionDao;
import kloSpringServer.model.ApiResult;
import kloSpringServer.model.Session;
import kloSpringServer.model.User;
import kloSpringServer.service.UserAuthentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 20.10.2013
 * Time: 0:52
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/session")
public class SessionController {

    private static final Logger logger = Logger.getLogger(SessionController.class);

    @Autowired
    private SessionDao sessionDao;

    @Autowired
    private UserAuthentication authentication;

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ApiResult newSessionForUser(@RequestBody User user, HttpServletResponse response, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        logger.info("Login attempt from " + ip);

        if (!user.validate()) { // invalid request
            logger.info("invalid request");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ApiResult(null, ApiResult.STATUS_ERROR);
        }

        if (!authentication.verifyUser(user)) { // wrong user/pass
            logger.info("wrong user/pass");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new ApiResult(null, ApiResult.STATUS_ERROR);
        }

        Session newSession = sessionDao.createNewSessionForIp(ip);
        return new ApiResult(newSession);
    }
}
