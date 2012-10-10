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
package org.kuali.hr.time.workarea.web;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class WorkAreaInquirableImpl extends KualiInquirableImpl {

	@Override
	public BusinessObject getBusinessObject(Map fieldValues) {
        WorkArea workArea = null;
        if (StringUtils.isNotBlank((String)fieldValues.get("tkWorkAreaId"))) {
            workArea = TkServiceLocator.getWorkAreaService().getWorkArea((String)fieldValues.get("tkWorkAreaId"));
        } else if (fieldValues.containsKey("workArea") && fieldValues.containsKey("effectiveDate")) {
            String workAreaVal = (String)fieldValues.get("workArea");
            Long wa = workAreaVal != null ? Long.parseLong(workAreaVal) : null;
            workArea = TkServiceLocator.getWorkAreaService().getWorkArea(wa,
                    new java.sql.Date(TKUtils.convertDateStringToTimestampNoTimezone((String)fieldValues.get("effectiveDate")).getTime()));
        } else {
	    	 workArea = (WorkArea) super.getBusinessObject(fieldValues);
        }

        if (workArea != null) {
		    TkServiceLocator.getWorkAreaService().populateWorkAreaRoles(workArea);
        }

		return workArea;
	}
}
