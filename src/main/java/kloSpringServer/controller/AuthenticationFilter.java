package kloSpringServer.controller;

import kloSpringServer.data.SessionDao;
import kloSpringServer.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    SessionDao sessionDao;

    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (token != null && !token.isEmpty()) {
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            Session session = new Session();
            session.setToken(token);
            session.setIpAddress(ip);
            boolean authorized = sessionDao.verifySession(session);
            request.setAttribute("authorized", authorized);
            if (logger.isDebugEnabled()) {
                logger.debug(ip + " " + token + " " + authorized);
            }
        }
        //TODO: redirect/throw exception/http status unauthorized
        filterChain.doFilter(request, response);
    }
}
