package org.kuali.hr.time.dept.lunch.service;

import org.apache.log4j.Logger;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.dept.lunch.dao.DepartmentLunchRuleDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class DepartmentLunchRuleServiceImpl implements DepartmentLunchRuleService {
	public DepartmentLunchRuleDao deptLunchRuleDao;
	private static final Logger LOG = Logger.getLogger(DepartmentLunchRuleServiceImpl.class);

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
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
			String dept = TkServiceLocator.getJobSerivce().getJob(doc.getPrincipalId(), timeBlock.getJobNumber(), new java.sql.Date(timeBlock.getBeginTimestamp().getTime())).getDept();
			DeptLunchRule deptLunchRule = TkServiceLocator.getDepartmentLunchRuleService().getDepartmentLunchRule(dept, timeBlock.getWorkArea(), doc.getPrincipalId(), timeBlock.getJobNumber(), new java.sql.Date(timeBlock.getBeginTimestamp().getTime()));
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
            BigDecimal newHours = detail.getHours().subtract(lunchHours).setScale(TkConstants.BIG_DECIMAL_SCALE);
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
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public DeptLunchRule getDepartmentLunchRule(String tkDeptLunchRuleId) {
		return deptLunchRuleDao.getDepartmentLunchRule(tkDeptLunchRuleId);
	}

}
