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
package org.kuali.kpme.core.principal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.principal.service.PrincipalHRAttributesService;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.principal.dao.PrincipalHRAttributesDao;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class PrincipalHRAttributesServiceImpl implements PrincipalHRAttributesService {
	private PrincipalHRAttributesDao principalHRAttributesDao;

	public void setPrincipalHRAttributesDao(PrincipalHRAttributesDao principalHRAttributesDao) {
		this.principalHRAttributesDao = principalHRAttributesDao;
	}
	
	public PrincipalHRAttributes getPrincipalCalendar(String principalId, LocalDate asOfDate){
		PrincipalHRAttributes pc =  this.principalHRAttributesDao.getPrincipalCalendar(principalId, asOfDate);
		if(pc != null) {
			pc.setCalendar((Calendar)HrServiceLocator.getCalendarService().getCalendarByGroup(pc.getPayCalendar()));
			pc.setLeaveCalObj((Calendar)HrServiceLocator.getCalendarService().getCalendarByGroup(pc.getLeaveCalendar()));
		}
		return pc;
	}
	
    public List<PrincipalHRAttributes> getActiveEmployeesForPayCalendar(String payCalendarName, LocalDate asOfDate) {
    	return principalHRAttributesDao.getActiveEmployeesForPayCalendar(payCalendarName, asOfDate);
    }
    
    public List<PrincipalHRAttributes> getActiveEmployeesForLeaveCalendar(String leaveCalendarName, LocalDate asOfDate) {
    	return principalHRAttributesDao.getActiveEmployeesForLeaveCalendar(leaveCalendarName, asOfDate);
    }
	
    public List<String> getActiveEmployeesIdForLeaveCalendarAndIdList(String leaveCalendarName, List<String> pidList, LocalDate asOfDate) {
    	return principalHRAttributesDao.getActiveEmployeesIdForLeaveCalendarAndIdList(leaveCalendarName, pidList, asOfDate);
    }
    
    public List<String> getActiveEmployeesIdForTimeCalendarAndIdList(String timeCalendarName, List<String> pidList, LocalDate asOfDate) {
    	return principalHRAttributesDao.getActiveEmployeesIdForTimeCalendarAndIdList(timeCalendarName, pidList, asOfDate);
    }
    
	/**
     * KPME-1250 Kagata
     * Get a list of active employees based on leave plan and as of a particular date
     */
    @Override
    public List<PrincipalHRAttributes> getActiveEmployeesForLeavePlan(String leavePlan, LocalDate asOfDate) {
        List<PrincipalHRAttributes> principals = principalHRAttributesDao.getActiveEmployeesForLeavePlan(leavePlan, asOfDate);

        return principals;
    }
    
//    @Override
//	public PrincipalHRAttributes getPrincipalHRAttributes(String principalId) {
//		return this.principalHRAttributesDao.getPrincipalHRAttributes(principalId);
//	}
    
    @Override
    public PrincipalHRAttributes getInactivePrincipalHRAttributes(String principalId, LocalDate asOfDate) {
    	return this.principalHRAttributesDao.getInactivePrincipalHRAttributes(principalId, asOfDate);
    }
    
    @Override
    public PrincipalHRAttributes getPrincipalHRAttributes(String hrPrincipalAttributeId) {
    	return this.principalHRAttributesDao.getPrincipalHRAttributes(hrPrincipalAttributeId);
    }
    
    @Override
    public List<PrincipalHRAttributes> getAllActivePrincipalHrAttributesForPrincipalId(String principalId, LocalDate asOfDate) {
    	return this.principalHRAttributesDao.getAllActivePrincipalHrAttributesForPrincipalId(principalId, asOfDate);
    }
    @Override
    public PrincipalHRAttributes getMaxTimeStampPrincipalHRAttributes(String principalId) {
    	return principalHRAttributesDao.getMaxTimeStampPrincipalHRAttributes(principalId);
    }
    
    @Override
    public List<PrincipalHRAttributes> getAllInActivePrincipalHrAttributesForPrincipalId(String principalId, LocalDate asOfDate) {
    	return this.principalHRAttributesDao.getAllInActivePrincipalHrAttributesForPrincipalId(principalId, asOfDate);
    }
    @Override
    public List<PrincipalHRAttributes> getActivePrincipalHrAttributesForRange(String principalId, LocalDate startDate, LocalDate endDate) {
    	return this.principalHRAttributesDao.getActivePrincipalHrAttributesForRange(principalId, startDate, endDate);
    }
    @Override
    public List<PrincipalHRAttributes> getInactivePrincipalHRAttributesForRange(String principalId, LocalDate startDate, LocalDate endDate) {
    	return this.principalHRAttributesDao.getInactivePrincipalHRAttributesForRange(principalId, startDate, endDate);
    }
    @Override
    public List<PrincipalHRAttributes> getPrincipalHrAtributes(String userPrincipalId, String principalId,
                                                               String leavePlan, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
    	List<PrincipalHRAttributes> results = new ArrayList<PrincipalHRAttributes>();
    	
    	List<PrincipalHRAttributes> principalHRAttributeObjs = principalHRAttributesDao.getPrincipalHrAtributes(principalId, leavePlan, fromEffdt, toEffdt, active, showHistory);

        //TODO - performance
    	for (PrincipalHRAttributes principalHRAttributeObj : principalHRAttributeObjs) {
        	JobContract jobObj = HrServiceLocator.getJobService().getPrimaryJob(principalHRAttributeObj.getPrincipalId(), principalHRAttributeObj.getEffectiveLocalDate());
    		
    		String department = jobObj != null ? jobObj.getDept() : null;
        	DepartmentContract departmentObj = jobObj != null ? HrServiceLocator.getDepartmentService().getDepartmentWithoutRoles(department, jobObj.getEffectiveLocalDate()) : null;
        	String location = departmentObj != null ? departmentObj.getLocation() : null;
        	
        	Map<String, String> roleQualification = new HashMap<String, String>();
        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, principalHRAttributeObj.getPrincipalId());
        	roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
        	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
        	
        	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
    		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
        		results.add(principalHRAttributeObj);
        	}
    	}
    	
    	return results;
    }

    @Override
    public List<String> getUniqueLeaveCalendars(List<String> principalIds) {
        return this.principalHRAttributesDao.getUniqueLeaveCalendars(principalIds);
    }
    
    @Override
    public List<String> getUniquePayCalendars(List<String> principalIds) {
    	return this.principalHRAttributesDao.getUniquePayCalendars(principalIds);
    }
    
}