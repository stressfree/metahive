// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.ConditionOfUse;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect ConditionOfUse_Roo_Json {
    
    public String ConditionOfUse.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static ConditionOfUse ConditionOfUse.fromJsonToConditionOfUse(String json) {
        return new JSONDeserializer<ConditionOfUse>().use(null, ConditionOfUse.class).deserialize(json);
    }
    
    public static String ConditionOfUse.toJsonArray(Collection<ConditionOfUse> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<ConditionOfUse> ConditionOfUse.fromJsonArrayToConditionOfUses(String json) {
        return new JSONDeserializer<List<ConditionOfUse>>().use(null, ArrayList.class).use("values", ConditionOfUse.class).deserialize(json);
    }
    
}