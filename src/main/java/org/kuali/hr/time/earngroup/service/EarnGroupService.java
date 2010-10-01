package org.kuali.hr.time.earngroup.service;

import java.sql.Date;

import org.kuali.hr.time.earngroup.EarnGroup;

public interface EarnGroupService {
	public EarnGroup getEarnGroup(String earnGroup, Date asOfDate);
	public EarnGroup getEarnGroupSummaryForEarnCode(String earnCode, Date asOfDate);
}
