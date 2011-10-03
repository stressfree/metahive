package com.sfs.metahive.model;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import com.sfs.metahive.model.UserRole;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import java.util.Collection;
import java.util.HashSet;

/**
 * The Class Principal.
 */
@RooJavaBean
@RooToString
@RooEntity(
		identifierColumn = "id", 
		table = "principal", 
		finders = { "findPrincipalsByOpenIdIdentifier" })
@RooJson
public class Principal implements UserDetails {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 13513413L;

    /** The open id identifier. */
    @NotNull
    @Column(name = "openid_identifier")
    private String openIdIdentifier;
    
    /** The user role. */
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    /** The user status. */
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    
    /** The first name. */
    @Column(name = "first_name")
    private String firstName;

    /** The last name. */
    @Column(name = "last_name")
    private String lastName;

    /** The email address. */
    @Column(name = "email_address")
    private String emailAddress;
        
    
    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
     */
    @Override
    public String getUsername() {
        return this.openIdIdentifier;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
     */
    @Override
    public String getPassword() {
        return "";
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        return this.userStatus == UserStatus.ACTIVE;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        grantedAuthorities.add(
                new GrantedAuthorityImpl(this.userRole.name()));
        return grantedAuthorities;
    }
}
