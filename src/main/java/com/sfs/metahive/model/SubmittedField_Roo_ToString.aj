// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import java.lang.String;

privileged aspect SubmittedField_Roo_ToString {
    
    public String SubmittedField.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Definition: ").append(getDefinition()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("PrimaryRecordId: ").append(getPrimaryRecordId()).append(", ");
        sb.append("Record: ").append(getRecord()).append(", ");
        sb.append("SecondaryRecordId: ").append(getSecondaryRecordId()).append(", ");
        sb.append("Submission: ").append(getSubmission()).append(", ");
        sb.append("TertiaryRecordId: ").append(getTertiaryRecordId()).append(", ");
        sb.append("Value: ").append(getValue()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}
