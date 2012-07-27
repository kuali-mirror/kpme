package org.kuali.hr.lm.leavecode.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.lm.leavecode.dao.LeaveCodeDao;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.krad.util.GlobalVariables;

import com.google.common.collect.Ordering;

public class LeaveCodeServiceImpl implements LeaveCodeService {

	private static final Logger LOG = Logger
			.getLogger(LeaveCodeServiceImpl.class);
	private LeaveCodeDao leaveCodeDao;

	public LeaveCodeDao getLeaveCodeDao() {
		return leaveCodeDao;
	}

	public void setLeaveCodeDao(LeaveCodeDao leaveCodeDao) {
		this.leaveCodeDao = leaveCodeDao;
	}

	@Override
	@CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
	public LeaveCode getLeaveCode(String lmLeaveCodeId) {
		return getLeaveCodeDao().getLeaveCode(lmLeaveCodeId);
	}

	@Override
    public List<LeaveCode> getLeaveCodes(String principalId, Date asOfDate) {
    	String leavePlan = null;
        List<LeaveCode> leaveCodes = new ArrayList<LeaveCode>();
        PrincipalHRAttributes hrAttribute = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
        if(hrAttribute != null) {
        	leavePlan = hrAttribute.getLeavePlan();
        	if (StringUtils.isBlank(leavePlan)) {
        		throw new RuntimeException("No leave plan defined for " + principalId + " in principal hr attributes");
        	}
        	
            List<LeaveCode> unfilteredLeaveCodes = leaveCodeDao.getLeaveCodes(leavePlan, asOfDate);
            TKUser user = TKContext.getUser();

            for (LeaveCode leaveCode : unfilteredLeaveCodes) {
                //if employee add this leave code
                //TODO how do we know this is an approver for them
                if ((leaveCode.getEmployee() && TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isActiveEmployee()) ||
                        (leaveCode.getApprover() && user.isApprover())) {
                    leaveCodes.add(leaveCode);
                }
            }

        }

        return leaveCodes;
    }

	@Override
	@CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
	public Map<String, String> getLeaveCodesForDisplay(String principalId) {
		List<LeaveCode> leaveCodes = getLeaveCodes(principalId,
				TKUtils.getCurrentDate());
		
		// KPME1449, chen, only display leaveCodes which are 'allow_scheduled_leave flag = Y'
		for (LeaveCode leaveCode : leaveCodes) {
			if ( !leaveCode.getAllowScheduledLeave().equalsIgnoreCase("Y")) {
				leaveCodes.remove(leaveCode);
			}
		} // kpme1449

		Comparator<LeaveCode> leaveCodeComparator = new Comparator<LeaveCode>() {
			@Override
			public int compare(LeaveCode leaveCode, LeaveCode leaveCode1) {
				return leaveCode.getLeaveCode().compareToIgnoreCase(
						leaveCode1.getLeaveCode());
			}
		};
		// Order by leaveCode ascending
		Ordering<LeaveCode> ordering = Ordering.from(leaveCodeComparator);

		Map<String, String> leaveCodesForDisplay = new LinkedHashMap<String, String>();
		for (LeaveCode leaveCode : ordering.sortedCopy(leaveCodes)) {
			leaveCodesForDisplay.put(leaveCode.getLeaveCodeKeyForDisplay(),
					leaveCode.getLeaveCodeValueForDisplay());
		}

		return leaveCodesForDisplay;
	}

	@Override
	@CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
	public LeaveCode getLeaveCode(String leaveCode, Date effectiveDate) {
		return leaveCodeDao.getLeaveCode(leaveCode, effectiveDate);
	}
	
	@Override
	public BigDecimal roundHrsWithLeaveCode(BigDecimal hours, LeaveCode leaveCode) {
		String roundOption = LMConstants.ROUND_OPTION_MAP.get(leaveCode.getRoundingOption());
		BigDecimal fractScale = new BigDecimal(leaveCode.getFractionalTimeAllowed());
		if(roundOption == null) {
			throw new RuntimeException("Rounding option of Leave Code " + leaveCode.getLeaveCode() + " is not recognized.");
		}
		BigDecimal roundedHours = hours;
		if(roundOption.equals("Traditional")) {
			roundedHours = hours.setScale(fractScale.scale(), BigDecimal.ROUND_HALF_EVEN);
		} else if(roundOption.equals("Truncate")) {
			roundedHours = hours.setScale(fractScale.scale(), BigDecimal.ROUND_DOWN);
		}
		return roundedHours;
	}
}
