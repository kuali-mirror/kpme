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
package org.kuali.kpme.core.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.kpme.core.api.KPMEConstants;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

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
	
	public static String getTriggerName(Class<?> jobClass, DateTime jobDate) {
		return jobClass.getSimpleName() + "-Trigger-" + "date=" + FORMAT.print(jobDate);
	}
	
	private static String getDataMapString(Map<String, String> dataMap) {
		List<String> dataMapPairs = new ArrayList<String>();
		for (Map.Entry<String, String> dataMapEntry : dataMap.entrySet()) {
			dataMapPairs.add(dataMapEntry.getKey() + "=" + dataMapEntry.getValue());
		}
		return StringUtils.join(dataMapPairs, "&");
	}

    public static String getBatchUserPrincipalName() {
        return ConfigContext.getCurrentContextConfig().getProperty(KPMEConstants.BATCH_USER_PRINCIPAL_NAME);
    }

    public static String getBatchUserPrincipalId() {
        String principalName = ConfigContext.getCurrentContextConfig().getProperty(KPMEConstants.BATCH_USER_PRINCIPAL_NAME);
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(principalName);
        return principal == null ? null : principal.getPrincipalId();
    }

}
