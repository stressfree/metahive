package com.sfs.metahive.model;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import com.sfs.metahive.model.Definition;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;

@RooJavaBean
@RooToString
@RooEntity
public class Category {

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Definition> definitions = new HashSet<Definition>();
}
