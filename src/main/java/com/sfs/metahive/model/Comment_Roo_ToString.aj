// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import java.lang.String;

privileged aspect Comment_Roo_ToString {
    
    public String Comment.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CommentType: ").append(getCommentType()).append(", ");
        sb.append("Created: ").append(getCreated()).append(", ");
        sb.append("DataSourceId: ").append(getDataSourceId()).append(", ");
        sb.append("Definition: ").append(getDefinition()).append(", ");
        sb.append("DescriptionId: ").append(getDescriptionId()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("KeyValueId: ").append(getKeyValueId()).append(", ");
        sb.append("Message: ").append(getMessage()).append(", ");
        sb.append("Person: ").append(getPerson()).append(", ");
        sb.append("Record: ").append(getRecord()).append(", ");
        sb.append("RelatedObject: ").append(getRelatedObject()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}
