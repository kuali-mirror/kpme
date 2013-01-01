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
package org.kuali.hr.time.principal.service;

import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.principal.dao.PrincipalHRAttributesDao;
import org.kuali.hr.time.service.base.TkServiceLocator;

import java.util.Date;
import java.util.List;

public class PrincipalHRAttributesServiceImpl implements PrincipalHRAttributesService {
	private PrincipalHRAttributesDao principalHRAttributesDao;

	public void setPrincipalHRAttributesDao(PrincipalHRAttributesDao principalHRAttributesDao) {
		this.principalHRAttributesDao = principalHRAttributesDao;
	}
	
	public PrincipalHRAttributes getPrincipalCalendar(String principalId, Date asOfDate){
		PrincipalHRAttributes pc =  this.principalHRAttributesDao.getPrincipalCalendar(principalId, asOfDate);
		if(pc != null) {
			pc.setCalendar(TkServiceLocator.getCalendarService().getCalendarByGroup(pc.getPayCalendar()));
			pc.setLeaveCalObj(TkServiceLocator.getCalendarService().getCalendarByGroup(pc.getLeaveCalendar()));
		}
		return pc;
	}
	
    public List<PrincipalHRAttributes> getActiveEmployeesForPayCalendar(String payCalendarName, Date asOfDate) {
    	return principalHRAttributesDao.getActiveEmployeesForPayCalendar(payCalendarName, asOfDate);
    }
    
    public List<PrincipalHRAttributes> getActiveEmployeesForLeaveCalendar(String leaveCalendarName, Date asOfDate) {
    	return principalHRAttributesDao.getActiveEmployeesForLeaveCalendar(leaveCalendarName, asOfDate);
    }
	
	/**
     * KPME-1250 Kagata
     * Get a list of active employees based on leave plan and as of a particular date
     */
    @Override
    public List<PrincipalHRAttributes> getActiveEmployeesForLeavePlan(String leavePlan, Date asOfDate) {
        List<PrincipalHRAttributes> principals = principalHRAttributesDao.getActiveEmployeesForLeavePlan(leavePlan, asOfDate);

        return principals;
    }
    
//    @Override
//	public PrincipalHRAttributes getPrincipalHRAttributes(String principalId) {
//		return this.principalHRAttributesDao.getPrincipalHRAttributes(principalId);
//	}
    
    @Override
    public PrincipalHRAttributes getInactivePrincipalHRAttributes(String principalId, Date asOfDate) {
    	return this.principalHRAttributesDao.getInactivePrincipalHRAttributes(principalId, asOfDate);
    }
    
    @Override
    public PrincipalHRAttributes getPrincipalHRAttributes(String hrPrincipalAttributeId) {
    	return this.principalHRAttributesDao.getPrincipalHRAttributes(hrPrincipalAttributeId);
    }
    
    @Override
    public List<PrincipalHRAttributes> getAllActivePrincipalHrAttributesForPrincipalId(String principalId, Date asOfDate) {
    	return this.principalHRAttributesDao.getAllActivePrincipalHrAttributesForPrincipalId(principalId, asOfDate);
    }
    @Override
    public PrincipalHRAttributes getMaxTimeStampPrincipalHRAttributes(String principalId) {
    	return principalHRAttributesDao.getMaxTimeStampPrincipalHRAttributes(principalId);
    }
    
    @Override
    public List<PrincipalHRAttributes> getAllInActivePrincipalHrAttributesForPrincipalId(String principalId, Date asOfDate) {
    	return this.principalHRAttributesDao.getAllInActivePrincipalHrAttributesForPrincipalId(principalId, asOfDate);
    }
    @Override
    public List<PrincipalHRAttributes> getActivePrincipalHrAttributesForRange(String principalId, Date startDate, Date endDate) {
    	return this.principalHRAttributesDao.getActivePrincipalHrAttributesForRange(principalId, startDate, endDate);
    }
    @Override
    public List<PrincipalHRAttributes> getInactivePrincipalHRAttributesForRange(String principalId, Date startDate, Date endDate) {
    	return this.principalHRAttributesDao.getInactivePrincipalHRAttributesForRange(principalId, startDate, endDate);
    }
    @Override
    public List<PrincipalHRAttributes> getPrincipalHrAtributes(String principalId, 
    		java.sql.Date fromEffdt, java.sql.Date toEffdt, String active, String showHistory) {
    	return this.principalHRAttributesDao.getPrincipalHrAtributes(principalId, fromEffdt, toEffdt, active, showHistory);
    }
}
