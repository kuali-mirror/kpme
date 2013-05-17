
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
package org.kuali.kpme.core.bo.earncode.security.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.bo.department.Department;
import org.kuali.kpme.core.bo.earncode.security.EarnCodeSecurity;
import org.kuali.kpme.core.bo.earncode.security.dao.EarnCodeSecurityDao;
import org.kuali.kpme.core.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class EarnCodeSecurityServiceImpl implements EarnCodeSecurityService {

    private EarnCodeSecurityDao earnCodeSecurityDao;

	public void setEarnCodeSecurityDao(EarnCodeSecurityDao earnCodeSecurityDao) {
		this.earnCodeSecurityDao = earnCodeSecurityDao;
	}

	@Override
	public List<EarnCodeSecurity> getEarnCodeSecurities(String department, String hrSalGroup, String location, LocalDate asOfDate) {
		return earnCodeSecurityDao.getEarnCodeSecurities(department, hrSalGroup, location, asOfDate);
	}

	@Override
	public EarnCodeSecurity getEarnCodeSecurity(String hrEarnCodeSecId) {
		return earnCodeSecurityDao.getEarnCodeSecurity(hrEarnCodeSecId);
	}

	@Override
	public List<EarnCodeSecurity> searchEarnCodeSecurities(String userPrincipalId, String dept,
			String salGroup, String earnCode, String location, LocalDate fromEffdt,
			LocalDate toEffdt, String active, String showHistory) {
		List<EarnCodeSecurity> results = new ArrayList<EarnCodeSecurity>();
		
 		List<EarnCodeSecurity> earnCodeSecurityObjs = earnCodeSecurityDao.searchEarnCodeSecurities(dept, salGroup, earnCode, location, fromEffdt,
								toEffdt, active, showHistory);
 		
    	for (EarnCodeSecurity earnCodeSecurityObj : earnCodeSecurityObjs) {
        	String department = earnCodeSecurityObj.getDept();
        	Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, earnCodeSecurityObj.getEffectiveLocalDate());
        	String loc = departmentObj != null ? departmentObj.getLocation() : null;
        	
        	Map<String, String> roleQualification = new HashMap<String, String>();
        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
        	roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
        	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), loc);
        	
        	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
    		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
        		results.add(earnCodeSecurityObj);
        	}
    	}
    	
 		return results;
	}
	@Override
	public int getEarnCodeSecurityCount(String dept, String salGroup, String earnCode, String employee, String approver, String location,
			String active, LocalDate effdt, String hrDeptEarnCodeId) {
		return earnCodeSecurityDao.getEarnCodeSecurityCount(dept, salGroup, earnCode, employee, approver, location,
				active, effdt, hrDeptEarnCodeId);
	}
	@Override
	public int getNewerEarnCodeSecurityCount(String earnCode, LocalDate effdt) {
		return earnCodeSecurityDao.getNewerEarnCodeSecurityCount(earnCode, effdt);
	}
}
