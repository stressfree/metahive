// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import java.lang.String;

privileged aspect KeyValue_Roo_ToString {
    
    public String KeyValue.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BooleanValue: ").append(getBooleanValue()).append(", ");
        sb.append("ChildKeyValues: ").append(getChildKeyValues() == null ? "null" : getChildKeyValues().size()).append(", ");
        sb.append("Context: ").append(getContext()).append(", ");
        sb.append("CssClass: ").append(getCssClass()).append(", ");
        sb.append("Definition: ").append(getDefinition()).append(", ");
        sb.append("DoubleValue: ").append(getDoubleValue()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("KeyValueType: ").append(getKeyValueType()).append(", ");
        sb.append("PrimaryRecordId: ").append(getPrimaryRecordId()).append(", ");
        sb.append("Record: ").append(getRecord()).append(", ");
        sb.append("SecondaryRecordId: ").append(getSecondaryRecordId()).append(", ");
        sb.append("StringValue: ").append(getStringValue()).append(", ");
        sb.append("TertiaryRecordId: ").append(getTertiaryRecordId()).append(", ");
        sb.append("UserRole: ").append(getUserRole()).append(", ");
        sb.append("Value: ").append(getValue()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}
