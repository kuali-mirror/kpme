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
package org.kuali.kpme.core.task;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.task.TaskContract;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class TaskInquirableImpl extends KualiInquirableImpl {
	
	@Override
	public BusinessObject getBusinessObject(Map fieldValues) {
        TaskContract task = null;
        if (StringUtils.isNotBlank((String)fieldValues.get("tkTaskId"))) {
        	task = HrServiceLocator.getTaskService().getTask((String) fieldValues.get("tkTaskId"));
        } else if (fieldValues.containsKey("task")) {
            String taskString = (String)fieldValues.get("task");
            Long taskNumber = taskString != null ? Long.parseLong(taskString) : null;
            String effDate = (String) fieldValues.get("effectiveDate");
            LocalDate effectiveDate = StringUtils.isBlank(effDate) ? LocalDate.now() : TKUtils.formatDateString(effDate);
            task = HrServiceLocator.getTaskService().getTask(taskNumber, effectiveDate);
        } else {
        	task = (Task) super.getBusinessObject(fieldValues);
        }

		return task;
	}

}