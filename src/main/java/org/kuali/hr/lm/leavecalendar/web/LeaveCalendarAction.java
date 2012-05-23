package org.kuali.hr.lm.leavecalendar.web;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.LeaveCalendar;
import org.kuali.hr.time.detail.web.ActionFormUtils;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LeaveCalendarAction extends TkAction {

	private static final Logger LOG = Logger
			.getLogger(LeaveCalendarAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;

		TKUser user = TKContext.getUser();
		String documentId = lcf.getDocumentId();
		// if the reload was trigger by changing of the selectedPayPeriod, use the passed in parameter as the calendar entry id
		String calendarEntryId = StringUtils.isNotBlank(request.getParameter("selectedPP")) ? request.getParameter("selectedPP") : lcf.getCalEntryId();
		
		// Here - viewPrincipal will be the principal of the user we intend to
		// view, be it target user, backdoor or otherwise.
		String viewPrincipal = user.getTargetPrincipalId();
		CalendarEntries calendarEntry = null;

		LeaveCalendarDocument lcd = null;
		LeaveCalendarDocumentHeader lcdh = null;

		// By handling the prev/next in the execute method, we are saving one
		// fetch/construction of a TimesheetDocument. If it were broken out into
		// methods, we would first fetch the current document, and then fetch
		// the next one instead of doing it in the single action.
		if (StringUtils.isNotBlank(documentId)) {
			lcd = TkServiceLocator.getLeaveCalendarService()
					.getLeaveCalendarDocument(documentId);
			calendarEntry = lcd.getCalendarEntry();
		} else if (StringUtils.isNotBlank(calendarEntryId)) {
			// do further procedure
			calendarEntry = TkServiceLocator.getCalendarEntriesSerivce()
					.getCalendarEntries(calendarEntryId);
			lcd = TkServiceLocator.getLeaveCalendarService()
					.getLeaveCalendarDocument(viewPrincipal, calendarEntry);
		} else {
			// Default to whatever is active for "today".
			Date currentDate = TKUtils.getTimelessDate(null);
			calendarEntry = TkServiceLocator.getCalendarSerivce()
					.getCurrentCalendarDates(viewPrincipal, currentDate);
			lcd = TkServiceLocator.getLeaveCalendarService()
					.openLeaveCalendarDocument(viewPrincipal, calendarEntry);
		}
		
		lcf.setCalendarEntry(calendarEntry);
		lcf.setAssignmentDescriptions(TkServiceLocator.getAssignmentService().getAssignmentDescriptions(lcd));

		if (lcd != null) {
			setupDocumentOnFormContext(lcf, lcd);
		} else {
			LOG.error("Null leave calendar document in LeaveCalendarAction.");
		}

		// PrincipalHRAttributes principalHRAttributes =
		// TkServiceLocator.getPrincipalHRAttributesService().getPrincipalCalendar(user.getPrincipalId(),
		// TKUtils.getCurrentDate());

		ActionForward forward = super.execute(mapping, form, request, response);
		if (forward.getRedirect()) {
			return forward;
		}

		LeaveCalendar calendar = new LeaveCalendar(viewPrincipal, calendarEntry);
		lcf.setLeaveCalendar(calendar);
		
		this.populateCalendarAndPayPeriodLists(request, lcf);
		

		return forward;
	}
	
	private void populateCalendarAndPayPeriodLists(HttpServletRequest request, LeaveCalendarForm lcf) {
		
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        List<CalendarEntries> ceList = TkServiceLocator.getCalendarEntriesSerivce().getAllCalendarEntriesForCalendarId(lcf.getCalendarEntry().getHrCalendarId());
        
        if(lcf.getCalendarYears().isEmpty()) {
        	// get calendar year drop down list contents
	        Set<String> yearSet = new HashSet<String>();
	        for(CalendarEntries ce : ceList) {
	        	yearSet.add(sdf.format(ce.getBeginPeriodDate()));
	        }
	        List<String> yearList = new ArrayList<String>(yearSet);
	        Collections.sort(yearList);
	        lcf.setCalendarYears(yearList);
        }
        // if selected calendar year is passed in
        if(request.getParameter("selectedCY")!= null) {
        	lcf.setSelectedCalendarYear(request.getParameter("selectedCY").toString());
        }
        // if there is no selected calendr year, use the year of current pay calendar entry
        if(StringUtils.isEmpty(lcf.getSelectedCalendarYear())) {
        	lcf.setSelectedCalendarYear(sdf.format(lcf.getCalendarEntry().getBeginPeriodDate()));
        }
        if(lcf.getPayPeriodsMap().isEmpty()) {
        	List<CalendarEntries> yearCEList = TkServiceLocator.getCalendarEntriesSerivce()
        			.getAllCalendarEntriesForCalendarIdAndYear(lcf.getCalendarEntry().getHrCalendarId(), lcf.getSelectedCalendarYear());
	        lcf.setPayPeriodsMap(ActionFormUtils.getPayPeriodsMap(yearCEList));
        }
        if(request.getParameter("selectedPP")!= null) {
        	lcf.setSelectedPayPeriod(request.getParameter("selectedPP").toString());
        }
        if(StringUtils.isEmpty(lcf.getSelectedPayPeriod())) {
        	lcf.setSelectedPayPeriod(lcf.getCalendarEntry().getHrCalendarEntriesId());
        }
	}
	

	public ActionForward addLeaveBlock(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		LeaveCalendarDocument lcd = lcf.getLeaveCalendarDocument();

		DateTime beginDate = new DateTime(
				TKUtils.convertDateStringToTimestamp(lcf.getStartDate()));
		DateTime endDate = new DateTime(
				TKUtils.convertDateStringToTimestamp(lcf.getEndDate()));
		String selectedLeaveCode = lcf.getSelectedLeaveCode();
		BigDecimal hours = lcf.getLeaveAmount();
		String desc = lcf.getDescription();

		Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(lcd, lcf.getSelectedAssignment());

		TkServiceLocator.getLeaveBlockService().addLeaveBlocks(beginDate,
				endDate, lcf.getCalendarEntry(), selectedLeaveCode, hours, desc, assignment);
		// after adding the leave block, set the fields of this form to null for future new leave blocks
		lcf.setLeaveAmount(null);
		lcf.setDescription(null);
		
		return mapping.findForward("basic");
	}

	public ActionForward deleteLeaveBlock(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		String leaveBlockId = lcf.getLeaveBlockId();

		// TODO: need security check
		TkServiceLocator.getLeaveBlockService().deleteLeaveBlock(
				Long.parseLong(leaveBlockId));

		return mapping.findForward("basic");
	}

	protected void setupDocumentOnFormContext(LeaveCalendarForm leaveForm,
			LeaveCalendarDocument lcd) {
		LeaveCalendarDocumentHeader prevLdh = null;
		LeaveCalendarDocumentHeader nextLdh = null;
		CalendarEntries futureCalEntry = null;
		String viewPrincipal = TKContext.getUser().getTargetPrincipalId();
		if (lcd.getLeaveCalendarDocumentHeader() != null) {
			TKContext.setCurrentLeaveCalendarDocumentId(lcd.getDocumentId());
			leaveForm.setDocumentId(lcd.getDocumentId());
		}
		TKContext.setCurrentLeaveCalendarDocument(lcd);
		leaveForm.setLeaveCalendarDocument(lcd);
		// -- put condition if it is not after current period
		boolean isFutureDate = TKUtils.getTimelessDate(null).compareTo(
				lcd.getCalendarEntry().getBeginPeriodDateTime()) >= 0;

		if (TKContext.getCurrentLeaveCalendarDocument()
				.getLeaveCalendarDocumentHeader() != null) {
			prevLdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService()
					.getPrevOrNextDocumentHeader(TkConstants.PREV_TIMESHEET,
							viewPrincipal);
			nextLdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService()
					.getPrevOrNextDocumentHeader(TkConstants.NEXT_TIMESHEET,
							viewPrincipal);
		}
		if (prevLdh != null) {
			leaveForm.setPrevDocumentId(prevLdh.getDocumentId());
		} else if (!isFutureDate) { // -- if calendar entry is not before the
									// current calendar entry

			// fetch previous entry
			CalendarEntries calNextEntry = TkServiceLocator
					.getCalendarEntriesSerivce()
					.getPreviousCalendarEntriesByCalendarId(
							lcd.getCalendarEntry().getHrCalendarId(),
							lcd.getCalendarEntry());
			if (calNextEntry != null) {
				leaveForm.setPrevCalEntryId(calNextEntry
						.getHrCalendarEntriesId());
			}
		}
		if (nextLdh != null) {
			leaveForm.setNextDocumentId(nextLdh.getDocumentId());
		} else {
			// Fetch planning month's entry
			int plannningMonths = 0;
			PrincipalHRAttributes principalHRAttributes = TkServiceLocator
					.getPrincipalHRAttributeService().getPrincipalCalendar(
							viewPrincipal, TKUtils.getCurrentDate());
			if (principalHRAttributes != null
					&& principalHRAttributes.getLeavePlan() != null) {

				LeavePlan lp = TkServiceLocator.getLeavePlanService()
						.getLeavePlan(principalHRAttributes.getLeavePlan(),
								TKUtils.getCurrentDate());
				// System.out.println("LEave Plan is >>>>>>>>>"+lp);

				if (lp != null && lp.getPlanningMonths() != null) {

					plannningMonths = Integer.parseInt(lp.getPlanningMonths());

					List<CalendarEntries> futureCalEntries = TkServiceLocator
							.getCalendarEntriesSerivce()
							.getFutureCalendarEntries(
									lcd.getCalendarEntry().getHrCalendarId(),
									TKUtils.getTimelessDate(null),
									plannningMonths);

					// System.out.println("Future calendar entries >>> "+futureCalEntries);
					if (futureCalEntries != null && !futureCalEntries.isEmpty()) {
						futureCalEntry = futureCalEntries.get(futureCalEntries
								.size() - 1);

						CalendarEntries calNextEntry = TkServiceLocator
								.getCalendarEntriesSerivce()
								.getNextCalendarEntriesByCalendarId(
										lcd.getCalendarEntry()
												.getHrCalendarId(),
										lcd.getCalendarEntry());

						if (calNextEntry != null
								&& futureCalEntries != null
								&& calNextEntry
										.getBeginPeriodDateTime()
										.compareTo(
												futureCalEntry
														.getBeginPeriodDateTime()) <= 0) {
							leaveForm.setNextCalEntryId(calNextEntry
									.getHrCalendarEntriesId());
						}
					}
				}
			}
		}
		leaveForm.setCalendarEntry(lcd.getCalendarEntry());
		leaveForm.setOnCurrentPeriod(ActionFormUtils.getOnCurrentPeriodFlag(lcd.getCalendarEntry()));

	}
	
	public ActionForward gotoCurrentPayPeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		String viewPrincipal = TKContext.getUser().getTargetPrincipalId();
		Date currentDate = TKUtils.getTimelessDate(null);
		CalendarEntries calendarEntry = TkServiceLocator.getCalendarSerivce().getCurrentCalendarDates(viewPrincipal, currentDate);
		LeaveCalendarDocument lcd = TkServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(viewPrincipal, calendarEntry);
		lcf.setCalendarEntry(calendarEntry);
		lcf.setAssignmentDescriptions(TkServiceLocator.getAssignmentService().getAssignmentDescriptions(lcd));
		lcf.setOnCurrentPeriod(ActionFormUtils.getOnCurrentPeriodFlag(calendarEntry));
		if (lcd != null) {
			setupDocumentOnFormContext(lcf, lcd);
		}
		return mapping.findForward("basic");
	  }
	
	//Triggered by changes of pay period drop down list, reload the whole page based on the selected pay period
	public ActionForward changeCalendarYear(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		  
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		if(request.getParameter("selectedCY") != null) {
			lcf.setSelectedCalendarYear(request.getParameter("selectedCY").toString());
		}
		return mapping.findForward("basic");
	}
	  
	//Triggered by changes of pay period drop down list, reload the whole page based on the selected pay period
	public ActionForward changePayPeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		if(request.getParameter("selectedPP") != null) {
			lcf.setSelectedPayPeriod(request.getParameter("selectedPP").toString());
	        CalendarEntries ce = TkServiceLocator.getCalendarEntriesSerivce()
				.getCalendarEntries(request.getParameter("selectedPP").toString());
			if(ce != null) {
				String viewPrincipal = TKContext.getUser().getTargetPrincipalId();
				LeaveCalendarDocument lcd = TkServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(viewPrincipal, ce);
				lcf.setCalEntryId(ce.getHrCalendarEntriesId());
				setupDocumentOnFormContext(lcf, lcd);
			}
		}
		return mapping.findForward("basic");
	}
	
}
