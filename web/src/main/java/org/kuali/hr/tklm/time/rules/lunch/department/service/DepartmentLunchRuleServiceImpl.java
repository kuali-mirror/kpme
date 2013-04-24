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
package org.kuali.hr.tklm.time.rules.lunch.department.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.kuali.hr.tklm.time.rules.lunch.department.DeptLunchRule;
import org.kuali.hr.tklm.time.rules.lunch.department.dao.DepartmentLunchRuleDao;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timeblock.TimeBlock;
import org.kuali.hr.tklm.time.timeblock.TimeHourDetail;
import org.kuali.hr.tklm.time.util.TKUtils;
import org.kuali.hr.tklm.time.util.TkConstants;
import org.kuali.hr.tklm.time.workflow.TimesheetDocumentHeader;

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
		deptLunchRule = deptLunchRuleDao.getDepartmentLunchRule(dept, -1L, "%", -1L, asOfDate);

		if(deptLunchRule!=null){
			return deptLunchRule;
		}
		deptLunchRule = deptLunchRuleDao.getDepartmentLunchRule(dept, -1L, principalId, -1L, asOfDate);
		return deptLunchRule;
	}

	/**
	 * If the hours is greater or equal than the shift hours, deduct the hour from the deduction_mins field
	 */
	@Override
	public void applyDepartmentLunchRule(List<TimeBlock> timeblocks) {
		for(TimeBlock timeBlock : timeblocks) {
            if (timeBlock.isLunchDeleted()) {
                continue;
            }
			TimesheetDocumentHeader doc = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(timeBlock.getDocumentId());
			String dept = TkServiceLocator.getJobService().getJob(doc.getPrincipalId(), timeBlock.getJobNumber(), timeBlock.getBeginDateTime().toLocalDate()).getDept();
			
			DeptLunchRule deptLunchRule = getDepartmentLunchRule(dept, timeBlock.getWorkArea(), doc.getPrincipalId(), timeBlock.getJobNumber(), timeBlock.getBeginDateTime().toLocalDate());
			if(timeBlock.getClockLogCreated() && deptLunchRule!= null && deptLunchRule.getDeductionMins() != null && timeBlock.getHours().compareTo(deptLunchRule.getShiftHours()) >= 0) {
                applyLunchRuleToDetails(timeBlock, deptLunchRule);
			}
		}
	}

    private void applyLunchRuleToDetails(TimeBlock block, DeptLunchRule rule) {
        List<TimeHourDetail> details = block.getTimeHourDetails();
        // TODO : Assumption here is that there will be one time hour detail -- May not be accurate.
        if (details.size() == 1) {
            TimeHourDetail detail = details.get(0);

            BigDecimal lunchHours = TKUtils.convertMinutesToHours(rule.getDeductionMins());
            BigDecimal newHours = detail.getHours().subtract(lunchHours).setScale(TkConstants.BIG_DECIMAL_SCALE, TkConstants.BIG_DECIMAL_SCALE_ROUNDING);
            detail.setHours(newHours);

            TimeHourDetail lunchDetail = new TimeHourDetail();
            lunchDetail.setHours(lunchHours.multiply(TkConstants.BIG_DECIMAL_NEGATIVE_ONE));
            lunchDetail.setEarnCode(TkConstants.LUNCH_EARN_CODE);
            lunchDetail.setTkTimeBlockId(block.getTkTimeBlockId());
            
            //Deduct from total for worked hours
            block.setHours(block.getHours().subtract(lunchHours,TkConstants.MATH_CONTEXT));
            
            details.add(lunchDetail);
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
    public List<DeptLunchRule> getDepartmentLunchRules(String dept, String workArea, String principalId, String jobNumber, String active) {
        return deptLunchRuleDao.getDepartmentLunchRules(dept, workArea, principalId, jobNumber, active);
    }
}
