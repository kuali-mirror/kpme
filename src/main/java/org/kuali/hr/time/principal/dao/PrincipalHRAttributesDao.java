package org.kuali.hr.time.principal.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.principal.PrincipalHRAttributes;

public interface PrincipalHRAttributesDao {
	public void saveOrUpdate(PrincipalHRAttributes principalCalendar);

	public void saveOrUpdate(List<PrincipalHRAttributes> lstPrincipalCalendar);

	public PrincipalHRAttributes getPrincipalCalendar(String principalId, Date asOfDate);
}
