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
    public ApiResult<Session> newSessionForUser(@RequestBody User user, HttpServletResponse response, HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        logger.info("login attempt from " + ip);
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (!user.validate()) { // invalid request
            logger.info("invalid request");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new ApiResult<>(ApiResult.STATUS_ERROR);
        }

        if (!authentication.verifyUser(user)) { // wrong user/pass
            logger.info("wrong user/pass");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new ApiResult<>(ApiResult.STATUS_ERROR, "Invalid user/pass");
        }

        Session newSession = sessionDao.createNewSessionForIp(ip);
        newSession.setUser(user.getUsername());
        logger.info("Successful login for " + user.getUsername() + " from " + newSession.getIpAddress());
        return new ApiResult<>(newSession);
    }
}
