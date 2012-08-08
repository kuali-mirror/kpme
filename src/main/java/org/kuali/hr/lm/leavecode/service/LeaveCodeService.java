package org.kuali.hr.lm.leavecode.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.kuali.hr.lm.leavecode.LeaveCode;
import org.springframework.cache.annotation.Cacheable;

public interface LeaveCodeService {
    
    /**
     * Fetch LeaveCode by id
     * @param lmLeaveCodeId
     * @return
     */
    @Cacheable(value= LeaveCode.CACHE_NAME, key="'lmLeaveCodeId=' + #p0")
    public LeaveCode getLeaveCode(String lmLeaveCodeId);

    @Cacheable(value= LeaveCode.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'asOfDate=' + #p1")
    List<LeaveCode> getLeaveCodes(String principalId, Date asOfDate);

    @Cacheable(value= LeaveCode.CACHE_NAME, key="'principalId=' + #p0")
    Map<String, String> getLeaveCodesForDisplay(String principalId);

    @Cacheable(value= LeaveCode.CACHE_NAME, key="'leaveCode=' + #p0 + '|' + 'effectiveDate=' + #p1")
    public LeaveCode getLeaveCode(String leaveCode, Date effectiveDate);
    /**
	 * use rounding option and fract time allowed of the given Leave Code to round the given hours
	 * @param hours
	 * @param leaveCode
	 */
    public BigDecimal roundHrsWithLeaveCode(BigDecimal hours, LeaveCode leaveCode);
}
