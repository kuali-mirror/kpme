package org.kuali.hr.pm.paystep.dao;

import java.util.List;

import org.kuali.hr.pm.paystep.PayStep;

public interface PayStepDao {

	public PayStep getPayStepById(String payStepId);

	public List<PayStep> getPaySteps(String payStep, String institution, String campus,
			String salaryGroup, String payGrade, String active);

}
