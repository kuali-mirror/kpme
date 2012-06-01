package org.kuali.hr.lm.leavecode.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.kuali.hr.lm.leavecode.LeaveCode;

public interface LeaveCodeService {
    
    /**
     * Fetch LeaveCode by id
     * @param lmLeaveCodeId
     * @return
     */
    public LeaveCode getLeaveCode(String lmLeaveCodeId);

    List<LeaveCode> getLeaveCodes(String principalId, Date asOfDate);

    Map<String, String> getLeaveCodesForDisplay(String principalId);
    
    public LeaveCode getLeaveCode(String leaveCode, Date effectiveDate);
    /**
	 * use rounding option and fract time allowed of the given Leave Code to round the given hours
	 * @param hours
	 * @param leaveCode
	 */
    public BigDecimal roundHrsWithLeaveCode(BigDecimal hours, LeaveCode leaveCode);
}
