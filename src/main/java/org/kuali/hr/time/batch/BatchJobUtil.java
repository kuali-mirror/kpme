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
