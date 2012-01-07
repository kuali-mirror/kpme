package org.kuali.hr.time.accrual.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.base.web.TkForm;

public class TimeOffAccrualActionForm extends TkForm {

	private static final long serialVersionUID = 431293610332309616L;
	
	List<Map<String, Object>> timeOffAccrualsCalc = new ArrayList<Map<String, Object>>();

	public List<Map<String, Object>> getTimeOffAccrualsCalc() {
		return timeOffAccrualsCalc;
	}

	public void setTimeOffAccrualsCalc(List<Map<String, Object>> timeOffAccrualsCalc) {
		this.timeOffAccrualsCalc = timeOffAccrualsCalc;
	}

}
