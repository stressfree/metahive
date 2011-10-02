package com.sfs.metahive.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.sfs.metahive.model.Principal;
import com.sfs.metahive.model.UserRole;


public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
    @Override
    public void commence(final HttpServletRequest request, 
    		final HttpServletResponse response, 
    		final AuthenticationException authException) 
    		throws IOException, ServletException {

        if (authException != null && request.getUserPrincipal() != null) {
        	
        	System.out.println(authException.getMessage());
        	
        	String redirect = "accessdenied";

            List<Principal> principals = Principal.findPrincipalsByOpenIdIdentifier(
            		request.getUserPrincipal().getName()).getResultList();
            
    		Principal principal = principals.size() == 0 ? null : principals.get(0);

    		if (principal.getUserRole() == UserRole.ROLE_NEWUSER) {
    			redirect = "user/newdetails";
    		}
    		
            response.sendRedirect(redirect);
    	}
    }
}
