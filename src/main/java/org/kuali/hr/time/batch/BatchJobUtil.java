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
package org.kuali.hr.time.batch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class BatchJobUtil {
	
	public static final DateTimeFormatter FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	public static String getJobGroupName(Class<?> jobClass, Map<String, String> jobGroupDataMap) {
		return jobClass.getSimpleName() + "-JobGroup-" + getDataMapString(jobGroupDataMap);
	}
	
	public static String getJobName(Class<?> jobClass, Map<String, String> jobDataMap) {
		return jobClass.getSimpleName() + "-Job-" + getDataMapString(jobDataMap);
	}
	
	public static String getTriggerGroupName(Class<?> jobClass, Map<String, String> triggerDataMap) {
        return jobClass.getSimpleName() + "-TriggerGroup-" + getDataMapString(triggerDataMap);
	}
	
	public static String getTriggerName(Class<?> jobClass, Date jobDate) {
		return jobClass.getSimpleName() + "-Trigger-" + "date=" + FORMAT.print(new DateTime(jobDate));
	}
	
	private static String getDataMapString(Map<String, String> dataMap) {
		List<String> dataMapPairs = new ArrayList<String>();
		for (Map.Entry<String, String> dataMapEntry : dataMap.entrySet()) {
			dataMapPairs.add(dataMapEntry.getKey() + "=" + dataMapEntry.getValue());
		}
		return StringUtils.join(dataMapPairs, "&");
	}

}
