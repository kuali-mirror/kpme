package org.kuali.hr.paygrade.service;

import java.sql.Date;

import org.kuali.hr.paygrade.PayGrade;

public interface PayGradeService {
	/**
	 * Pulls back a particular paygrade as of a particular date
	 * @param payGrade
	 * @param asOfDate
	 * @return
	 */
	public PayGrade getPayGrade(String payGrade, Date asOfDate);
	/**
	 * Get pay grade by a unique id
	 * @param hrPayGradeId
	 * @return
	 */
	public PayGrade getPayGrade(Long hrPayGradeId);
}
