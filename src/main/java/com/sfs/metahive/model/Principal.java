package com.sfs.metahive.model;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
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

@RooJavaBean
@RooToString
@RooEntity(
		identifierColumn = "id", 
		table = "principal", 
		finders = { "findPrincipalsByOpenIdIdentifier" })
public class Principal implements UserDetails {

	private static final long serialVersionUID = 13513413L;

    @NotNull
    @Column(name = "openid_identifier")
    private String openIdIdentifier;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_address")
    private String emailAddress;
    
    @Override
    public String getUsername() {
        return this.openIdIdentifier;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        grantedAuthorities.add(
                new GrantedAuthorityImpl(this.userRole.name()));
        return grantedAuthorities;
    }
}
