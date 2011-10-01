package org.kuali.hr.time.earngroup.service;

import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earngroup.EarnGroup;

import java.sql.Date;
import java.util.List;
import java.util.Set;

public interface EarnGroupService {
	/**
	 * Fetch earn group for a particular date
	 * @param earnGroup
	 * @param asOfDate
	 * @return
	 */
	public EarnGroup getEarnGroup(String earnGroup, Date asOfDate);
	/**
	 * Fetch earn group for an earn code as of a particular date
	 * @param earnCode
	 * @param asOfDate
	 * @return
	 */
	public EarnGroup getEarnGroupForEarnCode(String earnCode, Date asOfDate);
	/**
	 * Fetch Set of earn codes for earn group
	 * @param earnGroup
	 * @param asOfDate
	 * @return
	 */
	public Set<String> getEarnCodeListForEarnGroup(String earnGroup, Date asOfDate);
	/**
	 * Used to get earn group that this earn code belongs on in context to the summary
	 * CAUTION this is used only for the timesheet summary
	 */
	public EarnGroup getEarnGroupSummaryForEarnCode(String earnCode, Date asOfDate);
	
	public EarnGroup getEarnGroup(Long hrEarnGroupId);

    /**
	 * Fetch Set of earn codes for the overtime earn group
	 * @return
	 */
	public Set<String> getEarnCodeListForOvertimeEarnGroup();
	
	/**
	 * Get Earn Code map for overtime earn group
	 * @return
	 */
    List<EarnCode> getEarnCodeMapForOvertimeEarnGroup();
}
