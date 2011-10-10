package com.sfs.metahive.service;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.sfs.metahive.model.Person;
import com.sfs.metahive.model.UserRole;
import com.sfs.metahive.model.UserStatus;

/**
 * The Class OpenIdUserDetailsService.
 */
public class OpenIdUserDetailsService implements UserDetailsService {
	
    /**
     * Load the person based on the supplied openId identifier
     */
    public UserDetails loadUserByUsername(String openIdIdentifier) {
    	
        List<Person> people = Person.findPeopleByOpenIdIdentifier(
        		openIdIdentifier).getResultList();
        
        Person person = people.size() == 0 ? null : people.get(0);
        if (person == null) {
        	
        	person = new Person();
        	person.setOpenIdIdentifier(openIdIdentifier);
        	
        	Random generator = new Random();
        	String emailAddress = String.valueOf(generator.nextInt()) + "@"
        			+ String.valueOf(Calendar.getInstance().getTimeInMillis());
        	        	
        	person.setFirstName("New");
        	person.setLastName("User");
        	person.setEmailAddress(emailAddress);
        	person.setUserRole(UserRole.ROLE_NEWUSER);
        	person.setUserStatus(UserStatus.ACTIVE);
        	
        	person.persist();
        	
            return person;
        } else {
        	 if (!person.isEnabled()) {
                 throw new DisabledException("This user is disabled");
             }
            return person;
        }
    }
}
