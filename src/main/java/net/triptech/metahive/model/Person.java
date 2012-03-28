/*******************************************************************************
 * Copyright (c) 2012 David Harrison, Triptech Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 *
 * Contributors:
 *     David Harrison, Triptech Ltd - initial API and implementation
 ******************************************************************************/
package net.triptech.metahive.model;

import flexjson.JSON;

import org.apache.commons.lang.StringUtils;
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
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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
    @OneToMany(mappedBy = "person")
    private List<Description> descriptions = new ArrayList<Description>();

    /** The organisations. */
    @ManyToMany(mappedBy = "people")
    @OrderBy("name ASC")
    private List<Organisation> organisations = new ArrayList<Organisation>();

    /** The related data sources. */
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "pointsOfContact")
    private List<DataSource> dataSources = new ArrayList<DataSource>();

    /** The related comments. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    @OrderBy("created ASC")
    private List<Comment> comments = new ArrayList<Comment>();

    /** The related submissions. */
    @OneToMany(mappedBy = "person")
    @OrderBy("created ASC")
    private List<Submission> submissions = new ArrayList<Submission>();

    /** The definitions to be included in a record search. */
    @ManyToMany
    @OrderBy("name ASC")
    private List<Definition> searchDefinitions = new ArrayList<Definition>();

    /** The show all definitions flag. */
    private boolean showAllDefinitions;

    /** The expand all definitions flag. */
    private boolean expandAllDefinitions;

    /** The search options. */
    private String searchOptions;

    /** The search options map. */
    @Transient
    private Map<String, Boolean> searchOptionsMap;


    /**
     * Build the option map if any search options exist.
     */
    @PostLoad
    public final void postLoad() {
    	this.searchOptionsMap = new HashMap<String, Boolean>();

		if (StringUtils.isNotBlank(this.searchOptions)) {
			// Parse the search options
			StringTokenizer st = new StringTokenizer(this.searchOptions, "|");
			while (st.hasMoreTokens()) {
				String t = st.nextToken();
				String key = StringUtils.substring(t, 0, StringUtils.indexOf(t, "="));
				String vl = StringUtils.substring(t, StringUtils.indexOf(t, "=") + 1);

				// Parse the value to a boolean
				try {
					boolean bl = Boolean.parseBoolean(vl);
					this.searchOptionsMap.put(key, bl);
				} catch (Exception e) {
					// Error casting string to boolean
				}
			}
		}
    }

    /**
     * Returns the username used to authenticate the user. Cannot return null.
     *
     * @return the username (never null)
     */
    @Override
    @JSON(include=false)
    public final String getUsername() {
        return this.openIdIdentifier;
    }

    /**
     * Returns the password used to authenticate the user. Cannot return null.
     *
     * @return the password (never null)
     */
    @Override
    @JSON(include=false)
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
    @JSON(include=false)
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
    @JSON(include=false)
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
    @JSON(include=false)
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
    @JSON(include=false)
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
        Collection<GrantedAuthority> grantedAuthorities =
                new ArrayList<GrantedAuthority>();
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
     * Gets the search option, returns false by default.
     *
     * @param key the key
     * @return the search option
     */
    public boolean getSearchOption(final String key) {
    	boolean value = false;

    	if (this.getSearchOptionsMap().containsKey(key)) {
    		value = this.getSearchOptionsMap().get(key);
    	}
    	return value;
    }

    /**
     * Gets the search options map.
     *
     * @return the search options map
     */
    public final Map<String, Boolean> getSearchOptionsMap() {
    	if (this.searchOptionsMap == null) {
    		this.searchOptionsMap = new HashMap<String, Boolean>();
    	}
    	return this.searchOptionsMap;
    }

    /**
     * Change the search option.
     *
     * @param key the key
     * @param value the value
     */
    public final void changeSearchOption(final String key, final boolean value) {
    	this.getSearchOptionsMap().put(key, value);

    	StringBuilder sb = new StringBuilder();

    	// Covert the map to the string for persistence purposes
    	for (String optionKey : this.getSearchOptionsMap().keySet()) {
    		boolean optionValue = this.getSearchOptionsMap().get(optionKey);

    		if (sb.length() > 0 ) {
    			sb.append("|");
    		}
    		sb.append(optionKey);
    		sb.append("=");
    		sb.append(optionValue);
    	}
    	this.searchOptions = sb.toString();
    }


    /**
     * Adds an organisation.
     *
     * @param organisation the organisation
     */
    public final void addOrganisation(final Organisation organisation) {
        organisation.addPerson(this);
    }

    /**
     * Adds a data source.
     *
     * @param dataSource the data source
     */
    public final void addDataSource(final DataSource dataSource) {
        dataSource.addPointOfContact(this);
    }

    /**
     * Find an ordered list of people.
     *
     * @return an ordered list of people
     */
    public static List<Person> findAllPeople() {
        return entityManager().createQuery(
                "SELECT o FROM Person o ORDER BY lastName, firstName",
                Person.class).getResultList();
    }
}
