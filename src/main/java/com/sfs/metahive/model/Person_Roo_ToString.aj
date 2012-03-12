// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import java.lang.String;

privileged aspect Person_Roo_ToString {
    
    public String Person.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Authorities: ").append(getAuthorities() == null ? "null" : getAuthorities().size()).append(", ");
        sb.append("Comments: ").append(getComments() == null ? "null" : getComments().size()).append(", ");
        sb.append("DataSources: ").append(getDataSources() == null ? "null" : getDataSources().size()).append(", ");
        sb.append("Descriptions: ").append(getDescriptions() == null ? "null" : getDescriptions().size()).append(", ");
        sb.append("EmailAddress: ").append(getEmailAddress()).append(", ");
        sb.append("FirstName: ").append(getFirstName()).append(", ");
        sb.append("FormattedName: ").append(getFormattedName()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("LastName: ").append(getLastName()).append(", ");
        sb.append("OpenIdIdentifier: ").append(getOpenIdIdentifier()).append(", ");
        sb.append("Organisations: ").append(getOrganisations() == null ? "null" : getOrganisations().size()).append(", ");
        sb.append("Password: ").append(getPassword()).append(", ");
        sb.append("SearchDefinitions: ").append(getSearchDefinitions() == null ? "null" : getSearchDefinitions().size()).append(", ");
        sb.append("SearchOptions: ").append(getSearchOptions()).append(", ");
        sb.append("SearchOptionsMap: ").append(getSearchOptionsMap() == null ? "null" : getSearchOptionsMap().size()).append(", ");
        sb.append("Submissions: ").append(getSubmissions() == null ? "null" : getSubmissions().size()).append(", ");
        sb.append("UserRole: ").append(getUserRole()).append(", ");
        sb.append("UserStatus: ").append(getUserStatus()).append(", ");
        sb.append("Username: ").append(getUsername()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("AccountNonExpired: ").append(isAccountNonExpired()).append(", ");
        sb.append("AccountNonLocked: ").append(isAccountNonLocked()).append(", ");
        sb.append("CredentialsNonExpired: ").append(isCredentialsNonExpired()).append(", ");
        sb.append("Enabled: ").append(isEnabled()).append(", ");
        sb.append("ExpandAllDefinitions: ").append(isExpandAllDefinitions()).append(", ");
        sb.append("ShowAllDefinitions: ").append(isShowAllDefinitions());
        return sb.toString();
    }
    
}
