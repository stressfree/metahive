// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.triptech.metahive.model.Submission;

privileged aspect Submission_Roo_Json {
    
    public String Submission.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Submission Submission.fromJsonToSubmission(String json) {
        return new JSONDeserializer<Submission>().use(null, Submission.class).deserialize(json);
    }
    
    public static String Submission.toJsonArray(Collection<Submission> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<Submission> Submission.fromJsonArrayToSubmissions(String json) {
        return new JSONDeserializer<List<Submission>>().use(null, ArrayList.class).use("values", Submission.class).deserialize(json);
    }
    
}