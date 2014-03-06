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
package org.kuali.kpme.tklm.time.rules.clocklocation.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.rules.clocklocation.ClockLocationRule;
import org.kuali.kpme.tklm.time.rules.clocklocation.ClockLocationRuleIpAddress;
import org.kuali.kpme.tklm.time.rules.clocklocation.dao.ClockLocationDao;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class ClockLocationRuleServiceImpl implements ClockLocationRuleService {
	private ClockLocationDao clockLocationDao;

	public ClockLocationDao getClockLocationDao() {
		return clockLocationDao;
	}

	public void setClockLocationDao(ClockLocationDao clockLocationDao) {
		this.clockLocationDao = clockLocationDao;
	}

	public void processClockLocationRule(ClockLog clockLog, LocalDate asOfDate){
		List<ClockLocationRule> lstClockLocationRules = getClockLocationRule(clockLog.getJob().getDept(),
										clockLog.getWorkArea(), clockLog.getPrincipalId(), clockLog.getJobNumber(), asOfDate);
		if(lstClockLocationRules.isEmpty()){
			return;
		}
		for(ClockLocationRule clockLocationRule : lstClockLocationRules){
			List<ClockLocationRuleIpAddress> ruleIpAddresses = clockLocationRule.getIpAddresses();
			String ipAddressClock = clockLog.getIpAddress();
			for(ClockLocationRuleIpAddress ruleIp : ruleIpAddresses) {
				if(compareIpAddresses(ruleIp.getIpAddress(), ipAddressClock)){
					clockLog.setUnapprovedIP(false);
					return;
				}
			}
		}
		clockLog.setUnapprovedIP(true);
		GlobalVariables.getMessageMap().putWarning("property", "ipaddress.invalid.format", clockLog.getIpAddress());

	}
	
	public boolean isInValidIPClockLocation(String dept, Long workArea,String principalId, Long jobNumber, String ipAddress, LocalDate asOfDate){
		Boolean isInValid = true;
		List<ClockLocationRule> lstClockLocationRules = getClockLocationRule(dept, workArea, principalId, jobNumber, asOfDate);
		if(lstClockLocationRules.isEmpty()){
			isInValid = false;
			return isInValid;
		}
		for(ClockLocationRule clockLocationRule : lstClockLocationRules){
			List<ClockLocationRuleIpAddress> ruleIpAddresses = clockLocationRule.getIpAddresses();
			String ipAddressClock = ipAddress;
			for(ClockLocationRuleIpAddress ruleIp : ruleIpAddresses) {
				if(compareIpAddresses(ruleIp.getIpAddress(), ipAddressClock)){
					isInValid = false;
					break;
				}
			}
		}
		return isInValid;

	}

	private boolean compareIpAddresses(String ipAddressRule, String ipAddress){
		String[] rulePieces = StringUtils.split(ipAddressRule, ".");
        int ruleMax = rulePieces.length-1;

		String[] ipAddPieces = StringUtils.split(ipAddress,".");
		boolean match = true;
		for(int i=0; i<ipAddPieces.length; i++){
			if( ((i > ruleMax) && StringUtils.equals("%", rulePieces[ruleMax])) ||
                  ((i <= ruleMax) && ( StringUtils.equals(ipAddPieces[i], rulePieces[i]) || StringUtils.equals("%", rulePieces[i]) ))
                )
            {
				// we don't need to do anything.
			} else {
			    return false;
			}
		}
		return match;
	}

	@Override
	public List<ClockLocationRule> getClockLocationRule(String dept, Long workArea,String principalId, Long jobNumber, LocalDate asOfDate) {

        // 1 : dept, wa, principal, job
		List<ClockLocationRule> clockLocationRule = clockLocationDao.getClockLocationRule(dept, workArea,principalId,jobNumber,asOfDate);
		if(!clockLocationRule.isEmpty()){
			return clockLocationRule;
		}

        // 2 : dept, wa, principal, -1
		clockLocationRule = clockLocationDao.getClockLocationRule(dept, workArea, principalId, -1L, asOfDate);
		if(!clockLocationRule.isEmpty()){
			return clockLocationRule;
		}

        // 3 : dept, wa, %        , job
        clockLocationRule = clockLocationDao.getClockLocationRule(dept, workArea, "%", jobNumber, asOfDate);
        if(!clockLocationRule.isEmpty()){
            return clockLocationRule;
        }

        // 4 : dept, -1, principal, job
        clockLocationRule = clockLocationDao.getClockLocationRule(dept, -1L, principalId, jobNumber, asOfDate);
        if(!clockLocationRule.isEmpty()){
            return clockLocationRule;
        }

        // 5 : dept, wa, %        , -1
		clockLocationRule = clockLocationDao.getClockLocationRule(dept, workArea, "%", -1L, asOfDate);
		if(!clockLocationRule.isEmpty()){
			return clockLocationRule;
		}

        // 6 : dept, -1, principal, -1
        clockLocationRule = clockLocationDao.getClockLocationRule(dept, -1L, principalId, -1L, asOfDate);
        if(!clockLocationRule.isEmpty()){
            return clockLocationRule;
        }

        // 7 : dept, -1, %        , job
        clockLocationRule = clockLocationDao.getClockLocationRule(dept, -1L, "%", jobNumber, asOfDate);
        if(!clockLocationRule.isEmpty()){
            return clockLocationRule;
        }

        // 8 : dept, -1, %        , job
		clockLocationRule = clockLocationDao.getClockLocationRule(dept, -1L, "%", -1L, asOfDate);
		return clockLocationRule;
	}

	@Override
	public List<ClockLocationRule> getNewerVersionClockLocationRule(
			String dept, Long workArea, String principalId, Long jobNumber,
			LocalDate asOfDate) {
		 
		return clockLocationDao.getNewerVersionClockLocationRule(dept, workArea, principalId, jobNumber, asOfDate);
	}

	@Override
	public ClockLocationRule getClockLocationRule(String tkClockLocationRuleId) {
		return clockLocationDao.getClockLocationRule(tkClockLocationRuleId);
	}
	
	public void populateIPAddressesForCLR(ClockLocationRule clr){
		clockLocationDao.populateIPAddressesForCLR(clr);
	}

    public List<ClockLocationRule> getClockLocationRules(String userPrincipalId, LocalDate fromEffdt, LocalDate toEffdt, String principalId, String jobNumber,
                                                         String dept, String workArea, String active, String showHistory){
    	List<ClockLocationRule> results = new ArrayList<ClockLocationRule>();
    	
    	List<ClockLocationRule> clockLocationRuleObjs = clockLocationDao.getClockLocationRules(fromEffdt, toEffdt, principalId, jobNumber, dept, workArea, active, showHistory);
    
    	for (ClockLocationRule clockLocationRuleObj : clockLocationRuleObjs) {
        	String department = clockLocationRuleObj.getDept();
        	Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, clockLocationRuleObj.getEffectiveLocalDate());
        	String location = departmentObj != null ? departmentObj.getLocation() : null;
        	
        	Map<String, String> roleQualification = new HashMap<String, String>();
        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
        	roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
        	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
        	
        	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
    		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
        		results.add(clockLocationRuleObj);
        	}
    	}
    	
    	return results;
    }
}
