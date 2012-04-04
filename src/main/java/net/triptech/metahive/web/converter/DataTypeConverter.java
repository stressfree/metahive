package net.triptech.metahive.web.converter;

import net.triptech.metahive.model.DataType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DataTypeConverter implements Converter<DataType, String> {

    @Autowired
    private ApplicationContext context;

	@Override
	public String convert(DataType dataType) {
		return context.getMessage(dataType.getMessageKey(), null,
				LocaleContextHolder.getLocale());
    }
}