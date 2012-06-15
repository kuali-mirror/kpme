package org.kuali.hr.time.earncodegroup.dao;

import org.kuali.hr.time.earncodegroup.EarnCodeGroup;

import java.sql.Date;

public interface EarnCodeGroupDaoService {
	public EarnCodeGroup getEarnCodeGroup(String earnCodeGroup, Date asOfDate);
	public EarnCodeGroup getEarnCodeGroupForEarnCode(String earnCode, Date asOfDate);
	public EarnCodeGroup getEarnCodeGroupSummaryForEarnCode(String earnCode, Date asOfDate);
	public EarnCodeGroup getEarnCodeGroup(String hrEarnGroupId);
	public int getEarnCodeGroupCount(String earnGroup);
	public int getNewerEarnCodeGroupCount(String earnGroup, Date effdt);
}
