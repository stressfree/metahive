package net.triptech.metahive.web.converter;

import net.triptech.metahive.model.Applicability;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ApplicabilityConverter implements Converter<Applicability, String> {

	@Override
	public String convert(Applicability applicability) {
		return applicability.getMetahiveName();
    }
}
