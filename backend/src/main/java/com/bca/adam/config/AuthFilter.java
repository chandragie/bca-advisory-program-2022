package com.bca.adam.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter implements Filter {

    @Value("${server.servlet.context-path}")
    String contextPath;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String auth = request.getHeader("Authorization");
        if (null != auth || isAllowedAnonymousURL(request) || request.getRequestURI().startsWith("/adam/sign/out")) {
            fc.doFilter(request, response);
        } else {
            // response.sendRedirect(request.getContextPath() + "/common/unauthorized");
            request.getRequestDispatcher("/common/unauthorized").forward(request, response);
        }
    }

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

}
