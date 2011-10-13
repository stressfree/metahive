package com.sfs.metahive.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.sfs.metahive.model.Person;


public abstract class BaseController {

	/**
	 * Load the person from the request.
	 *
	 * @param request the request
	 * @return the person
	 */
	protected final Person loadUser(final HttpServletRequest request) {
		
		Person user = null;
		
		if (request.getUserPrincipal() != null
				&& StringUtils.isNotBlank(request.getUserPrincipal().getName())) {
			
			List<Person> people = Person.findPeopleByOpenIdIdentifier(
					request.getUserPrincipal().getName()).getResultList();
			
			user = people.size() == 0 ? null : people.get(0);
		}
		return user;
	}
	
	/**
	 * Encode the url path segment.
	 *
	 * @param pathSegment the path segment
	 * @param httpServletRequest the http servlet request
	 * @return the string
	 */
	protected String encodeUrlPathSegment(String pathSegment, 
			HttpServletRequest httpServletRequest) {
		
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        }
        catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
	
}
