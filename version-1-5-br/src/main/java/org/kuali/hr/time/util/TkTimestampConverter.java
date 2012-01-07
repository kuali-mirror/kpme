package org.kuali.hr.time.util;

import java.sql.Timestamp;

import org.apache.ojb.broker.accesslayer.conversions.ConversionException;
import org.apache.ojb.broker.accesslayer.conversions.FieldConversion;

public class TkTimestampConverter implements FieldConversion{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object javaToSql(Object arg0) throws ConversionException {
		if(arg0 == null){
			return new Timestamp(TKUtils.getCurrentDate().getTime());
		}
		return arg0;
	}

	@Override
	public Object sqlToJava(Object arg0) throws ConversionException {
		return arg0;
	}

}
