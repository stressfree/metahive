package com.sfs.metahive.service;

import java.util.List;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sfs.metahive.model.Person;

/**
 * The Class OpenIdUserDetailsService.
 */
public class OpenIdUserDetailsService implements UserDetailsService {

	/**
	 * Implementation of {@code UserDetailsService}. We only need this to
	 * satisfy the {@code RememberMeServices} requirements.
	 */
	public UserDetails loadUserByUsername(String id)
			throws UsernameNotFoundException {

		List<Person> people = Person.findPeopleByOpenIdIdentifier(id).getResultList();

		Person person = people.size() == 0 ? null : people.get(0);

		if (person == null) {
			throw new UsernameNotFoundException(id);
		}
		if (!person.isEnabled()) {
			throw new DisabledException("This user is disabled");
		}

		return person;
	}
}
