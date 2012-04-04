package net.triptech.metahive.web.converter;

import net.triptech.metahive.model.UserStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserStatusConverter implements Converter<UserStatus, String> {

    @Autowired
    private ApplicationContext context;

	@Override
	public String convert(UserStatus userStatus) {
		return context.getMessage(userStatus.getMessageKey(), null,
				LocaleContextHolder.getLocale());
    }
}
