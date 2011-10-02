package com.sfs.metahive;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;


public class RedirectAccessDeniedHandler implements AccessDeniedHandler {

	private String accessDeniedUrl;

    public RedirectAccessDeniedHandler() {
    }

    public RedirectAccessDeniedHandler(String accessDeniedUrl) {
        this.accessDeniedUrl = accessDeniedUrl;
    }

    @Override
	public void handle(final HttpServletRequest request, 
    		final HttpServletResponse response, 
    		final AccessDeniedException accessDeniedException) 
    		throws IOException, ServletException {
        response.sendRedirect(accessDeniedUrl);
    }

    public String getAccessDeniedUrl() {
        return accessDeniedUrl;
    }

    public void setAccessDeniedUrl(String accessDeniedUrl) {
        this.accessDeniedUrl = accessDeniedUrl;
    }
	
}
