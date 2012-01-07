package org.kuali.hr.job;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kns.web.format.Formatter;

public class JobNumberFormatter extends Formatter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object format(Object value) {
		if(value != null){
			Long val = (Long)value;
			if(val == -1L){
				return "%";
			}
			return val.toString();
		}
		return super.format(value);
	}
	
	@Override
	public Object convertFromPresentationFormat(Object value) {
		if(value instanceof String){
			if(StringUtils.isNotEmpty((String)value) && !StringUtils.equals("%", (String)value)){
				Long val = Long.parseLong((String)value);
				return val;
			}
		} 	
		return super.convertFromPresentationFormat(value);
	}	
}
