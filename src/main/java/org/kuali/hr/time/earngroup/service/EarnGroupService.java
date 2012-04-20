package org.kuali.hr.time.earngroup.service;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.timesheet.TimesheetDocument;

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
	
	public EarnGroup getEarnGroup(String hrEarnGroupId);
	
	/**
	 * Returns list of warning text from earn group that is used by time blocks of the timesheetDocument
	 * @param timesheetDocument
	 * @return
	 */
	public List<String> warningTextFromEarnGroupsOfDocument(TimesheetDocument timesheetDocument);
    /**
     * get the count of earn groups by given earnGroup
     * @param earnGroup
     * @return int
     */
    public int getEarnGroupCount(String earnGroup);
	
}
