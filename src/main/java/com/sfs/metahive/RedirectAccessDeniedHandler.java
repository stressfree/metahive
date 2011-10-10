package com.sfs.metahive;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.sfs.metahive.model.Person;
import com.sfs.metahive.model.UserRole;


/**
 * The Class RedirectAccessDeniedHandler.
 */
public class RedirectAccessDeniedHandler implements AccessDeniedHandler {

	/** The access denied url. */
	private String accessDeniedUrl;
	
	/** The new user url. */
	private String newUserUrl;
	

    /**
     * Instantiates a new redirect access denied handler.
     */
    public RedirectAccessDeniedHandler() {
    }

    /**
     * Instantiates a new redirect access denied handler.
     *
     * @param accessDeniedUrlVal the access denied url
     */
    public RedirectAccessDeniedHandler(final String accessDeniedUrlVal) {
        this.accessDeniedUrl = accessDeniedUrlVal;
    }

    /**
     * Handle the access denied action.
     *
     * @param request the request
     * @param response the response
     * @param accessDeniedException the access denied exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    @Override
	public final void handle(final HttpServletRequest request, 
    		final HttpServletResponse response, 
    		final AccessDeniedException accessDeniedException) 
    		throws IOException, ServletException {
    	
    	String redirect = this.accessDeniedUrl;
    	
    	if (StringUtils.isNotBlank(this.newUserUrl) 
    			&& request.getUserPrincipal() != null) {
    		List<Person> people = Person.findPeopleByOpenIdIdentifier(
    			request.getUserPrincipal().getName()).getResultList();
    
    		Person person = people.size() == 0 ? null : people.get(0);
    		
    		if (person != null && person.getUserRole() == UserRole.ROLE_NEWUSER) {
    			redirect = this.newUserUrl;
    		}
    	}
    	
        response.sendRedirect(redirect);
    }

    /**
     * Gets the access denied url.
     *
     * @return the access denied url
     */
    public final String getAccessDeniedUrl() {
        return accessDeniedUrl;
    }

    /**
     * Sets the access denied url.
     *
     * @param accessDeniedUrlVal the new access denied url
     */
    public final void setAccessDeniedUrl(final String accessDeniedUrlVal) {
        this.accessDeniedUrl = accessDeniedUrlVal;
    }
    
    /**
     * Gets the new user url.
     *
     * @return the new user url
     */
    public final String getNewUserUrl() {
        return this.newUserUrl;
    }
    
    /**
     * Sets the new user url.
     *
     * @param newUserUrlVal the new new user url
     */
    public final void setNewUserUrl(final String newUserUrlVal) {
        this.newUserUrl = newUserUrlVal;
    }
	
}
