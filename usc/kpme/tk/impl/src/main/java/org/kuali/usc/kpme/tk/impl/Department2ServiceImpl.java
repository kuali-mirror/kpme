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
package org.kuali.usc.kpme.tk.impl;

import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.department.service.DepartmentServiceImpl;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.usc.kpme.tk.api.Department2Service;

public class Department2ServiceImpl extends DepartmentServiceImpl
	implements Department2Service {

    public String getDepartmentByCode(String code) throws RiceIllegalArgumentException
    {
    	DepartmentBo departmentBo = this.getDepartmentDao().getDepartment(code);
    	if (departmentBo == null)
    		return "";
    	return departmentBo.getDescription();
    }
	
}