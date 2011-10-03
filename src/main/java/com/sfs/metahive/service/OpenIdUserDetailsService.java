package com.sfs.metahive.service;

import java.util.List;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.sfs.metahive.model.Principal;
import com.sfs.metahive.model.UserRole;
import com.sfs.metahive.model.UserStatus;

/**
 * The Class OpenIdUserDetailsService.
 */
public class OpenIdUserDetailsService implements UserDetailsService {
	
    /**
     * Load the principal based on the supplied openId identifier
     */
    public UserDetails loadUserByUsername(String openIdIdentifier) {
    	
        List<Principal> principals = Principal
        		.findPrincipalsByOpenIdIdentifier(openIdIdentifier).getResultList();
        
        Principal principal = principals.size() == 0 ? null : principals.get(0);
        if (principal == null) {
        	
        	principal = new Principal();
        	principal.setOpenIdIdentifier(openIdIdentifier);
        	
        	principal.setUserRole(UserRole.ROLE_NEWUSER);
        	principal.setUserStatus(UserStatus.ACTIVE);
        	
        	principal.persist();
        	
            return principal;
        } else {
        	 if (!principal.isEnabled()) {
                 throw new DisabledException("This user is disabled");
             }
            return principal;
        }
    }
}
