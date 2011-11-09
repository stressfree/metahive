package com.sfs.metahive.model;

import java.util.List;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The Class MetahivePreferences.
 */
@RooJavaBean
@RooToString
@RooEntity
public class MetahivePreferences {

    /** The url. */
    @NotNull
    @Size(min = 5, max = 200)
    private String url;

    /** The admin email. */
    @NotNull
    @Size(min = 3, max = 200)
    private String adminEmail;

    /** The from email. */
    @NotNull
    @Size(min = 3, max = 200)
    private String fromEmail;

    /** The homepage title. */
    @NotNull
    @Size(min = 2, max = 200)
    private String homepageTitle;

    /** The homepage content. */
    @Lob
    private String homepageContent;
    

    /**
     * Load the preferences.
     *
     * @return the preferences
     */
    public static MetahivePreferences load() {
    	    	
        List<MetahivePreferences> prefs = MetahivePreferences
        		.findAllMetahivePreferenceses();

        MetahivePreferences preferences = prefs.size() == 0 ? null : prefs.get(0);
    	
    	if (preferences == null) {
    		preferences = new MetahivePreferences();
    	}
    	return preferences;
    }
}
