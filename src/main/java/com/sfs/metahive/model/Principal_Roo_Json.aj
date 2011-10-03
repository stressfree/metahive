// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.Principal;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect Principal_Roo_Json {
    
    public String Principal.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Principal Principal.fromJsonToPrincipal(String json) {
        return new JSONDeserializer<Principal>().use(null, Principal.class).deserialize(json);
    }
    
    public static String Principal.toJsonArray(Collection<Principal> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<Principal> Principal.fromJsonArrayToPrincipals(String json) {
        return new JSONDeserializer<List<Principal>>().use(null, ArrayList.class).use("values", Principal.class).deserialize(json);
    }
    
}
