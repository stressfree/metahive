// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import java.lang.String;

privileged aspect Description_Roo_ToString {

    public String Description.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Created: ").append(getCreated()).append(", ");
        sb.append("Definition: ").append(getDefinition()).append(", ");
        sb.append("Description: ").append(getDescription()).append(", ");
        sb.append("ExampleValues: ").append(getExampleValues()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Person: ").append(getPerson()).append(", ");
        sb.append("SimpleDescription: ").append(getSimpleDescription()).append(", ");
        sb.append("UnitOfMeasure: ").append(getUnitOfMeasure()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }

}
