package com.sfs.metahive.model;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class Person.
 */
@RooJavaBean
@RooToString
@RooEntity(
		identifierColumn = "id", 
		table = "person", 
		finders = { "findPeopleByOpenIdIdentifier" })
@RooJson
public class Person implements UserDetails {

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
    @NotNull
    @Column(name = "first_name")
    private String firstName;

    /** The last name. */
    @NotNull
    @Column(name = "last_name")
    private String lastName;

    /** The email address. */
    @NotNull
    @Column(name = "email_address", unique = true)
    private String emailAddress;
    
	/** The descriptions this user has created. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
	private Set<Description> descriptions = new HashSet<Description>();
	
	/** The organisations. */
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "people")
	private Set<Organisation> organisations = new HashSet<Organisation>();
    
	/** 
	 * Returns the username used to authenticate the user. Cannot return null.
	 * 
	 * @return the username (never null)
	 */
    @Override
	public final String getUsername() {
        return this.openIdIdentifier;
    }

    /**
     * Returns the password used to authenticate the user. Cannot return null.
     * 
     * @return the password (never null)
     */
    @Override
	public final String getPassword() {
        return "";
    }

    /**
     * Indicates whether the user's account has expired. 
     * An expired account cannot be authenticated.
     * 
     * @return true if the user's account is valid (ie non-expired), 
     *         false if no longer valid (ie expired)
     */
    @Override
	public final boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. 
     * A locked user cannot be authenticated.
     * 
     * @return true if the user is not locked, false otherwise
     */
    @Override
	public final boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. 
     * Expired credentials prevent authentication.
     * 
     * @return true if the user's credentials are valid (ie non-expired),
     *         false if no longer valid (ie expired)
     */
    @Override
	public final boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. 
     * A disabled user cannot be authenticated.
     * 
     * @return true if the user is enabled, false otherwise
     */
    @Override
	public final boolean isEnabled() {
        return this.userStatus == UserStatus.ACTIVE;
    }

    /**
     * Returns the authorities granted to the user. Cannot return null.
     * 
     * @return the authorities, sorted by natural key (never null)
     */
    @Override
	public final Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        grantedAuthorities.add(
                new GrantedAuthorityImpl(this.userRole.name()));
        return grantedAuthorities;
    }
    
    /**
     * The person's formatted name.
     * 
     * @return the formatted name of the person
     */
    public final String getFormattedName() {
    	return this.firstName + " " + this.lastName;
    }
    
	/**
	 * Adds an organisation.
	 * 
	 * @param organisation the organisation
	 */
	public void addOrganisation(Organisation organisation) {
		organisation.addPerson(this);
	}
}