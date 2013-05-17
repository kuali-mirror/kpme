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
package org.kuali.kpme.core.bo.department.web;


import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.department.Department;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class DepartmentInquirableImpl extends KualiInquirableImpl {

    @Override
    @SuppressWarnings("rawtypes")
    public BusinessObject getBusinessObject(Map fieldValues) {
        Department departmentObj = null;

        if (StringUtils.isNotBlank((String) fieldValues.get("hrDeptId"))) {
            departmentObj = HrServiceLocator.getDepartmentService().getDepartment((String) fieldValues.get("hrDeptId"));
        } else if (fieldValues.containsKey("dept") && fieldValues.containsKey("effectiveDate")) {
            String department = (String) fieldValues.get("dept");
            LocalDate effectiveDate = TKUtils.formatDateString((String) fieldValues.get("effectiveDate"));
            departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, effectiveDate);
        } else {
            departmentObj = (Department) super.getBusinessObject(fieldValues);
        }

        return departmentObj;
    }
}
