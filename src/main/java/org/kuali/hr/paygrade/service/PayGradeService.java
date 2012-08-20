package org.kuali.hr.paygrade.service;

import org.kuali.hr.paygrade.PayGrade;
import org.springframework.cache.annotation.Cacheable;

import java.sql.Date;

public interface PayGradeService {
	/**
	 * Pulls back a particular paygrade as of a particular date
	 * @param payGrade
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= PayGrade.CACHE_NAME, key="'payGrade=' + #p0 + '|' + 'asOfDate=' + #p1")
	public PayGrade getPayGrade(String payGrade, Date asOfDate);
	/**
	 * Get pay grade by a unique id
	 * @param hrPayGradeId
	 * @return
	 */
    @Cacheable(value= PayGrade.CACHE_NAME, key="'hrPayGradeId=' + #p0")
	public PayGrade getPayGrade(String hrPayGradeId);
	/**
	 * get count of pay grade with given payGrade
	 * @param payGrade
	 * @return int
	 */
	public int getPayGradeCount(String payGrade);
}
