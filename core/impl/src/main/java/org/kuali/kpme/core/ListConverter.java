/**
 * Copyright 2004-2014 The Kuali Foundation
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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.accesslayer.conversions.ConversionException;
import org.apache.ojb.broker.accesslayer.conversions.FieldConversion;

public class ListConverter implements FieldConversion{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object javaToSql(Object arg0) throws ConversionException {
		return arg0;
	}

	// To convert a string that contains selected values in checkbox group control or
	// multi select control in the database table to the back model property, 
	// which is a list type of primitives (usually string)
	@Override
	public Object sqlToJava(Object arg0) throws ConversionException {
		
		String selectedValues = arg0.toString();
		List<String> aList = new ArrayList<String>();
		
		selectedValues = StringUtils.removeStart(selectedValues, "[");
		selectedValues = StringUtils.removeEnd(selectedValues, "]");
		String[] values = StringUtils.split(selectedValues, ",");
		
		for(String value : values){
			aList.add(StringUtils.strip(value, " ")); // strip whitespace from the start and end
		}
		

		return aList;
	}

}
