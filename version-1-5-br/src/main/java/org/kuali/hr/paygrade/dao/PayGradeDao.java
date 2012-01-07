package org.kuali.hr.paygrade.dao;

import java.sql.Date;

import org.kuali.hr.paygrade.PayGrade;

public interface PayGradeDao {
	/**
	 * Get paygrade as of a particular date
	 * @param payGrade
	 * @param asOfDate
	 * @return
	 */
	public PayGrade getPayGrade(String payGrade,Date asOfDate);
	/**
	 * Get paygrade by unique id
	 * @param hrPayGradeId
	 * @return
	 */
	public PayGrade getPayGrade(String hrPayGradeId);
}
