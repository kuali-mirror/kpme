package org.kuali.hr.time.detail.web;

import org.kuali.hr.time.base.web.TkForm;

public class TimeDetailActionForm extends TkForm {

    /**
     *
     */
    private static final long serialVersionUID = 5277197287612035236L;

	private String beginPeriodDate;
	private String endPeriodDate;

	public String getBeginPeriodDate() {
		return beginPeriodDate;
	}

	public void setBeginPeriodDate(String beginPeriodDate) {
		this.beginPeriodDate = beginPeriodDate;
	}

	public String getEndPeriodDate() {
		return endPeriodDate;
	}

	public void setEndPeriodDate(String endPeriodDate) {
		this.endPeriodDate = endPeriodDate;
	}
}
