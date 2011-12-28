package org.kuali.hr.time.earngroup.dao;

import org.kuali.hr.time.earngroup.EarnGroup;

import java.sql.Date;

public interface EarnGroupDaoService {
	public EarnGroup getEarnGroup(String earnGroup, Date asOfDate);
	public EarnGroup getEarnGroupForEarnCode(String earnCode, Date asOfDate);
	public EarnGroup getEarnGroupSummaryForEarnCode(String earnCode, Date asOfDate);
	public EarnGroup getEarnGroup(String hrEarnGroupId);
}
