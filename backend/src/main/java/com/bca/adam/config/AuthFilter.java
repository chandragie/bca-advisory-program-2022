package com.bca.adam.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bca.adam.service.LoginService;
import com.bca.adam.util.JWTTokenizer;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    @Value("${server.servlet.context-path}")
    String contextPath;

    @Autowired
    LoginService loginService;

    private boolean isAllowedAnonymousURL(HttpServletRequest request) {
        boolean retVal = false;
        String uri = request.getRequestURI();

        List<String> allowedAnonymousURL = new ArrayList<String>();
        allowedAnonymousURL.add(contextPath + "/user");
        allowedAnonymousURL.add(contextPath + "/sign/in");

        for (String url : allowedAnonymousURL) {
            if (uri.startsWith(url)) {
                retVal = true;
                break;
            }
        }

        return retVal;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String auth = request.getHeader("Authorization");

        if (!isAllowedAnonymousURL(request)) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
            response.setHeader("Access-Control-Allow-Headers",
                    "Authorization, Origin, X-Requested-With, Content-Type, Accept");

            if (auth == null) {
                log.info("Auth is null");
                request.getRequestDispatcher("/common/unauthorized").forward(request, response);

            } else {
                log.info("Auth {}", auth);
                if (JWTTokenizer.validateJWT(auth) != null)
                    filterChain.doFilter(request, response);
                else
                    request.getRequestDispatcher("/common/unauthorized").forward(request, response);
            }

        } else {
            log.debug("Accessing anonymous URL : {}", request.getRequestURI());
            filterChain.doFilter(request, response);
        }
    }

}
