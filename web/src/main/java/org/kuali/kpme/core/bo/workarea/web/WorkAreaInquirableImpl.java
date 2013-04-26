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
package org.kuali.kpme.core.bo.workarea.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.workarea.WorkArea;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.common.TKUtils;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

@SuppressWarnings("deprecation")
public class WorkAreaInquirableImpl extends KualiInquirableImpl {

	private static final long serialVersionUID = -4002061046745019065L;

	@Override
	@SuppressWarnings("rawtypes")
	public BusinessObject getBusinessObject(Map fieldValues) {
        WorkArea workAreaObj = null;
        
        if (StringUtils.isNotBlank((String) fieldValues.get("tkWorkAreaId"))) {
            workAreaObj = HrServiceLocator.getWorkAreaService().getWorkArea((String) fieldValues.get("tkWorkAreaId"));
        } else if (fieldValues.containsKey("workArea") && fieldValues.containsKey("effectiveDate")) {
            String workAreaVal = (String) fieldValues.get("workArea");
            Long workArea = workAreaVal != null ? Long.valueOf(workAreaVal) : null;
            LocalDate effectiveDate = TKUtils.formatDateString((String) fieldValues.get("effectiveDate"));
            workAreaObj = HrServiceLocator.getWorkAreaService().getWorkArea(workArea, effectiveDate);
        } else {
	    	 workAreaObj = (WorkArea) super.getBusinessObject(fieldValues);
        }

		return workAreaObj;
	}
	
}