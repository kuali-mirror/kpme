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
package org.kuali.hr.tklm.time.util;

import java.sql.Timestamp;

import org.apache.ojb.broker.accesslayer.conversions.ConversionException;
import org.apache.ojb.broker.accesslayer.conversions.FieldConversion;
import org.joda.time.LocalDate;

public class TkTimestampConverter implements FieldConversion{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object javaToSql(Object arg0) throws ConversionException {
		if(arg0 == null){
			return new Timestamp(LocalDate.now().toDate().getTime());
		}
		return arg0;
	}

	@Override
	public Object sqlToJava(Object arg0) throws ConversionException {
		return arg0;
	}

}
