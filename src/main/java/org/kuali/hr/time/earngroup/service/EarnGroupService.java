package org.kuali.hr.time.earngroup.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.earngroup.EarnGroup;

public interface EarnGroupService {
	public EarnGroup getEarnGroup(String earnGroup, Date asOfDate);
	public EarnGroup getEarnGroupForEarnCode(String earnCode, Date asOfDate);
	public List<String> getEarnCodeListForEarnGroup(String earnGroup, Date asOfDate);
	//CAUTION this is used only for the timesheet summary
	public EarnGroup getEarnGroupSummaryForEarnCode(String earnCode, Date asOfDate);
}
