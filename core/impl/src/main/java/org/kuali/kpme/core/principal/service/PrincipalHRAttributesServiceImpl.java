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

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.api.principal.service.PrincipalHRAttributesService;
import org.kuali.kpme.core.principal.PrincipalHRAttributesBo;
import org.kuali.kpme.core.principal.dao.PrincipalHRAttributesDao;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrincipalHRAttributesServiceImpl implements PrincipalHRAttributesService {
	private PrincipalHRAttributesDao principalHRAttributesDao;

	public void setPrincipalHRAttributesDao(PrincipalHRAttributesDao principalHRAttributesDao) {
		this.principalHRAttributesDao = principalHRAttributesDao;
	}
	
	public PrincipalHRAttributes getPrincipalCalendar(String principalId, LocalDate asOfDate){
		PrincipalHRAttributesBo pc =  this.principalHRAttributesDao.getPrincipalCalendar(principalId, asOfDate);
        if (pc == null) {
            return null;
        }
        PrincipalHRAttributes.Builder builder = PrincipalHRAttributes.Builder.create(pc);
        Calendar calendar = HrServiceLocator.getCalendarService().getCalendarByGroup(pc.getPayCalendar());
        Calendar leaveCalendar = HrServiceLocator.getCalendarService().getCalendarByGroup(pc.getLeaveCalendar());
	    if (calendar != null) {
            builder.setCalendar(Calendar.Builder.create(calendar));
        }
        if (leaveCalendar != null) {
            builder.setLeaveCalObj(Calendar.Builder.create(leaveCalendar));
        }
		return builder.build();
	}
	
    public List<PrincipalHRAttributes> getActiveEmployeesForPayCalendar(String payCalendarName, LocalDate asOfDate) {
    	return ModelObjectUtils.transform(principalHRAttributesDao.getActiveEmployeesForPayCalendar(payCalendarName, asOfDate), PrincipalHRAttributesBo.toImmutable);
    }
    
    public List<PrincipalHRAttributes> getActiveEmployeesForLeaveCalendar(String leaveCalendarName, LocalDate asOfDate) {
    	return ModelObjectUtils.transform(principalHRAttributesDao.getActiveEmployeesForLeaveCalendar(leaveCalendarName, asOfDate), PrincipalHRAttributesBo.toImmutable);
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
        return ModelObjectUtils.transform(principalHRAttributesDao.getActiveEmployeesForLeavePlan(leavePlan, asOfDate), PrincipalHRAttributesBo.toImmutable);
    }

    @Override
    public PrincipalHRAttributes getInactivePrincipalHRAttributes(String principalId, LocalDate asOfDate) {
    	return PrincipalHRAttributesBo.to(this.principalHRAttributesDao.getInactivePrincipalHRAttributes(principalId, asOfDate));
    }
    
    @Override
    public PrincipalHRAttributes getPrincipalHRAttributes(String hrPrincipalAttributeId) {
    	return PrincipalHRAttributesBo.to(this.principalHRAttributesDao.getPrincipalHRAttributes(hrPrincipalAttributeId));
    }
    
    @Override
    public List<PrincipalHRAttributes> getAllActivePrincipalHrAttributesForPrincipalId(String principalId, LocalDate asOfDate) {
    	return ModelObjectUtils.transform(this.principalHRAttributesDao.getAllActivePrincipalHrAttributesForPrincipalId(principalId, asOfDate), PrincipalHRAttributesBo.toImmutable);
    }
    @Override
    public PrincipalHRAttributes getMaxTimeStampPrincipalHRAttributes(String principalId) {
    	return PrincipalHRAttributesBo.to(principalHRAttributesDao.getMaxTimeStampPrincipalHRAttributes(principalId));
    }
    
    @Override
    public List<PrincipalHRAttributes> getAllInActivePrincipalHrAttributesForPrincipalId(String principalId, LocalDate asOfDate) {
    	return ModelObjectUtils.transform(this.principalHRAttributesDao.getAllInActivePrincipalHrAttributesForPrincipalId(principalId, asOfDate), PrincipalHRAttributesBo.toImmutable);
    }
    @Override
    public List<PrincipalHRAttributes> getActivePrincipalHrAttributesForRange(String principalId, LocalDate startDate, LocalDate endDate) {
    	return ModelObjectUtils.transform(this.principalHRAttributesDao.getActivePrincipalHrAttributesForRange(principalId, startDate, endDate), PrincipalHRAttributesBo.toImmutable);
    }
    @Override
    public List<PrincipalHRAttributes> getInactivePrincipalHRAttributesForRange(String principalId, LocalDate startDate, LocalDate endDate) {
    	return ModelObjectUtils.transform(this.principalHRAttributesDao.getInactivePrincipalHRAttributesForRange(principalId, startDate, endDate), PrincipalHRAttributesBo.toImmutable);
    }
    @Override
    public List<PrincipalHRAttributes> getPrincipalHrAtributes(String userPrincipalId, String principalId,
                                                               String leavePlan, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
    	List<PrincipalHRAttributesBo> results = new ArrayList<>();
    	
    	List<PrincipalHRAttributesBo> principalHRAttributeObjs = principalHRAttributesDao.getPrincipalHrAtributes(principalId, leavePlan, fromEffdt, toEffdt, active, showHistory);

        //TODO - performance
    	for (PrincipalHRAttributesBo principalHRAttributeObj : principalHRAttributeObjs) {
        	JobContract jobObj = HrServiceLocator.getJobService().getPrimaryJob(principalHRAttributeObj.getPrincipalId(), principalHRAttributeObj.getEffectiveLocalDate());
    		
    		String department = jobObj != null ? jobObj.getDept() : null;
        	Department departmentObj = jobObj != null ? HrServiceLocator.getDepartmentService().getDepartment(department, jobObj.getGroupKeyCode(), jobObj.getEffectiveLocalDate()) : null;
        	String location = departmentObj != null ? departmentObj.getGroupKey().getLocationId() : null;
            String groupKeyCode = departmentObj != null ? departmentObj.getGroupKeyCode() : null;
        	
        	Map<String, String> roleQualification = new HashMap<>();
        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, principalHRAttributeObj.getPrincipalId());
            roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
            roleQualification.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), groupKeyCode);
        	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
        	
        	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
    		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
        		results.add(principalHRAttributeObj);
        	}
    	}
    	
    	return ModelObjectUtils.transform(results, PrincipalHRAttributesBo.toImmutable);
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