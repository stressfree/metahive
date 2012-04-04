package net.triptech.metahive.web.converter;

import net.triptech.metahive.model.KeyValueGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class KeyValueGeneratorConverter implements Converter<KeyValueGenerator, String> {

    @Autowired
    private ApplicationContext context;

	@Override
	public String convert(KeyValueGenerator keyValueGenerator) {
		return context.getMessage(keyValueGenerator.getMessageKey(), null,
				LocaleContextHolder.getLocale());
    }
}
