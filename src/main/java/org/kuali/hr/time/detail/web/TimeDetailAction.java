package org.kuali.hr.time.detail.web;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONValue;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.web.TimesheetAction;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;

public class TimeDetailAction extends TimesheetAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		TimeDetailActionForm timeDetailForm = (TimeDetailActionForm) form;
		String principalId = TKContext.getUser().getPrincipalId();

		List<Job> job = TKContext.getUser().getJobs();
		java.sql.Date beginPeriodDate = job.get(0).getPayType().getPayCalendar().getPayCalendarDates().get(0).getBeginPeriodDate();
		java.sql.Date endPeriodDate = job.get(0).getPayType().getPayCalendar().getPayCalendarDates().get(0).getEndPeriodDate();

		timeDetailForm.setBeginPeriodDate(beginPeriodDate);
		timeDetailForm.setEndPeriodDate(endPeriodDate);

		List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocksByPeriod(principalId, timeDetailForm.getBeginPeriodDate(), timeDetailForm.getEndPeriodDate());

		// for visually impaired users
		timeDetailForm.setTimeBlocks(timeBlocks);

		return super.execute(mapping, form, request, response);
	}

	public ActionForward webService(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		TimeDetailActionForm timeDetailForm = (TimeDetailActionForm) form;

		String principalId = TKContext.getUser().getPrincipalId();
		Date beginDate = timeDetailForm.getBeginPeriodDate();
		Date endDate = timeDetailForm.getEndPeriodDate();

//		List<Job> jobs = TKContext.getUser().getJobs();
		List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocksByPeriod(principalId, beginDate, endDate);

		List<Map<String,Object>> timeBlockList = new LinkedList<Map<String,Object>>();

		for(TimeBlock timeBlock : timeBlocks) {
			Map<String,Object> timeBlockMap = new LinkedHashMap<String, Object>();

			// TODO: need to hook up the real assignment

			timeBlockMap.put("title", "HRMS Java Team : " + timeBlock.getEarnCode());
			timeBlockMap.put("start", new java.util.Date(timeBlock.getBeginTimestamp().getTime()).toString());
			timeBlockMap.put("end", new java.util.Date(timeBlock.getEndTimestamp().getTime()).toString());
			timeBlockMap.put("id", timeBlock.getTkTimeBlockId().toString());

			timeBlockList.add(timeBlockMap);
		}

		// generate a JOSN string
		timeDetailForm.setTimeBlockJson(JSONValue.toJSONString(timeBlockList));

		return mapping.findForward("ws");
	}

	public ActionForward deleteTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		TimeDetailActionForm timeDetailForm = (TimeDetailActionForm) form;
		TimeBlock timeBlockToDelete = TkServiceLocator.getTimeBlockService().getTimeBlock(timeDetailForm.getTimeBlockId());
		TkServiceLocator.getTimeBlockService().deleteTimeBlock(timeBlockToDelete);

		return mapping.findForward("basic");
	}

	public ActionForward addTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		TimeDetailActionForm timeDetailForm = (TimeDetailActionForm) form;
		Date beginDateField = timeDetailForm.getBeginDate();
		Date endDateField = timeDetailForm.getEndDate();
		String[] beginTimeField = timeDetailForm.getBeginTime().split(":");
		String[] endTimeField = timeDetailForm.getEndTime().split(":");

		Calendar begin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();

		begin.setTime(beginDateField);
		begin.set(Calendar.HOUR_OF_DAY, Integer.parseInt(beginTimeField[0]));
		begin.set(Calendar.MINUTE, Integer.parseInt(beginTimeField[1]));
		begin.set(Calendar.SECOND, 0);

		end.setTime(endDateField);
		end.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTimeField[0]));
		end.set(Calendar.MINUTE, Integer.parseInt(endTimeField[1]));
		end.set(Calendar.SECOND, 0);

		if(StringUtils.equals(timeDetailForm.getAcrossDays(),"y")) {
			List<TimeBlock> timeBlockList = new LinkedList<TimeBlock>();

			long dayBetween = TKUtils.getDaysBetween(begin, end);
			for (int i = 0; i < dayBetween; i++) {

				Calendar b = (Calendar)begin.clone();
				Calendar e = (Calendar)end.clone();

				b.set(Calendar.DATE, begin.get(Calendar.DATE) + i);
				e.set(Calendar.DATE, begin.get(Calendar.DATE) + i);

				TimeBlock tb = new TimeBlock();
			  	tb.setJobNumber(0L);
		    	tb.setWorkArea(0L);
		    	tb.setTask(0L);
		    	tb.setEarnCode(timeDetailForm.getEarnCode());
		    	tb.setBeginTimestamp(new Timestamp(b.getTimeInMillis()));
		    	tb.setEndTimestamp(new Timestamp(e.getTimeInMillis()));
		    	tb.setClockLogCreated(true);
		    	tb.setHours(TKUtils.getHoursBetween(begin.getTimeInMillis(), end.getTimeInMillis()));
		    	tb.setUserPrincipalId(TKContext.getUser().getPrincipalId());
		    	tb.setTimestamp(new Timestamp(System.currentTimeMillis()));
		    	tb.setBeginTimestampTimezone(TKUtils.getTimeZone());
		    	tb.setEndTimestampTimezone(TKUtils.getTimeZone());

		    	timeBlockList.add(tb);
			}

			TkServiceLocator.getTimeBlockService().saveTimeBlockList(timeBlockList);
		}
		else {

			TimeBlock tb = new TimeBlock();
		  	tb.setJobNumber(0L);
	    	tb.setWorkArea(0L);
	    	tb.setTask(0L);
	    	tb.setEarnCode(timeDetailForm.getEarnCode());
	    	tb.setBeginTimestamp(new Timestamp(begin.getTimeInMillis()));
	    	tb.setEndTimestamp(new Timestamp(end.getTimeInMillis()));
	    	tb.setClockLogCreated(true);
	    	tb.setHours(TKUtils.getHoursBetween(begin.getTimeInMillis(), end.getTimeInMillis()));
	    	tb.setUserPrincipalId(TKContext.getUser().getPrincipalId());
	    	tb.setTimestamp(new Timestamp(System.currentTimeMillis()));
	    	tb.setBeginTimestampTimezone(TKUtils.getTimeZone());
	    	tb.setEndTimestampTimezone(TKUtils.getTimeZone());

	    	TkServiceLocator.getTimeBlockService().saveTimeBlock(tb);
		}

		return mapping.findForward("basic");
	}
}
