package org.kuali.hr.lm.leavecalendar.service;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.leavecalendar.dao.LeaveCalendarDao;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.service.WorkflowDocument;

public class LeaveCalendarServiceImpl implements LeaveCalendarService {

    private LeaveCalendarDao leaveCalendarDao;

    @Override
    public LeaveCalendarDocument getLeaveCalendarDocument(String documentId) {
        LeaveCalendarDocument lcd = null;
        LeaveCalendarDocumentHeader lcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId);

        if (lcdh != null) {
            lcd = new LeaveCalendarDocument(lcdh);
            CalendarEntries pce = TkServiceLocator.getCalendarService().getCalendarDatesByPayEndDate(lcdh.getPrincipalId(), lcdh.getEndDate(), LMConstants.LEAVE_CALENDAR_TYPE);
            lcd.setCalendarEntry(pce);
        } else {
            throw new RuntimeException("Could not find LeaveCalendarDocumentHeader for DocumentID: " + documentId);
        }

        List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDocumentId(documentId);
        lcd.setLeaveBlocks(leaveBlocks);

        // Fetching assignments
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignmentsByPayEntry(lcdh.getPrincipalId(), lcd.getCalendarEntry());
        lcd.setAssignments(assignments);
        
        return lcd;
    }

    @Override
    public LeaveCalendarDocument openLeaveCalendarDocument(String principalId, CalendarEntries calEntry) throws WorkflowException {
        LeaveCalendarDocument doc;

        Date begin = calEntry.getBeginPeriodDateTime();
        Date end = calEntry.getEndPeriodDateTime();

        LeaveCalendarDocumentHeader header = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, begin, end);
        if (header == null) {
            doc = initiateWorkflowDocument(principalId, begin, end, LeaveCalendarDocument.LEAVE_CALENDAR_DOCUMENT_TYPE, LeaveCalendarDocument.LEAVE_CALENDAR_DOCUMENT_TITLE);
            // This will preload the document data.
            loadLeaveCalendarDocumentData(doc, principalId, calEntry);
        } else {
            doc = getLeaveCalendarDocument(header.getDocumentId());
        }
        doc.setCalendarEntry(calEntry);
        // TODO: need to set the summary
        return doc;
    }

    protected LeaveCalendarDocument initiateWorkflowDocument(String principalId, Date payBeginDate, Date payEndDate, String documentType, String title) throws WorkflowException {
        LeaveCalendarDocument leaveCalendarDocument = null;
        WorkflowDocument workflowDocument = null;

        workflowDocument = new WorkflowDocument(principalId, documentType);
        workflowDocument.setTitle(title);

        String status = workflowDocument.getRouteHeader().getDocRouteStatus();
        LeaveCalendarDocumentHeader documentHeader = new LeaveCalendarDocumentHeader(workflowDocument.getRouteHeaderId().toString(), principalId, payBeginDate, payEndDate, status);

        documentHeader.setDocumentId(workflowDocument.getRouteHeaderId().toString());
        documentHeader.setDocumentStatus("I");

        TkServiceLocator.getLeaveCalendarDocumentHeaderService().saveOrUpdate(documentHeader);
        leaveCalendarDocument = new LeaveCalendarDocument(documentHeader);

        // TODO:
        //TkServiceLocator.getTkSearchableAttributeService().updateSearchableAttribute(leaveCalendarDocument, payEndDate);

        return leaveCalendarDocument;
    }

    /**
     * Preload the document data. It preloads:
     * - LeaveBlocks on the document.
     * @param ldoc
     * @param principalId
     * @param calEntry
     */
    protected void loadLeaveCalendarDocumentData(LeaveCalendarDocument ldoc, String principalId, CalendarEntries calEntry) {
        List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDocumentId(ldoc.getDocumentId());
        ldoc.setLeaveBlocks(leaveBlocks);
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignmentsByPayEntry(principalId, calEntry);
        ldoc.setAssignments(assignments);
    }

    public LeaveCalendarDao getLeaveCalendarDao() {
        return leaveCalendarDao;
    }

    public void setLeaveCalendarDao(LeaveCalendarDao leaveCalendarDao) {
        this.leaveCalendarDao = leaveCalendarDao;
    }

	@Override
	public LeaveCalendarDocument getLeaveCalendarDocument(
			String principalId, CalendarEntries calendarEntry) {
		LeaveCalendarDocument leaveCalendarDocument = new LeaveCalendarDocument(calendarEntry);
		LeaveCalendarDocumentHeader lcdh = new LeaveCalendarDocumentHeader();
		lcdh.setBeginDate(calendarEntry.getBeginPeriodDateTime());
		lcdh.setEndDate(calendarEntry.getEndPeriodDateTime());
		leaveCalendarDocument.setLeaveCalendarDocumentHeader(lcdh);
		// Fetching assignments
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignmentsByPayEntry(principalId, calendarEntry);
        leaveCalendarDocument.setAssignments(assignments);
		return leaveCalendarDocument;
	}
	
	@Override
	 public LeaveSummary getLeaveSummary(String principalId, CalendarEntries calendarEntry) throws Exception {
    	LeaveSummary ls = new LeaveSummary();
    	List<LeaveSummaryRow> rows = new ArrayList<LeaveSummaryRow>();
    	
    	if(StringUtils.isNotEmpty(principalId) && calendarEntry != null) {
    		
    		// get principalHrAttributes for begin date of this pay period
    		PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, calendarEntry.getBeginPeriodDate());
    		// get list of principalHrAttributes that become active during this pay period
    		List<PrincipalHRAttributes> phaList = TkServiceLocator.getPrincipalHRAttributeService()
    			.getActivePrincipalHrAttributesForRange(principalId, calendarEntry.getBeginPeriodDate(), calendarEntry.getEndPeriodDate());
    		
    		Set<String> lpStrings = new HashSet<String>();
    		if(pha != null) {
    			lpStrings.add(pha.getLeavePlan());
    		}
    		if(CollectionUtils.isNotEmpty(phaList)) {
    			for(PrincipalHRAttributes aPha : phaList) {
    				lpStrings.add(aPha.getLeavePlan());
    			}
    		}
    		
    		if(CollectionUtils.isNotEmpty(lpStrings)) {
    			for(String aLpString : lpStrings) {
	    			LeavePlan lp = TkServiceLocator.getLeavePlanService().getLeavePlan(aLpString, calendarEntry.getEndPeriodDate());
	    			if(lp == null) {
	    				continue;
	    			}
	    			
    				DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    				// get the latest approved leave calendar
            		LeaveCalendarDocumentHeader approvedLcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getMaxEndDateApprovedLeaveCalendar(principalId);
            		List<LeaveBlock> approvedLeaveBlocks = new ArrayList<LeaveBlock> ();
            		if(approvedLcdh != null) {
            			Date endApprovedDate = new java.sql.Date(approvedLcdh.getEndDate().getTime());
            			LocalDateTime aLocalTime = new DateTime(approvedLcdh.getEndDate()).toLocalDateTime();
            			DateTime endApprovedTime = aLocalTime.toDateTime(TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
            			if(endApprovedTime.getHourOfDay() == 0) {
            				endApprovedDate = TKUtils.addDates(endApprovedDate, -1);
            			}
        				Date yearStartDate = this.calendarYearStartDate(lp.getCalendarYearStart(), endApprovedDate);
        				String datesString = formatter.format(yearStartDate) + " - " + formatter.format(endApprovedDate);
        				ls.setYtdDatesString(datesString);
        				// get leave blocks from the beginning of this employment to the end date of the latest approved calendar
            			approvedLeaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, yearStartDate, endApprovedDate);
            		}
            		// get the earliest pending leave calendar
            		LeaveCalendarDocumentHeader pendingLcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getMinBeginDatePendingLeaveCalendar(principalId);
            		List<LeaveBlock> pendingLeaveBlocks = new ArrayList<LeaveBlock> ();
            		if(pendingLcdh != null) {
            			Date pendingStartDate = pendingLcdh.getBeginDate();
            			Date pendingEndDate = calendarEntry.getEndPeriodDate();
            			DateTime endDateTime = calendarEntry.getEndLocalDateTime().toDateTime(TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
            			if(endDateTime.getHourOfDay() == 0) {
            				pendingEndDate = TKUtils.addDates(pendingEndDate, -1);
            			}
            			
            			if(!pendingStartDate.after(pendingEndDate)) {
            				String aString = formatter.format(pendingStartDate) + " - " + formatter.format(pendingEndDate);
            				ls.setPendingDatesString(aString);
            				pendingLeaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, pendingStartDate, pendingEndDate);
            			}
            		}
    				List<AccrualCategory> acList = TkServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(lp.getLeavePlan(), calendarEntry.getEndPeriodDate());
    				if(CollectionUtils.isNotEmpty(acList)) {
    					for(AccrualCategory ac : acList) {
    						if(ac.getShowOnGrid().equals("Y")) {
    							LeaveSummaryRow lsr = new LeaveSummaryRow();
    							lsr.setAccrualCategory(ac.getAccrualCategory());
    							AccrualCategoryRule acRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(ac.getLmAccrualCategoryId());
    							if(acRule != null && acRule.getMaxUsage()!= null) {
    								lsr.setUsageLimit(new BigDecimal(acRule.getMaxUsage()));
    							} else {
    								lsr.setUsageLimit(BigDecimal.ZERO);
    							}
    							// assign approved values
    							this.assignApprovedValuesToRow(lsr, ac, approvedLeaveBlocks);
    							// assign pending values
    							this.assignPendingValuesToRow(lsr, ac, pendingLeaveBlocks);
    							
    							rows.add(lsr);
    						}
    					}
    				}
	    			
    			}
    		}
    	}
    	ls.setLeaveSummaryRows(rows);
    	return ls;
    }
	
	private void assignApprovedValuesToRow(LeaveSummaryRow lsr, AccrualCategory ac, List<LeaveBlock> approvedLeaveBlocks ) {
		BigDecimal accrualedBalance = BigDecimal.ZERO;
		BigDecimal approvedUsage = BigDecimal.ZERO;
		BigDecimal fmlaUsage = BigDecimal.ZERO;
		for(LeaveBlock aLeaveBlock : approvedLeaveBlocks) {
			if(aLeaveBlock.getAccrualCategoryId().equals(ac.getLmAccrualCategoryId())) {
				if(StringUtils.isNotEmpty(aLeaveBlock.getRequestStatus())
						&& aLeaveBlock.getRequestStatus().equals(LMConstants.REQUEST_STATUS.APPROVED)) {
					if(aLeaveBlock.getLeaveAmount().compareTo(BigDecimal.ZERO) >= 0) {
						accrualedBalance = accrualedBalance.add(aLeaveBlock.getLeaveAmount());
					} else {
						approvedUsage = approvedUsage.add(aLeaveBlock.getLeaveAmount());
						EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(aLeaveBlock.getEarnCode(), aLeaveBlock.getLeaveDate());
				    	if(ec != null && ec.getFmla().equals("Y")) {
				    		fmlaUsage = fmlaUsage.add(aLeaveBlock.getLeaveAmount());
				    	}
					}
					
				}
			}
		}
		lsr.setYtdAccruedBalance(accrualedBalance);
		lsr.setYtdApprovedUsage(approvedUsage.negate());
		lsr.setFmlaUsage(fmlaUsage.negate());
		lsr.setLeaveBalance(lsr.getYtdAccruedBalance().subtract(lsr.getYtdApprovedUsage()));
	}
	
	private void assignPendingValuesToRow(LeaveSummaryRow lsr, AccrualCategory ac, List<LeaveBlock> pendingLeaveBlocks ) {
		BigDecimal pendingAccrual= BigDecimal.ZERO;
		BigDecimal pendingRequests = BigDecimal.ZERO;
		
		for(LeaveBlock aLeaveBlock : pendingLeaveBlocks) {
			if(aLeaveBlock.getAccrualCategoryId().equals(ac.getLmAccrualCategoryId())) {
				if(aLeaveBlock.getLeaveAmount().compareTo(BigDecimal.ZERO) >= 0) {
					pendingAccrual = pendingAccrual.add(aLeaveBlock.getLeaveAmount());
				} else {
					pendingRequests = pendingRequests.add(aLeaveBlock.getLeaveAmount());
				}
			}
		}
		lsr.setPendingLeaveAccrual(pendingAccrual);
		lsr.setPendingLeaveRequests(pendingRequests.negate());
		BigDecimal pendingBalance = lsr.getLeaveBalance().add(lsr.getPendingLeaveAccrual()).subtract(lsr.getPendingLeaveRequests());
		lsr.setPendingLeaveBalance(pendingBalance);
		
		BigDecimal pendingAvailable = BigDecimal.ZERO;
		if(lsr.getUsageLimit() != null) {
			pendingAvailable = lsr.getUsageLimit().subtract(lsr.getYtdApprovedUsage().add(pendingRequests));
		}
		lsr.setPendingAvailableUsage(pendingAvailable);
	}
	
	
	public Date calendarYearStartDate(String dateString, Date asOfDate) throws ParseException {
		// dateString in MM/DD format
		Calendar gc = new GregorianCalendar();
		gc.setTime(asOfDate);
		String yearString = Integer.toString(gc.get(Calendar.YEAR));
		String tempString = dateString;
		if(StringUtils.isEmpty(dateString)) {
			tempString = "01/01";		// use 01/01 as default starting date
		}
		String fullString = tempString + "/" + yearString;
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date aDate = formatter.parse(fullString);
		if(aDate.after(asOfDate)) {
			yearString = Integer.toString(gc.get(Calendar.YEAR) -1);
			fullString = tempString + "/" + yearString;
			aDate = formatter.parse(fullString);
		}
		return aDate;
	}

	
}

