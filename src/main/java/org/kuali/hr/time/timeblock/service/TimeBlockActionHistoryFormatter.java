package org.kuali.hr.time.timeblock.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.web.format.Formatter;

public class TimeBlockActionHistoryFormatter extends Formatter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object format(Object value) {
		if (value != null) {
			String val = (String) value;
			if (StringUtils.equals(TkConstants.ACTIONS.ADD_TIME_BLOCK,
					(String) value)) {
				val = "Add";
			} else if (StringUtils.equals(
					TkConstants.ACTIONS.UPDATE_TIME_BLOCK, (String) value)) {
				val = "Update";
			} else if (StringUtils.equals(
					TkConstants.ACTIONS.DELETE_TIME_BLOCK, (String) value)) {
				val = "Delete";
			}
			return val;
		}
		return super.format(value);
	}

	@Override
	public Object convertFromPresentationFormat(Object value) {
		return super.convertFromPresentationFormat(value);
	}

}
