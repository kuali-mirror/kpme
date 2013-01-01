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
package org.kuali.hr.time.rice.kns.web.format;

import java.sql.Time;
import java.text.SimpleDateFormat;

import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.core.web.format.FormatException;
import org.kuali.rice.core.web.format.Formatter;

public class SqlTimeFormatter extends Formatter {

	/**
    * 
    */
	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat sdFormat = new SimpleDateFormat(
			"hh:mm aa");

	static {
		registerFormatter(Time.class, SqlTimeFormatter.class);
	}

	/**
    */
	@Override
	protected Object convertToObject(String source) {
		Object o = null;

		try {
			o = new Time(sdFormat.parse(source).getTime());
		} catch (Exception e) {
			throw new FormatException("parsing", RiceKeyConstants.ERROR_DATE,
					source, e);
		}

		return o;
	}

	@Override
	public Object format(Object source) {
		if (source != null && source instanceof Time) {
			Time time = (Time) source;
			return sdFormat.format(time);
		}

		return null;
	}
}
