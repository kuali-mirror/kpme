package org.kuali.hr.time.detail.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;

public class TimeDetailAction extends TkAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		TimeDetailActionForm timeDetailForm = (TimeDetailActionForm) form;

		List<Job> job = TkServiceLocator.getJobSerivce().getJobs("eric");
		java.sql.Date beginPeriodDate = job.get(0).getPayType().getCalendarGroupObj().getPayCalendarDates().get(0).getBeginPeriodDate();

		timeDetailForm.setBeginPeriodDate(TkConstants.SDF.format(beginPeriodDate));
		return super.execute(mapping, form, request, response);
	}
}
