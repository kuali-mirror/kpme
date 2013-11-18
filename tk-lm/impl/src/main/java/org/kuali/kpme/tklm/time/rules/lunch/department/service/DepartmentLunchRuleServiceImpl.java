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
package org.kuali.kpme.tklm.time.rules.lunch.department.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.time.rules.lunch.department.DeptLunchRule;
import org.kuali.kpme.tklm.time.rules.lunch.department.dao.DepartmentLunchRuleDao;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class DepartmentLunchRuleServiceImpl implements DepartmentLunchRuleService {
	public DepartmentLunchRuleDao deptLunchRuleDao;
	private static final Logger LOG = Logger.getLogger(DepartmentLunchRuleServiceImpl.class);

	@Override
	public DeptLunchRule getDepartmentLunchRule(String dept, Long workArea,
			String principalId, Long jobNumber, LocalDate asOfDate) {
		DeptLunchRule deptLunchRule = deptLunchRuleDao.getDepartmentLunchRule(dept, workArea, principalId, jobNumber, asOfDate);
		if(deptLunchRule!=null){
			return deptLunchRule;
		}
		deptLunchRule = deptLunchRuleDao.getDepartmentLunchRule(dept, workArea, principalId, -1L, asOfDate);
		if(deptLunchRule!=null){
			return deptLunchRule;
		}
		deptLunchRule = deptLunchRuleDao.getDepartmentLunchRule(dept, workArea, "%", -1L, asOfDate);

		if(deptLunchRule!=null){
			return deptLunchRule;
		}
		deptLunchRule = deptLunchRuleDao.getDepartmentLunchRule(dept, -1L, principalId, -1L, asOfDate);

		if(deptLunchRule!=null){
			return deptLunchRule;
		}
		deptLunchRule = deptLunchRuleDao.getDepartmentLunchRule(dept, -1L, "%", -1L, asOfDate);
		return deptLunchRule;
	}
	
	@Override
	public DeptLunchRule getDepartmentLunchRuleNoWildCards(String dept, Long workArea,
												String principalId, Long jobNumber, LocalDate asOfDate) {
		return deptLunchRuleDao.getDepartmentLunchRule(dept, workArea, principalId, jobNumber, asOfDate);
	}

	/**
	 * If the hours is greater or equal than the shift hours, deduct the hour from the deduction_mins field
	 */
	@Override
	public void applyDepartmentLunchRule(List<TimeBlock> timeblocks) {
		Map<String,TimeBlock> clockLogEndIdToTimeBlockMap = new HashMap<String,TimeBlock>();
		for(TimeBlock timeBlock : timeblocks) {
            if (timeBlock.isLunchDeleted()) {
                continue;
            }
			TimesheetDocumentHeader doc = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(timeBlock.getDocumentId());
			String dept = HrServiceLocator.getJobService().getJob(doc.getPrincipalId(), timeBlock.getJobNumber(), timeBlock.getBeginDateTime().toLocalDate()).getDept();
			
			DeptLunchRule deptLunchRule = getDepartmentLunchRule(dept, timeBlock.getWorkArea(), doc.getPrincipalId(), timeBlock.getJobNumber(), timeBlock.getBeginDateTime().toLocalDate());
			if(timeBlock.getClockLogCreated() && deptLunchRule!= null && deptLunchRule.getDeductionMins() != null && timeBlock.getHours().compareTo(deptLunchRule.getShiftHours()) >= 0) {
				//KPME-2740 apply lunch deduction to only one of the two time blocks created by an overnight shift.
				if(timeBlock.getClockLogEndId() != null && !clockLogEndIdToTimeBlockMap.containsKey(timeBlock.getClockLogEndId())) {
					applyLunchRuleToDetails(timeBlock, deptLunchRule, clockLogEndIdToTimeBlockMap);
				}
			}
		}
	}
	
    private void applyLunchRuleToDetails(TimeBlock block, DeptLunchRule rule, Map<String, TimeBlock> clockIdToTimeBlockMap) {
        List<TimeHourDetail> details = block.getTimeHourDetails();
        // TODO : Assumption here is that there will be one time hour detail -- May not be accurate.
        if (details.size() == 1) {
            TimeHourDetail detail = details.get(0);

            BigDecimal lunchHours = TKUtils.convertMinutesToHours(rule.getDeductionMins());
            BigDecimal newHours = detail.getHours().subtract(lunchHours).setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING);
            detail.setHours(newHours);

            TimeHourDetail lunchDetail = new TimeHourDetail();
            lunchDetail.setHours(lunchHours.multiply(HrConstants.BIG_DECIMAL_NEGATIVE_ONE));
            lunchDetail.setEarnCode(HrConstants.LUNCH_EARN_CODE);
            lunchDetail.setTkTimeBlockId(block.getTkTimeBlockId());
            
            //Deduct from total for worked hours
            block.setHours(block.getHours().subtract(lunchHours,HrConstants.MATH_CONTEXT));
            
            details.add(lunchDetail);
            clockIdToTimeBlockMap.put(block.getClockLogEndId(), block);
        } else {
            // TODO : Determine what to do in this case.
            LOG.warn("Hour details size > 1 in Lunch rule application.");
        }
    }

	public DepartmentLunchRuleDao getDeptLunchRuleDao() {
		return deptLunchRuleDao;
	}


	public void setDeptLunchRuleDao(DepartmentLunchRuleDao deptLunchRuleDao) {
		this.deptLunchRuleDao = deptLunchRuleDao;
	}

	@Override
	public DeptLunchRule getDepartmentLunchRule(String tkDeptLunchRuleId) {
		return deptLunchRuleDao.getDepartmentLunchRule(tkDeptLunchRuleId);
	}

    @Override
    public List<DeptLunchRule> getDepartmentLunchRules(String userPrincipalId, String dept, String workArea, String principalId, String jobNumber, 
    												   LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
    	List<DeptLunchRule> results = new ArrayList<DeptLunchRule>();
        
    	List<DeptLunchRule> departmentLunchRuleObjs = deptLunchRuleDao.getDepartmentLunchRules(dept, workArea, principalId, jobNumber, fromEffdt, toEffdt, active, showHistory);
    
    	for (DeptLunchRule departmentLunchRuleObj : departmentLunchRuleObjs) {
        	String department = departmentLunchRuleObj.getDept();
        	DepartmentContract departmentObj = HrServiceLocator.getDepartmentService().getDepartmentWithoutRoles(department, departmentLunchRuleObj.getEffectiveLocalDate());
        	String location = departmentObj != null ? departmentObj.getLocation() : null;
        	
        	Map<String, String> roleQualification = new HashMap<String, String>();
        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
        	roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
        	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
        	
        	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
    		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
        		results.add(departmentLunchRuleObj);
        	}
    	}
    	
    	return results;
    }
}
