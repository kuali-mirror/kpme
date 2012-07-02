package org.kuali.hr.paygrade.service;

import org.kuali.hr.paygrade.PayGrade;

import java.sql.Date;

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
	public PayGrade getPayGrade(String hrPayGradeId);
	/**
	 * get count of pay grade with given payGrade
	 * @param payGrade
	 * @return int
	 */
	public int getPayGradeCount(String payGrade);
}
