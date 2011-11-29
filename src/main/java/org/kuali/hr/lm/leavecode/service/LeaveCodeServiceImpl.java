package org.kuali.hr.lm.leavecode.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.lm.leavecode.dao.LeaveCodeDao;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;

public class LeaveCodeServiceImpl implements LeaveCodeService {

	private static final Logger LOG = Logger.getLogger(LeaveCodeServiceImpl.class);
	private LeaveCodeDao leaveCodeDao;


    public LeaveCodeDao getLeaveCodeDao() {
		return leaveCodeDao;
	}


	public void setLeaveCodeDao(LeaveCodeDao leaveCodeDao) {
		this.leaveCodeDao = leaveCodeDao;
	}


	@Override
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public LeaveCode getLeaveCode(Long lmLeaveCodeId) {
		return getLeaveCodeDao().getLeaveCode(lmLeaveCodeId);
	}
	
	public List<LeaveCode> getLeaveCodes(String principalId, Date asOfDate){
		List<LeaveCode> leaveCodes = new ArrayList<LeaveCode>();
		PrincipalHRAttributes hrAttribute = TkServiceLocator.getPrincipalHRAttributesService().getPrincipalCalendar(principalId, asOfDate);
		String leavePlan = hrAttribute.getLeavePlan();
		if(StringUtils.isBlank(leavePlan)){
			throw new RuntimeException("No leave plan defined for "+principalId + " in princpal hr attributes");
		}
		
		List<LeaveCode> unfilteredLeaveCodes = leaveCodeDao.getLeaveCodes(principalId, leavePlan, asOfDate);
		TKUser user = TKContext.getUser();
		
		for(LeaveCode leaveCode : unfilteredLeaveCodes){
			//if employee add this leave code
			//TODO how do we know this is an approver for them
			if((leaveCode.getEmployee() && user.getCurrentRoles().isActiveEmployee()) || 
					(leaveCode.getApprover() && user.isApprover())){
				leaveCodes.add(leaveCode);
			} 	
		}        
		return leaveCodes;
		
	}
	
   

}
