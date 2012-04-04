package net.triptech.metahive.web.converter;

import net.triptech.metahive.model.UserRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserRoleConverter implements Converter<UserRole, String> {

    @Autowired
    private ApplicationContext context;

	@Override
	public String convert(UserRole userRole) {
		return context.getMessage(userRole.getMessageKey(), null,
				LocaleContextHolder.getLocale());
    }
}
