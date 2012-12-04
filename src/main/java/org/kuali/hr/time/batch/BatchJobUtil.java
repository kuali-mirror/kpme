/**
 * Copyright 2004-2012 The Kuali Foundation
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

public class BatchJobUtil {
	
	private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	
	public static String getGroupName(Class<?> jobClass, Date jobDate) {
		return jobClass.getSimpleName() + "-Group-" + "date=" + FORMAT.format(jobDate);
	}
	
	public static String getJobName(Class<?> jobClass, Map<String, String> jobDataMap) {
		List<String> jobDataMapPairs = new ArrayList<String>();
		for (Map.Entry<String, String> jobDataMapEntry : jobDataMap.entrySet()) {
			jobDataMapPairs.add(jobDataMapEntry.getKey() + "=" + jobDataMapEntry.getValue());
		}
		return jobClass.getSimpleName() + "-" + StringUtils.join(jobDataMapPairs, "&");
	}
	
	public static String getTriggerName(Class<?> jobClass, Date jobDate) {
        return jobClass.getSimpleName() + "-Trigger-" + "date=" + FORMAT.format(jobDate);
	}

}
