package org.kuali.hr.paygrade.service;

import java.sql.Date;

import org.kuali.hr.paygrade.PayGrade;

public interface PayGradeService {
	public PayGrade getPayGrade(String payGrade, Date asOfDate);
}
