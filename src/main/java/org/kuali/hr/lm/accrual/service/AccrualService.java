package org.kuali.hr.lm.accrual.service;

import java.sql.Date;

public interface AccrualService {
	public void runAccrual(String principalId);
	public void runAccrual(String principalId, Date startDate, Date endDate);
}
