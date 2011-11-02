package com.sfs.metahive.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.sfs.metahive.model.Person;
import com.sfs.metahive.model.UserRole;
import com.sfs.metahive.model.UserStatus;

/**
 * The Class OpenIdAuthenticationFailureHandler.
 */
public class OpenIdAuthenticationFailureHandler implements
		AuthenticationFailureHandler {

	/**
	 * Called when an authentication attempt fails.
	 * 
	 * @param request - the request during which the authentication attempt occurred.
	 * @param response - the response.
	 * @param exception - the exception which was thrown to reject the authentication
	 * request.
	 * @throws java.io.IOException
	 * @throws javax.servlet.ServletException
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, 
			HttpServletResponse response,
			AuthenticationException authenticationException) 
		    throws IOException, ServletException {
		
		if (authenticationException instanceof DisabledException) {
			RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
			redirectStrategy.sendRedirect(request, response, "/accountDisabled");
		}		
		
		if (isFailedDueToUserNotRegistered(authenticationException)) {
			
			OpenIDAuthenticationToken token	= (OpenIDAuthenticationToken)
					authenticationException.getAuthentication();
						
			String id = token.getIdentityUrl();
			
			List<Person> people = Person.findPeopleByOpenIdIdentifier(id).getResultList();

			Person person = people.size() == 0 ? null : people.get(0);
			
			if (person == null) {
				
				// The person does not exist, create
				String email = null;
				String firstName = null;
				String lastName = null;

				List<OpenIDAttribute> attributes = token.getAttributes();

				for (OpenIDAttribute attribute : attributes) {
					if (attribute.getName().equals("email")) {
						email = attribute.getValues().get(0);
					}
					if (attribute.getName().equals("firstName")) {
						firstName = attribute.getValues().get(0);
					}
					if (attribute.getName().equals("lastName")) {
						lastName = attribute.getValues().get(0);
					}
				}

				if (StringUtils.isBlank(email)) {
					Random generator = new Random();
					email = String.valueOf(generator.nextInt()) + "@"
							+ String.valueOf(Calendar.getInstance().getTimeInMillis());
				}
				if (StringUtils.isBlank(firstName)) {
					firstName = "New";
				}
				if (StringUtils.isBlank(lastName)) {
					lastName = "User";
				}		
				
				person = new Person();
				person.setOpenIdIdentifier(id);

				person.setEmailAddress(email);
				person.setFirstName(firstName);
				person.setLastName(lastName);
				person.setUserRole(UserRole.ROLE_USER);
				person.setUserStatus(UserStatus.ACTIVE);

				person.persist();				
				
				// Recreate OpenIDAuthentication token, transfer values from existing
				// token, and assign roles from retrieved user. Since grantedAuthorities 
				// is unmodifiable list and no way to update the pre created token.

				OpenIDAuthenticationToken newToken = new OpenIDAuthenticationToken(
						person, person.getAuthorities(), 
						token.getIdentityUrl(), token.getAttributes());
				newToken.setAuthenticated(true);
				
				token.setDetails(person);
				SecurityContextHolder.getContext().setAuthentication(newToken);
								
				RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
				redirectStrategy.sendRedirect(request, response, "/user");
			}
		}
	}

	/**
	 * Checks if is failed due to user not registered.
	 *
	 * @param authenticationException the authentication exception
	 * @return true, if is failed due to user not registered
	 */
	private boolean isFailedDueToUserNotRegistered(
			AuthenticationException authenticationException) {

		return null != authenticationException
				&& authenticationException instanceof UsernameNotFoundException
				&& authenticationException.getAuthentication() 
						instanceof OpenIDAuthenticationToken
				&& ((OpenIDAuthenticationToken) authenticationException
						.getAuthentication()).getStatus().equals(
						OpenIDAuthenticationStatus.SUCCESS);
	}
}
