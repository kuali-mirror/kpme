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
package org.kuali.kpme.core.department.web;


import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.service.HrServiceLocatorInternal;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.krad.inquiry.InquirableImpl;

public class DepartmentInquirableKradImpl extends InquirableImpl {

	private static final long serialVersionUID = 4345521354840709599L;

	@Override
    @SuppressWarnings("rawtypes")
    public DepartmentContract retrieveDataObject(Map fieldValues) {
    	DepartmentBo departmentObj = null;

        if (StringUtils.isNotBlank((String) fieldValues.get("hrDeptId"))) {
            departmentObj = HrServiceLocatorInternal.getDepartmentInternalService().getDepartmentWithRoleData((String) fieldValues.get("hrDeptId"));
        } else if (fieldValues.containsKey("dept") && fieldValues.containsKey("effectiveDate") && fieldValues.containsKey("groupKeyCode")) {
            String department = (String) fieldValues.get("dept");
            String effDate = (String) fieldValues.get("effectiveDate");
            String groupKeyCode = (String) fieldValues.get("groupKeyCode");
            LocalDate effectiveDate = StringUtils.isBlank(effDate) ? LocalDate.now() : TKUtils.formatDateString(effDate);
  
            departmentObj =HrServiceLocatorInternal.getDepartmentInternalService().getDepartmentWithRoleData(department, groupKeyCode, effectiveDate);

        } else {
            departmentObj = (DepartmentBo)super.retrieveDataObject(fieldValues);
        }

        return departmentObj;
    	
    }

}
