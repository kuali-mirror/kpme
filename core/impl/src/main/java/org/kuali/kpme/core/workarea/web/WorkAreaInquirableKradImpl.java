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
package org.kuali.kpme.core.workarea.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.workarea.WorkAreaContract;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.service.HrServiceLocatorInternal;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.core.workarea.WorkAreaBo;
import org.kuali.rice.krad.inquiry.InquirableImpl;

@SuppressWarnings("deprecation")
public class WorkAreaInquirableKradImpl extends InquirableImpl {

	private static final long serialVersionUID = -4002061046745019065L;

	@Override
	@SuppressWarnings("rawtypes")
	public WorkAreaBo retrieveDataObject(Map fieldValues) {
        WorkAreaBo workAreaObj = null;
        
        if (StringUtils.isNotBlank((String) fieldValues.get("tkWorkAreaId"))) {
            workAreaObj = HrServiceLocatorInternal.getWorkAreaInternalService().getWorkArea((String) fieldValues.get("tkWorkAreaId"));
        } else if (fieldValues.containsKey("workArea") && fieldValues.containsKey("effectiveDate")) {
            String workAreaVal = (String) fieldValues.get("workArea");
            Long workArea = workAreaVal != null ? Long.valueOf(workAreaVal) : null;
            String effDate = (String) fieldValues.get("effectiveDate");
            LocalDate effectiveDate = StringUtils.isBlank(effDate) ? LocalDate.now() : TKUtils.formatDateString(effDate);
            workAreaObj = HrServiceLocatorInternal.getWorkAreaInternalService().getWorkArea(workArea, effectiveDate);
        } else {
	    	 workAreaObj = (WorkAreaBo) super.retrieveDataObject(fieldValues);
        }

		return workAreaObj;
	}
	
}