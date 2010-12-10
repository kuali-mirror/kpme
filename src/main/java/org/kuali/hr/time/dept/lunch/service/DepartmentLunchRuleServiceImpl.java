package org.kuali.hr.time.dept.lunch.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.dept.lunch.dao.DepartmentLunchRuleDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

public class DepartmentLunchRuleServiceImpl implements DepartmentLunchRuleService {
	public DepartmentLunchRuleDao deptLunchRuleDao;
	private static final Logger LOG = Logger.getLogger(DepartmentLunchRuleServiceImpl.class);
	
	@Override
	public DeptLunchRule getDepartmentLunchRule(String dept, Long workArea,
			String principalId, Long jobNumber, Date asOfDate) {
		DeptLunchRule deptLunchRule = deptLunchRuleDao.getDepartmentLunchRule(dept, workArea, principalId, jobNumber, asOfDate);
		if(deptLunchRule!=null){
			return deptLunchRule;
		}
		deptLunchRule = deptLunchRuleDao.getDepartmentLunchRule(dept, workArea, principalId, -1L, asOfDate);
		if(deptLunchRule!=null){
			return deptLunchRule;
		}
		deptLunchRule = deptLunchRuleDao.getDepartmentLunchRule(dept, workArea, "*", -1L, asOfDate);
		
		if(deptLunchRule!=null){
			return deptLunchRule;
		}
		deptLunchRule = deptLunchRuleDao.getDepartmentLunchRule(dept, -1L, "*", -1L, asOfDate);
		
		if(deptLunchRule!=null){
			return deptLunchRule;
		}
		deptLunchRule = deptLunchRuleDao.getDepartmentLunchRule(dept, -1L, principalId, -1L, asOfDate);
		return deptLunchRule;
	}
	/**
	 * if the worked time is greater or equal than the shift hours, deduct the time from the deduction_mins field 
	 * @param timeBlock
	 * @param deptLunchRule
	 * @return hoursAfterDeduction
	 */
	@Override
	public List<TimeBlock> applyDepartmentLunchRule(List<TimeBlock> timeblocks) {
		
		List<TimeBlock> listNewTimeBlocks = new LinkedList<TimeBlock>();
	
		for(TimeBlock timeBlock : timeblocks) {
			String dept = TkServiceLocator.getJobSerivce().getJob(TKContext.getPrincipalId(), timeBlock.getJobNumber(), new java.sql.Date(timeBlock.getBeginTimestamp().getTime())).getDept();
			DeptLunchRule deptLunchRule = TkServiceLocator.getDepartmentLunchRuleService().getDepartmentLunchRule(
					dept, timeBlock.getWorkArea(), TKContext.getPrincipalId(), timeBlock.getJobNumber(), new java.sql.Date(timeBlock.getBeginTimestamp().getTime()));
			
			if(deptLunchRule!= null && deptLunchRule.getDeductionMins() != null && timeBlock.getHours().compareTo(deptLunchRule.getShiftHours()) >= 0) {
				// convert deduction minutes to hours 
				BigDecimal hours = timeBlock.getHours().subtract(TKUtils.convertMinutesToHours(deptLunchRule.getDeductionMins()));
				timeBlock.setHours(hours);
			}
			
			listNewTimeBlocks.add(timeBlock);
		}
		return listNewTimeBlocks;
	}

	public DepartmentLunchRuleDao getDeptLunchRuleDao() {
		return deptLunchRuleDao;
	}


	public void setDeptLunchRuleDao(DepartmentLunchRuleDao deptLunchRuleDao) {
		this.deptLunchRuleDao = deptLunchRuleDao;
	}

}
