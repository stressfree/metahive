package com.sfs.metahive.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
    @Override
    public void commence(final HttpServletRequest request, 
    		final HttpServletResponse response, 
    		final AuthenticationException authException) 
    		throws IOException, ServletException {

        if (authException != null) {
            response.sendRedirect("403.html");
        }
    }
}
