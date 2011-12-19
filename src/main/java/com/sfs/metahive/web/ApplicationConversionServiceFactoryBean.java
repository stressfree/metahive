package com.sfs.metahive.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.RooConversionService;

import com.sfs.metahive.model.DataType;
import com.sfs.metahive.model.KeyValueGenerator;
import com.sfs.metahive.model.UserRole;
import com.sfs.metahive.model.UserStatus;
        
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
		registry.addConverter(getDataTypeConverter());
		registry.addConverter(getKeyValueGeneratorConverter());
		registry.addConverter(getUserRoleConverter());
		registry.addConverter(getUserStatusConverter());
	}

	/**
	 * Gets the data type converter.
	 *
	 * @return the data type converter
	 */
	Converter<DataType, String> getDataTypeConverter() { 
        return new Converter<DataType, String>() { 
            public String convert(DataType dataType) { 
                return context.getMessage(dataType.getMessageKey(), null, 
                		LocaleContextHolder.getLocale()); 
            } 
        }; 
    }

	/**
	 * Gets the key value generator converter.
	 *
	 * @return the key value generator converter
	 */
	Converter<KeyValueGenerator, String> getKeyValueGeneratorConverter() { 
        return new Converter<KeyValueGenerator, String>() { 
            public String convert(KeyValueGenerator keyValueGenerator) { 
                return context.getMessage(keyValueGenerator.getMessageKey(), null, 
                		LocaleContextHolder.getLocale()); 
            } 
        }; 
    }
	
	/**
	 * Gets the user role converter.
	 *
	 * @return the user role converter
	 */
	Converter<UserRole, String> getUserRoleConverter() { 
        return new Converter<UserRole, String>() { 
            public String convert(UserRole userRole) { 
                return context.getMessage(userRole.getMessageKey(), null, 
                		LocaleContextHolder.getLocale()); 
            } 
        }; 
    }

	/**
	 * Gets the user status converter.
	 *
	 * @return the user status converter
	 */
	Converter<UserStatus, String> getUserStatusConverter() { 
        return new Converter<UserStatus, String>() { 
            public String convert(UserStatus userStatus) { 
                return context.getMessage(userStatus.getMessageKey(), null, 
                		LocaleContextHolder.getLocale());
            } 
        }; 
    }
	
}
