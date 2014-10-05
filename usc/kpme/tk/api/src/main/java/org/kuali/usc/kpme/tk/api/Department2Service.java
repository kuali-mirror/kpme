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
package org.kuali.usc.kpme.tk.api;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.kpme.core.api.department.DepartmentService;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.location.api.LocationConstants;

@WebService(name = "department2Service", targetNamespace = LocationConstants.Namespaces.LOCATION_NAMESPACE_2_0)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface Department2Service extends DepartmentService {
	
    /**
     * Fetch department by id
     * @param hrDeptId
     * @return
     */
//    @Cacheable(value=DepartmentContract.CACHE_NAME, key="'hrDeptId=' + #p0")
//    Department getDepartment(String hrDeptId);
    @WebMethod(operationName="getDepartmentByCode")
    @WebResult(name = "department")
//    @Cacheable(value=Department.Cache.NAME, key="'code=' + #p0")
    String getDepartmentByCode(@WebParam(name = "code") String code) throws RiceIllegalArgumentException;
    
}