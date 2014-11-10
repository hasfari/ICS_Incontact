package com.incontact.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * ConvertStringToBollean.java
 */
@Converter
public class ConvertStringToBollean implements AttributeConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean value) {
		if (value == null) {
            return null;
        }
        if (value.booleanValue()) {
            return "Y";
        }
        return "N";
	}

	@Override
	public Boolean convertToEntityAttribute(String value) {
		if (value == null) {
            return null;
        }
        if (value.equals("Y") || value.equals("y")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
