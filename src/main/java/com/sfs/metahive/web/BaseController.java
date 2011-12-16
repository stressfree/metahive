package com.sfs.metahive.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.sfs.metahive.model.Person;
import com.sfs.metahive.model.MetahivePreferences;


public abstract class BaseController {

	@Autowired
	private ApplicationContext context;

	/**
	 * Gets the translated message.
	 *
	 * @param code the code
	 * @return the message
	 */
	protected final String getMessage(final String code) {		
		return context.getMessage(code, null, LocaleContextHolder.getLocale());		
	}
	
	/**
	 * Gets the translated message.
	 *
	 * @param code the code
	 * @param objectClass the object class
	 * @return the message
	 */
	protected final String getMessage(final String code, final Class<?> objectClass) {
		
		String objectCode = StringUtils.replace(objectClass.getName(), ".", "_");
		
		String argument = context.getMessage("label_" + objectCode.toLowerCase(), null,
        		LocaleContextHolder.getLocale());
		
		return context.getMessage(code, new String[] { argument.toLowerCase() }, 
        		LocaleContextHolder.getLocale());		
	}
	
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
	

    @ModelAttribute("preferences")
    public MetahivePreferences loadPreferences() {
    	return MetahivePreferences.load();
    }

    @ModelAttribute("resultCounts")
    public Integer[] resultCounts() {
    	return new Integer[] { 50, 100, 200 };
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
