package kloSpringServer.service;

import kloSpringServer.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 21.10.2013
 * Time: 2:23
 */
@Component
public class AuthenticationFilter implements Filter {
    @Autowired
    SessionHelper sessionHelper;

    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class);

    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper((HttpServletRequest) request);
            String token = wrapper.getHeader("token");
            if (token != null) {
                String ip = request.getRemoteAddr();
                Session session = new Session();
                session.setToken(token);
                session.setIpAddress(ip);
                boolean hasAccess = sessionHelper.verifySession(session);
                request.setAttribute("hasAccess", hasAccess);
                if (logger.isDebugEnabled()) {
                    logger.debug(ip + " " + token + " " + hasAccess);
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
