package org.kuali.hr.lm.earncodesec.dao;

import org.kuali.hr.lm.earncodesec.EarnCodeSecurity;

import java.util.Date;
import java.util.List;

public interface EarnCodeSecurityDao {

	public void saveOrUpdate(EarnCodeSecurity earnCodeSecurity);

	public void saveOrUpdate(List<EarnCodeSecurity> earnCodeSecList);

	public List<EarnCodeSecurity> getEarnCodeSecurities(String department, String hr_sal_group, String location, Date asOfDate);

	public EarnCodeSecurity getEarnCodeSecurity(String hrEarnCodeSecId);
	
	public List<EarnCodeSecurity> searchEarnCodeSecurities(String dept, String salGroup, String earnCode, String location,
			java.sql.Date fromEffdt, java.sql.Date toEffdt, String active, String showHistory);
	
	public int getEarnCodeSecurityCount(String dept, String salGroup, String earnCode, String employee, String approver, String location,
			String active, java.sql.Date effdt,String hrDeptEarnCodeId);
	
	public int getNewerEarnCodeSecurityCount(String earnCode, Date effdt);
}
