// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import java.lang.String;

privileged aspect DataSource_Roo_ToString {
    
    public String DataSource.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CollectionDate: ").append(getCollectionDate()).append(", ");
        sb.append("CollectionSource: ").append(getCollectionSource()).append(", ");
        sb.append("ConditionOfUse: ").append(getConditionOfUse()).append(", ");
        sb.append("Definition: ").append(getDefinition()).append(", ");
        sb.append("Details: ").append(getDetails()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Organisation: ").append(getOrganisation()).append(", ");
        sb.append("PointsOfContact: ").append(getPointsOfContact() == null ? "null" : getPointsOfContact().size()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}
