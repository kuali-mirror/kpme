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
package org.kuali.hr.time.department.web;


import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.base.web.TkInquirableImpl;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.krad.bo.BusinessObject;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class DepartmentInquirableImpl extends TkInquirableImpl {

	@Override
	public BusinessObject getBusinessObject(Map fieldValues) {
		Department dept = null;
		if(StringUtils.isNotBlank((String)fieldValues.get("hrDeptId"))) {
			dept = TkServiceLocator.getDepartmentService().getDepartment((String) fieldValues.get("hrDeptId"));

		} else if(StringUtils.isNotBlank((String)fieldValues.get("dept"))
					&& StringUtils.isNotBlank((String)fieldValues.get("effectiveDate"))) {
			java.util.Date uDate = null;
			try {
				uDate = new SimpleDateFormat("MM/dd/yyyy").parse(fieldValues.get("effectiveDate").toString());

			    Date effdt = new Date(uDate.getTime());
			    dept = TkServiceLocator.getDepartmentService().getDepartment((String) fieldValues.get("dept"), effdt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
			
		} else {
			dept = (Department) super.getBusinessObject(fieldValues);
		}

        if (dept != null) {
            TkServiceLocator.getDepartmentService().populateDepartmentRoles(dept);
        }
		return dept;
	}
}
