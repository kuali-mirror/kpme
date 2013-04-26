/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.tklm.time.timeblock.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.tklm.common.TkConstants;
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
