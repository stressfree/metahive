package com.sfs.metahive.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.RooConversionService;

import com.sfs.metahive.model.UserRole;
        
/**
 * A central place to register application Converters and Formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean 
		extends FormattingConversionServiceFactoryBean {

	@Autowired
	private ApplicationContext context;
	
	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
		
		registry.addConverter(getUserRoleConverter());
	}
	

	/**
	 * Gets the user role converter.
	 *
	 * @return the user role converter
	 */
	Converter<UserRole, String> getUserRoleConverter() { 
        return new Converter<UserRole, String>() { 
            public String convert(UserRole userRole) { 
            	System.out.println("Got here");
                return context.getMessage(userRole.getMessageKey(), null, 
                		LocaleContextHolder.getLocale()); 
            } 
        }; 
    }
	
}
