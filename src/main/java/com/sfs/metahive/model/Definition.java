package com.sfs.metahive.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findDefinitionsByNameLike" })
public class Definition {

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @Lob
    private String description;

    private String exampleValues;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "definitions")
    private Set<Category> categories = new HashSet<Category>();
}
