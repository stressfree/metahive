package net.triptech.metahive.web.converter;

import net.triptech.metahive.model.DefinitionType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DefinitionTypeConverter implements Converter<DefinitionType, String> {

    @Autowired
    private ApplicationContext context;

	@Override
	public String convert(DefinitionType definitionType) {
		return context.getMessage(definitionType.getMessageKey(), null,
				LocaleContextHolder.getLocale());
    }
}
