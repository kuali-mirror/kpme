package org.kuali.hr.time.detail.web;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
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
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.web.TimesheetAction;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;

public class TimeDetailAction extends TimesheetAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.execute(mapping, form, request, response);
		TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
		
		tdaf.setBeginPeriodDate(tdaf.getPayCalendarDates().getBeginPeriodDate());
		tdaf.setEndPeriodDate(tdaf.getPayCalendarDates().getEndPeriodDate());

		// for visually impaired users 
		// TimesheetDocument td = tdaf.getTimesheetDocument();
		// List<TimeBlock> timeBlocks = td.getTimeBlocks();
		// tdaf.setTimeBlocks(timeBlocks);
		
		return forward;
	}
	
	// this is an ajax call
	public ActionForward getEarnCodes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
		List<Assignment> assignments = tdaf.getTimesheetDocument().getAssignments();
		AssignmentDescriptionKey key = new AssignmentDescriptionKey(tdaf.getAssignmentUniqueKey());
		String earnCodeString = "";
		
		for(Assignment assignment : assignments) {
			if(assignment.getJobNumber().compareTo(key.getJobNumber()) == 0 &&
			    assignment.getWorkArea().compareTo(key.getWorkArea()) == 0 &&
			    assignment.getTask().compareTo(key.getTask()) == 0) {
				
				List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodes(assignment);
				for(EarnCode earnCode: earnCodes) {
					earnCodeString += "<option value='" + earnCode.getEarnCode() + "'>" + earnCode.getEarnCode() + " : " + earnCode.getDescription() + "</option>";
				}
			}
		}
		
		tdaf.setOutputString(earnCodeString);
		return mapping.findForward("basic");
	}

	public ActionForward getTimeblocks(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		TimeDetailActionForm timeDetailForm = (TimeDetailActionForm) form;
		
		List<Map<String,Object>> timeBlockList = TkServiceLocator.getTimeBlockService().getTimeBlocksForOurput(timeDetailForm);
		timeDetailForm.setOutputString(JSONValue.toJSONString(timeBlockList));
		
		return mapping.findForward("ws");
	}

	public ActionForward deleteTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// TODO: need to set the clock action to DELETE
		
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

		// TODO: need to change the static data
		if(StringUtils.equals(timeDetailForm.getAcrossDays(),"y")) {
			List<TimeBlock> timeBlockList = new LinkedList<TimeBlock>();

			long daysBetween = TKUtils.getDaysBetween(begin, end);
			for (int i = 0; i < daysBetween; i++) {

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
