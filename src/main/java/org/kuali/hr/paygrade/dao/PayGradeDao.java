package org.kuali.hr.paygrade.dao;

import java.sql.Date;

import org.kuali.hr.paygrade.PayGrade;

public interface PayGradeDao {
	public PayGrade getPayGrade(String payGrade,Date asOfDate);
}
