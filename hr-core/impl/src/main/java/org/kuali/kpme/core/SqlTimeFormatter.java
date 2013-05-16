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
package org.kuali.kpme.core;

import java.sql.Time;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.rice.core.web.format.FormatException;
import org.kuali.rice.core.web.format.Formatter;

public class SqlTimeFormatter extends Formatter {

	private static final long serialVersionUID = -6187456990309386413L;
	
	private static final DateTimeFormatter sdFormat = DateTimeFormat.forPattern("hh:mm aa");

	static {
		registerFormatter(Time.class, SqlTimeFormatter.class);
		
		//sdFormat.setLenient(false);
	}

	/**
    */
	@Override
	protected Object convertToObject(String source) {
		Object o = null;

		try {
            o = new Time(LocalTime.parse(source, sdFormat).getMillisOfDay());
		} catch (Exception e) {
			throw new FormatException("parsing", "error.invalidTime", source, e);
		}

		return o;
	}

	@Override
	public Object format(Object source) {
		if (source != null && source instanceof Time) {
			Time time = (Time) source;
			return sdFormat.print(new LocalTime(time));
		}

		return null;
	}
}
