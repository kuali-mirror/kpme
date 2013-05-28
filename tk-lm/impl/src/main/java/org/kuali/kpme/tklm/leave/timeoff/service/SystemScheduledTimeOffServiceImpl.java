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
package org.kuali.kpme.tklm.leave.timeoff.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.leave.timeoff.SystemScheduledTimeOff;
import org.kuali.kpme.tklm.leave.timeoff.dao.SystemScheduledTimeOffDao;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class SystemScheduledTimeOffServiceImpl implements SystemScheduledTimeOffService {

	private static final Logger LOG = Logger.getLogger(SystemScheduledTimeOffServiceImpl.class);
	private SystemScheduledTimeOffDao systemScheduledTimeOffDao;

	public SystemScheduledTimeOffDao getSystemScheduledTimeOffDao() {
		return systemScheduledTimeOffDao;
	}

	public void setSystemScheduledTimeOffDao(
			SystemScheduledTimeOffDao systemScheduledTimeOffDao) {
		this.systemScheduledTimeOffDao = systemScheduledTimeOffDao;
	}

	@Override
	public SystemScheduledTimeOff getSystemScheduledTimeOff(String lmSystemScheduledTimeOffId) {
		return getSystemScheduledTimeOffDao().getSystemScheduledTimeOff(lmSystemScheduledTimeOffId);
	}

	@Override
	public List<SystemScheduledTimeOff> getSystemScheduledTimeOffForPayPeriod(
			String leavePlan, LocalDate startDate, LocalDate endDate) {
		return getSystemScheduledTimeOffDao().getSystemScheduledTimeOffForPayPeriod(leavePlan, startDate, endDate);
	}

	@Override
	public SystemScheduledTimeOff getSystemScheduledTimeOffByDate(
			String leavePlan, LocalDate startDate) {
		return getSystemScheduledTimeOffDao().getSystemScheduledTimeOffByDate(leavePlan, startDate);
	}	
	@Override
	public Assignment getAssignmentToApplyHolidays(TimesheetDocument timesheetDocument, LocalDate payEndDate) {
		Job primaryJob = HrServiceLocator.getJobService().getPrimaryJob(timesheetDocument.getPrincipalId(), payEndDate);
		for(Assignment assign : timesheetDocument.getAssignments()){
			if(assign.getJobNumber().equals(primaryJob.getJobNumber())){
				return assign;
			}
		}
		return null;
	}

    @Override
    public BigDecimal calculateSysSchTimeOffHours(Job job, BigDecimal sstoHours) {
        BigDecimal fte = job.getStandardHours().divide(new BigDecimal(40.0),HrConstants.BIG_DECIMAL_SCALE);
        return fte.multiply(sstoHours).setScale(HrConstants.BIG_DECIMAL_SCALE);
    }

    @Override
    public List<SystemScheduledTimeOff> getSystemScheduledTimeOffs(String userPrincipalId, LocalDate fromEffdt, LocalDate toEffdt, String earnCode, LocalDate fromAccruedDate, LocalDate toAccruedDate, 
    		LocalDate fromSchTimeOffDate, LocalDate toSchTimeOffDate, String active, String showHist) {
    	List<SystemScheduledTimeOff> results = new ArrayList<SystemScheduledTimeOff>();
        
    	List<SystemScheduledTimeOff> systemScheduledTimeOffObjs = systemScheduledTimeOffDao.getSystemScheduledTimeOffs(fromEffdt, toEffdt, earnCode, fromAccruedDate, toAccruedDate, fromSchTimeOffDate, 
        															toSchTimeOffDate, active, showHist);
    
    	for (SystemScheduledTimeOff systemScheduledTimeOffObj : systemScheduledTimeOffObjs) {
        	Map<String, String> roleQualification = new HashMap<String, String>();
        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
        	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), systemScheduledTimeOffObj.getLocation());
        	
        	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
    		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
        		results.add(systemScheduledTimeOffObj);
        	}
    	}
    	
    	return results;
    }
    
    @Override
    public List<SystemScheduledTimeOff> getSystemScheduledTimeOffsForLeavePlan(LocalDate fromAccruedDate, LocalDate toAccruedDate, String leavePlan) {
    	return systemScheduledTimeOffDao.getSystemScheduledTimeOffsForLeavePlan(fromAccruedDate, toAccruedDate, leavePlan);
    }

	@Override
	public String getSSTODescriptionForDate(String leavePlan,
			LocalDate localDate) {
		String description = "";
		SystemScheduledTimeOff ssto = systemScheduledTimeOffDao.getSystemScheduledTimeOffByDate(leavePlan, localDate);
		if(ssto != null)
			description = ssto.getDescr();
		return description;
	}

}
