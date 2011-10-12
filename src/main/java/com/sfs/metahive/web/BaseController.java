package com.sfs.metahive.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

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
}
