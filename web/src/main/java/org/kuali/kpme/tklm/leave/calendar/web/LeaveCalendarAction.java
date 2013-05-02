/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.tklm.leave.calendar.web;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.kpme.core.bo.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.bo.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.calendar.Calendar;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.bo.earncode.EarnCode;
import org.kuali.kpme.core.bo.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.block.LeaveBlockAggregate;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendar;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.calendar.validation.LeaveCalendarValidationUtil;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.summary.LeaveSummary;
import org.kuali.kpme.tklm.leave.summary.LeaveSummaryRow;
import org.kuali.kpme.tklm.leave.transfer.BalanceTransfer;
import org.kuali.kpme.tklm.leave.transfer.validation.BalanceTransferValidationUtils;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.kpme.tklm.leave.workflow.LeaveRequestDocument;
import org.kuali.kpme.tklm.time.detail.web.ActionFormUtils;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.util.UrlFactory;

public class LeaveCalendarAction extends KPMEAction {

	private static final Logger LOG = Logger.getLogger(LeaveCalendarAction.class);

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        String principalId = GlobalVariables.getUserSession().getPrincipalId();
        String documentId = TKContext.getCurrentLeaveCalendarDocumentId();
        
        if (documentId != null && !LmServiceLocator.getLMPermissionService().canViewLeaveCalendar(principalId, documentId)) {
            throw new AuthorizationException(GlobalVariables.getUserSession().getPrincipalId(), "LeaveCalendarAction: docid: " + documentId, "");
        }
    }
    
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		String documentId = lcf.getDocumentId();
		
        if (StringUtils.equals(request.getParameter("command"), "displayDocSearchView")
        		|| StringUtils.equals(request.getParameter("command"), "displayActionListView") ) {
        	documentId = (String) request.getParameter("docId");
        }

        LOG.debug("DOCID: " + documentId);
        
		// if the reload was trigger by changing of the selectedPayPeriod, use the passed in parameter as the calendar entry id
		String calendarEntryId = StringUtils.isNotBlank(request.getParameter("selectedPP")) ? request.getParameter("selectedPP") : lcf.getCalEntryId();
		
		// Here - viewPrincipal will be the principal of the user we intend to
		// view, be it target user, backdoor or otherwise.
		String viewPrincipal = TKContext.getTargetPrincipalId();
		CalendarEntry calendarEntry = null;

		LeaveCalendarDocument lcd = null;
		LeaveCalendarDocumentHeader lcdh = null;

		// By handling the prev/next in the execute method, we are saving one
		// fetch/construction of a LeaveCalendarDocument. If it were broken out into
		// methods, we would first fetch the current document, and then fetch
		// the next one instead of doing it in the single action.
		if (StringUtils.isNotBlank(documentId)) {
			lcd = LmServiceLocator.getLeaveCalendarService()
					.getLeaveCalendarDocument(documentId);
			calendarEntry = lcd.getCalendarEntry();
		} else if (StringUtils.isNotBlank(calendarEntryId)) {
			// do further procedure
			calendarEntry = HrServiceLocator.getCalendarEntryService()
					.getCalendarEntry(calendarEntryId);
		} else {
			// Default to whatever is active for "today".
			calendarEntry = HrServiceLocator.getCalendarService()
					.getCurrentCalendarDatesForLeaveCalendar(viewPrincipal, new LocalDate().toDateTimeAtStartOfDay());
		}
		lcf.setCalendarEntry(calendarEntry);
		if(calendarEntry != null) {
			lcf.setCalEntryId(calendarEntry.getHrCalendarEntryId());
		}
		// check configuration setting for allowing accrual service to be ran from leave calendar
		String runAccrualFlag = ConfigContext.getCurrentContextConfig().getProperty(LMConstants.RUN_ACCRUAL_FROM_CALENDAR);
		if(StringUtils.equals(runAccrualFlag, "true")) {
			// run accrual for future dates only, use planning month of leave plan for accrual period
			// only run the accrual if the calendar entry contains future dates
			if(calendarEntry != null && calendarEntry.getEndPeriodDate().after(LocalDate.now().toDate())) {
				if(LmServiceLocator.getLeaveAccrualService().statusChangedSinceLastRun(viewPrincipal)) {
					LmServiceLocator.getLeaveAccrualService().calculateFutureAccrualUsingPlanningMonth(viewPrincipal, calendarEntry.getBeginPeriodFullDateTime().toLocalDate());
				}
			}
		}
		
		if(lcd == null) {
			// use jobs to find out if this leave calendar should have a document created or not
			boolean createFlag = LmServiceLocator.getLeaveCalendarService().shouldCreateLeaveDocument(viewPrincipal, calendarEntry);
			if(createFlag) {
				lcd = LmServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(viewPrincipal, calendarEntry);
			} else {
				LeaveCalendarDocumentHeader header = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(viewPrincipal, calendarEntry.getBeginPeriodFullDateTime(), calendarEntry.getEndPeriodFullDateTime());
				if(header != null) {
					lcd = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(header.getDocumentId());
				}
			}
		}
		List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(viewPrincipal, calendarEntry);
		List<String> assignmentKeys = new ArrayList<String>();
        for(Assignment assign : assignments) {
        	assignmentKeys.add(assign.getAssignmentKey());
        }
		if (lcd != null) {
			lcf.setDocumentId(lcd.getDocumentId());
			lcf.setAssignmentDescriptions(HrServiceLocator.getAssignmentService().getAssignmentDescriptions(lcd));
            lcdh = lcd.getDocumentHeader();
		} else {
			lcf.setAssignmentDescriptions(HrServiceLocator.getAssignmentService().getAssignmentDescriptionsForAssignments(assignments));  
		}
		setupDocumentOnFormContext(lcf, lcd);
		ActionForward forward = super.execute(mapping, form, request, response);
		//no window exists if mapping->forward = closeBalanceTransferDoc.
		if (forward.getRedirect()) {
			return forward;
		}

        LeaveCalendar calendar = null;
        if (calendarEntry != null) {
            calendar = new LeaveCalendar(viewPrincipal, calendarEntry, assignmentKeys);
            lcf.setLeaveCalendar(calendar);
        }
		
		this.populateCalendarAndPayPeriodLists(request, lcf);

		// KPME-1447
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        if (lcdh != null && lcdh.getPrincipalId() != null && lcdh.getBeginDate() != null && lcdh.getEndDate() != null) {
            leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksForLeaveCalendar(lcdh.getPrincipalId(), LocalDate.fromDateFields(lcdh.getBeginDate()), LocalDate.fromDateFields(lcdh.getEndDate()), assignmentKeys);
        } else if(calendarEntry != null){
            leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksForLeaveCalendar(viewPrincipal, calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate(), assignmentKeys);
        } 
        
        // leave summary
        if (calendarEntry != null) {
            //check to see if we are on a previous leave plan
            PrincipalHRAttributes principalCal = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(viewPrincipal, calendarEntry.getEndPeriodFullDateTime().toLocalDate());
            if(principalCal != null) {

                DateTime currentYearBeginDate = HrServiceLocator.getLeavePlanService().getFirstDayOfLeavePlan(principalCal.getLeavePlan(), LocalDate.now());
                DateTime calEntryEndDate = new DateTime(calendarEntry.getEndPeriodDate());
	            if (calEntryEndDate.getMillis() > currentYearBeginDate.getMillis()) {
	            	//current or future year
	                LeaveSummary ls = LmServiceLocator.getLeaveSummaryService().getLeaveSummary(viewPrincipal, calendarEntry);
	                lcf.setLeaveSummary(ls);
                } else {
                    //current year roll over date has been passed, all previous calendars belong to the previous leave plan calendar year.
                    DateTime effDate = HrServiceLocator.getLeavePlanService().getRolloverDayOfLeavePlan(principalCal.getLeavePlan(), calEntryEndDate.minusDays(1).toLocalDate());
                    LeaveSummary ls = LmServiceLocator.getLeaveSummaryService().getLeaveSummaryAsOfDateWithoutFuture(viewPrincipal, effDate.toLocalDate());
                    //override title element (based on date passed in)
                    DateFormat formatter = new SimpleDateFormat("MMMM d");
                    DateFormat formatter2 = new SimpleDateFormat("MMMM d yyyy");
                    DateTime entryEndDate = new LocalDateTime(calendarEntry.getEndPeriodDate()).toDateTime();
                    if (entryEndDate.getHourOfDay() == 0) {
                        entryEndDate = entryEndDate.minusDays(1);
                    }
                    String aString = formatter.format(calendarEntry.getBeginPeriodDate()) + " - " + formatter2.format(entryEndDate.toDate());
                    ls.setPendingDatesString(aString);
                    DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM d, yyyy");
                    ls.setNote("Values as of: " + fmt.print(effDate));
                    lcf.setLeaveSummary(ls);
                }

            }
        }
        
        // add warning messages based on earn codes of leave blocks
        Map<String, Set<String>> allMessages = LeaveCalendarValidationUtil.getWarningMessagesForLeaveBlocks(leaveBlocks);

        // add warning message for accrual categories that have exceeded max balance.

        // Could set a flag on the transferable rows here so that LeaveCalendarSubmit.do knows
        // which row(s) to transfer when user submits the calendar for approval.

        if(ObjectUtils.isNotNull(calendarEntry)) {
            PrincipalHRAttributes principalCalendar = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(viewPrincipal, calendarEntry.getEndPeriodFullDateTime().toLocalDate());
	        List<BalanceTransfer> losses = new ArrayList<BalanceTransfer>();

	        Interval calendarInterval = new Interval(calendarEntry.getBeginPeriodDate().getTime(), calendarEntry.getEndPeriodDate().getTime());
	        Map<String,Set<LeaveBlock>> maxBalInfractions = new HashMap<String,Set<LeaveBlock>>();
	        
	        Date effectiveDate = LocalDate.now().toDate();
	        if(!calendarInterval.contains(effectiveDate.getTime()))
	        	effectiveDate = calendarEntry.getEndPeriodDate();
	        
            if(ObjectUtils.isNotNull(principalCalendar)) {
    	        maxBalInfractions = LmServiceLocator.getAccrualCategoryMaxBalanceService().getMaxBalanceViolations(calendarEntry, viewPrincipal);
    	        
		        LeaveSummary summary = lcf.getLeaveSummary();
    	        for(Entry<String,Set<LeaveBlock>> entry : maxBalInfractions.entrySet()) {
    	        	for(LeaveBlock lb : entry.getValue()) {
    	        		AccrualCategory accrualCat = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(lb.getAccrualCategory(), lb.getLeaveLocalDate());
			        	AccrualCategoryRule aRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(lb.getAccrualCategoryRuleId());
			        	if(StringUtils.equals(aRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.LOSE)) {
			        		DateTime aDate = null;
			        		if(StringUtils.equals(aRule.getMaxBalanceActionFrequency(), HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
			        			aDate = HrServiceLocator.getLeavePlanService().getRolloverDayOfLeavePlan(principalCalendar.getLeavePlan(), lb.getLeaveLocalDate());
			        		}
			        		else {
				        		Calendar cal = HrServiceLocator.getCalendarService().getCalendarByPrincipalIdAndDate(viewPrincipal, new LocalDate(lb.getLeaveDate()), true);
				        		CalendarEntry leaveEntry = HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(cal.getHrCalendarId(), new DateTime(lb.getLeaveDate()));
				        		aDate = new DateTime(leaveEntry.getEndPeriodDate());
			        		}
			        		aDate = aDate.minusDays(1);
			        		if(calendarInterval.contains(aDate.getMillis()) && aDate.toDate().compareTo(calendarEntry.getEndPeriodDate()) <= 0) {
				        		//may want to calculate summary for all rows, displayable or not, and determine displayability via tags.
				    			AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(aRule.getLmAccrualCategoryId());
				    			BigDecimal accruedBalance = HrServiceLocator.getAccrualCategoryService().getAccruedBalanceForPrincipal(viewPrincipal, accrualCategory, lb.getLeaveLocalDate());
					        	
					        	BalanceTransfer loseTransfer = LmServiceLocator.getBalanceTransferService().initializeTransfer(viewPrincipal, lb.getAccrualCategoryRuleId(), accruedBalance, lb.getLeaveLocalDate());
					        	boolean valid = BalanceTransferValidationUtils.validateTransfer(loseTransfer);
					        	if(valid)
					        		losses.add(loseTransfer);
			        		}
			        	}
			        	else if(StringUtils.equals(HrConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND, aRule.getMaxBalanceActionFrequency())) {
				        	if(calendarInterval.contains(lb.getLeaveDate().getTime())) {
					        	// accrual categories within the leave plan that are hidden from the leave summary will not appear.
					        	List<LeaveSummaryRow> summaryRows = summary.getLeaveSummaryRows();
					        	List<LeaveSummaryRow> updatedSummaryRows = new ArrayList<LeaveSummaryRow>(summaryRows.size());
					        	//AccrualCategoryRule currentRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(accrualCat, effectiveDate, principalCalendar.getServiceDate());
					        	for(LeaveSummaryRow summaryRow : summaryRows) {
					        		if(StringUtils.equals(summaryRow.getAccrualCategory(),accrualCat.getAccrualCategory())) {
					        			if(StringUtils.equals(aRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.PAYOUT))
					        				summaryRow.setPayoutable(true);
					        			else
					        				if(StringUtils.equals(aRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.TRANSFER))
						        				summaryRow.setTransferable(true);

					        			summaryRow.setInfractingLeaveBlockId(lb.getLmLeaveBlockId());
					        		}
					        		updatedSummaryRows.add(summaryRow);
					        	}
					        	summary.setLeaveSummaryRows(updatedSummaryRows);
				        	}
			        	}

        				if(calendarInterval.contains(lb.getLeaveDate().getTime())) {
        		        	// accrual categories within the leave plan that are hidden from the leave summary WILL appear.
	        				String message = "You have exceeded the maximum balance limit for '" + accrualCat.getAccrualCategory() + "' as of " + lb.getLeaveDate() + ". "+
	                    			"Depending upon the accrual category rules, leave over this limit may be forfeited.";
                            //  leave blocks are sorted in getMaxBalanceViolations() method, so we just take the one with the earliest leave date for an accrual category.
	        				if(!StringUtils.contains(allMessages.get("warningMessages").toString(),"You have exceeded the maximum balance limit for '"+accrualCat.getAccrualCategory())) {
	                            allMessages.get("warningMessages").add(message);
	        				}
        				}
    	        	}
    	        }
	        	lcf.setLeaveSummary(summary);
            }
	        lcf.setForfeitures(losses);
	        
	        Map<String,Set<String>> transactions = LeaveCalendarValidationUtil.validatePendingTransactions(viewPrincipal, calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate());

	        allMessages.get("infoMessages").addAll(transactions.get("infoMessages"));
	        allMessages.get("warningMessages").addAll(transactions.get("warningMessages"));
	        allMessages.get("actionMessages").addAll(transactions.get("actionMessages"));
        }
        


        
        // add warning messages based on max carry over balances for each accrual category
        if(calendarEntry != null) {
	        PrincipalHRAttributes principalCalendar = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(viewPrincipal, calendarEntry.getEndPeriodFullDateTime().toLocalDate());
			if (principalCalendar != null) {
				List<AccrualCategory> accrualCategories = HrServiceLocator.getAccrualCategoryService().getActiveLeaveAccrualCategoriesForLeavePlan(principalCalendar.getLeavePlan(), calendarEntry.getEndPeriodFullDateTime().toLocalDate());
				for (AccrualCategory accrualCategory : accrualCategories) {
					if (LmServiceLocator.getAccrualCategoryMaxCarryOverService().exceedsAccrualCategoryMaxCarryOver(accrualCategory.getAccrualCategory(), viewPrincipal, calendarEntry, calendarEntry.getEndPeriodFullDateTime().toLocalDate())) {
						String message = "Your pending leave balance is greater than the annual max carry over for accrual category '" + accrualCategory.getAccrualCategory() + "' and upon approval, the excess balance will be lost.";
						if (!allMessages.get("warningMessages").contains(message)) {
                            allMessages.get("warningMessages").add(message);
						}
					}
				}
				
				// KPME-1279 check for Absent Earn code to add warning.
				List<EarnCode> earnCodes = HrServiceLocator.getEarnCodeService().getEarnCodesForPrincipal(viewPrincipal, calendarEntry.getEndPeriodFullDateTime().toLocalDate(), true);
				if(earnCodes != null && !earnCodes.isEmpty()) {
					for (EarnCode earnCodeObj : earnCodes) {
						if(earnCodeObj != null) {
							if("Y".equalsIgnoreCase(earnCodeObj.getAffectPay()) && "N".equalsIgnoreCase(earnCodeObj.getEligibleForAccrual())) {
								String message = "Absent time cannot be used until other accrual balances are zero or below a specified accrual balance.";
								if (!allMessages.get("warningMessages").contains(message)) {
		                            allMessages.get("warningMessages").add(message);
		                            break;
								}
							}
						}
					}
				}
				
			}
        }

        List<String> warningMessages = new ArrayList<String>();
        List<String> infoMessages = new ArrayList<String>();
        List<String> actionMessages = new ArrayList<String>();
        
        warningMessages.addAll(allMessages.get("warningMessages"));
        infoMessages.addAll(allMessages.get("infoMessages"));
        actionMessages.addAll(allMessages.get("actionMessages"));

        lcf.setWarningMessages(warningMessages);
        lcf.setInfoMessages(infoMessages);
        lcf.setActionMessages(actionMessages);
        
		// KPME-1690
//        LeaveCalendar leaveCalender = new LeaveCalendar(viewPrincipal, calendarEntry);
        if (calendarEntry != null) {
            LeaveBlockAggregate aggregate = new LeaveBlockAggregate(leaveBlocks, calendarEntry, calendar);
            lcf.setLeaveBlockString(LeaveActionFormUtils.getLeaveBlocksJson(aggregate.getFlattenedLeaveBlockList()));
        }
        //lcf.setLeaveBlockString(ActionFormUtils.getLeaveBlocksJson(aggregate.getFlattenedLeaveBlockList()));
		
//        System.out.println("Leave block string : "+lcf.getLeaveBlockString());
		return forward;
	}
	
	private void populateCalendarAndPayPeriodLists(HttpServletRequest request, LeaveCalendarForm lcf) {
		String viewPrincipal = TKContext.getTargetPrincipalId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        // find all the calendar entries up to the planning months of this employee
        List<CalendarEntry> ceList = lcf.getCalendarEntry() == null ? new ArrayList<CalendarEntry>() : HrServiceLocator.getCalendarEntryService()
        	.getAllCalendarEntriesForCalendarIdUpToPlanningMonths(lcf.getCalendarEntry().getHrCalendarId(), TKContext.getTargetPrincipalId());
        
        if(lcf.getCalendarYears().isEmpty()) {
        	// get calendar year drop down list contents
	        Set<String> yearSet = new HashSet<String>();
	        for(CalendarEntry ce : ceList) {
	        	yearSet.add(sdf.format(ce.getBeginPeriodDate()));
	        }
	        List<String> yearList = new ArrayList<String>(yearSet);
	        Collections.sort(yearList);
	        Collections.reverse(yearList);	// newest on top
	        lcf.setCalendarYears(yearList);
        }
        // if selected calendar year is passed in
        if(request.getParameter("selectedCY")!= null) {
        	lcf.setSelectedCalendarYear(request.getParameter("selectedCY").toString());
        }
        // if there is no selected calendr year, use the year of current pay calendar entry
        if(StringUtils.isEmpty(lcf.getSelectedCalendarYear())
                && lcf.getCalendarEntry() != null) {
        	lcf.setSelectedCalendarYear(sdf.format(lcf.getCalendarEntry().getBeginPeriodDate()));
        }
        
        if(lcf.getPayPeriodsMap().isEmpty()) {
      		List<CalendarEntry> yearCEList = ActionFormUtils.getAllCalendarEntriesForYear(ceList, lcf.getSelectedCalendarYear());
	        lcf.setPayPeriodsMap(ActionFormUtils.getPayPeriodsMap(yearCEList, viewPrincipal));
        }
        
        if(request.getParameter("selectedPP")!= null) {
        	lcf.setSelectedPayPeriod(request.getParameter("selectedPP").toString());
        }
        if(StringUtils.isEmpty(lcf.getSelectedPayPeriod())
                && lcf.getCalendarEntry() != null) {
        	lcf.setSelectedPayPeriod(lcf.getCalendarEntry().getHrCalendarEntryId());
        }
	}	

	public ActionForward addLeaveBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		LeaveCalendarDocument lcd = lcf.getLeaveCalendarDocument();
		
		String principalId = TKContext.getPrincipalId();
		String targetPrincipalId = TKContext.getTargetPrincipalId();
		CalendarEntry calendarEntry = lcf.getCalendarEntry();
		String selectedAssignment = lcf.getSelectedAssignment();
		
		DateTime beginDate = null;
		DateTime endDate = null;
		
		/** -- Jignasha : if earchcode type is 'T' then change the date and time with timezone.
		// Surgery point - Need to construct a Date/Time with Appropriate Timezone.
		 * */
		LOG.debug("Start time is "+lcf.getStartTime());
		LOG.debug("Emnd time is "+lcf.getEndTime());
		if(lcf.getStartTime() != null && lcf.getEndTime() != null) {
			beginDate = new DateTime(TKUtils.convertDateStringToTimestampWithoutZone(lcf.getStartDate(), lcf.getStartTime()).getTime());
			endDate   = new DateTime(TKUtils.convertDateStringToTimestampWithoutZone(lcf.getEndDate(), lcf.getEndTime()).getTime());
		}  else {
			beginDate = new DateTime(TKUtils.formatDateTimeStringNoTimezone(lcf.getStartDate()));
			endDate = new DateTime(TKUtils.formatDateTimeStringNoTimezone(lcf.getEndDate()));
		}
        LOG.debug("Begin Date is>> "+beginDate);
        LOG.debug("End Date is>> "+endDate);
		
		/** Old Code
 		DateTime beginDate = new DateTime(TKUtils.convertDateStringToTimestampNoTimezone(lcf.getStartDate()));
		DateTime endDate = new DateTime(TKUtils.convertDateStringToTimestampNoTimezone(lcf.getEndDate()));
		*/
		
		String selectedEarnCode = lcf.getSelectedEarnCode();
		BigDecimal hours = lcf.getLeaveAmount();
		String desc = lcf.getDescription();
		String spanningWeeks = lcf.getSpanningWeeks();  // KPME-1446
		
		String documentId = lcd != null ? lcd.getDocumentId() : "";
		
		Assignment assignment = null;
		if(lcd != null) {
			assignment = HrServiceLocator.getAssignmentService().getAssignment(lcd, selectedAssignment);
		} else {
			List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(targetPrincipalId, calendarEntry);
			assignment = HrServiceLocator.getAssignmentService().getAssignment(assignments, selectedAssignment, calendarEntry.getBeginPeriodFullDateTime().toLocalDate());
		}

		LmServiceLocator.getLeaveBlockService().addLeaveBlocks(beginDate, endDate, calendarEntry, selectedEarnCode, hours, desc, assignment, spanningWeeks, 
				LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR, targetPrincipalId);

		generateLeaveCalendarChangedNotification(principalId, targetPrincipalId, documentId, calendarEntry.getHrCalendarEntryId());
		
		// after adding the leave block, set the fields of this form to null for future new leave blocks
		lcf.setLeaveAmount(null);
		lcf.setDescription(null);
		
		// call accrual service if earn code is not eligible for accrual
		if(calendarEntry != null) {
			this.rerunAccrualForNotEligibleForAccrualChanges(selectedEarnCode, endDate.toLocalDate(), calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate());
		 }
		// recalculate summary
		if (calendarEntry != null) {
			LeaveSummary ls = LmServiceLocator.getLeaveSummaryService().getLeaveSummary(targetPrincipalId, calendarEntry);
		    lcf.setLeaveSummary(ls);
		}
		
		return mapping.findForward("basic");
	}


	public ActionForward deleteLeaveBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		LeaveCalendarDocument lcd = lcf.getLeaveCalendarDocument();

		String principalId = TKContext.getPrincipalId();
		String targetPrincipalId = TKContext.getTargetPrincipalId();
		CalendarEntry calendarEntry = lcf.getCalendarEntry();
		String leaveBlockId = lcf.getLeaveBlockId();
		
		String documentId = lcd != null ? lcd.getDocumentId() : "";

        LeaveBlock blockToDelete = LmServiceLocator.getLeaveBlockService().getLeaveBlock(leaveBlockId);
        if (blockToDelete != null && LmServiceLocator.getLMPermissionService().canDeleteLeaveBlock(TKContext.getPrincipalId(), blockToDelete)) {
        	//if leave block is a pending leave request, cancel the leave request document
        	if(blockToDelete.getRequestStatus().equals(HrConstants.REQUEST_STATUS.REQUESTED)) {
        		List<LeaveRequestDocument> lrdList = LmServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocumentsByLeaveBlockId(blockToDelete.getLmLeaveBlockId());
        		if(CollectionUtils.isNotEmpty(lrdList)) {
        			for(LeaveRequestDocument lrd : lrdList) { 
        				DocumentStatus status = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(lrd.getDocumentNumber());
        				if(DocumentStatus.ENROUTE.getCode().equals(status.getCode())) {
        					// cancel the leave request document as the employee.
        					LmServiceLocator.getLeaveRequestDocumentService().recallAndCancelLeave(lrd.getDocumentNumber(), targetPrincipalId, "Leave block deleted by user " + principalId);
        				}
        			}
        		}
        	}
        	
        	List<String> approverList = new ArrayList<String>();
        	//if leave block is an approved leave request, get list of approver's id
        	if(blockToDelete.getRequestStatus().equals(HrConstants.REQUEST_STATUS.APPROVED)) {
        		List<LeaveRequestDocument> lrdList = LmServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocumentsByLeaveBlockId(blockToDelete.getLmLeaveBlockId());
        		if(CollectionUtils.isNotEmpty(lrdList)) {
        			for(LeaveRequestDocument lrd : lrdList) { 
        				DocumentStatus status = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(lrd.getDocumentNumber());
        				if(DocumentStatus.FINAL.getCode().equals(status.getCode())) {
        					// get approver's id for sending out email notification later
        					approverList = LmServiceLocator.getLeaveRequestDocumentService().getApproverIdList(lrd.getDocumentNumber());
        				}
        			}
        		}
        	}

        	LmServiceLocator.getLeaveBlockService().deleteLeaveBlock(leaveBlockId, principalId);
		    generateLeaveCalendarChangedNotification(principalId, targetPrincipalId, documentId, calendarEntry.getHrCalendarEntryId());
		    if(CollectionUtils.isNotEmpty(approverList)) {
		    	this.generateLeaveBlockDeletionNotification(approverList, targetPrincipalId, principalId, TKUtils.formatDate(blockToDelete.getLeaveLocalDate()), blockToDelete.getLeaveAmount().toString());
		    }
        	
		    // recalculate accruals
		    if(lcf.getCalendarEntry() != null) {
		    	rerunAccrualForNotEligibleForAccrualChanges(blockToDelete.getEarnCode(), blockToDelete.getLeaveLocalDate(), calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate());
		    }	
        }
		// recalculate summary
		if(lcf.getCalendarEntry() != null) {
			LeaveSummary ls = LmServiceLocator.getLeaveSummaryService().getLeaveSummary(targetPrincipalId, calendarEntry);
		    lcf.setLeaveSummary(ls);
		}
		return mapping.findForward("basic");
	}
	
	/**
	 * Recalculate accrual when a leave block with not-eligible-for-accrual earn code is added or deleted
	 * calculate accrual only for the calendar entry period
	 * @param earnCode
	 * @param asOfDate
	 * @param startDate
	 * @param endDate
	 */
	private void rerunAccrualForNotEligibleForAccrualChanges(String earnCode, LocalDate asOfDate, LocalDate startDate, LocalDate endDate) {
		EarnCode ec = HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, asOfDate);
		if(ec != null && ec.getEligibleForAccrual().equals("N")) {
			if(startDate != null && endDate != null) {
				// since we are only recalculating accrual for this pay period, we use "false" to not record the accrual run data
				LmServiceLocator.getLeaveAccrualService().runAccrual(TKContext.getTargetPrincipalId(), startDate.toDateTimeAtStartOfDay(), endDate.toDateTimeAtStartOfDay(), false);
			}
		}
	}
	
	// KPME-1447
	public ActionForward updateLeaveBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		LeaveCalendarDocument lcd = lcf.getLeaveCalendarDocument();
		
		String principalId = TKContext.getPrincipalId();
		String targetPrincipalId = TKContext.getTargetPrincipalId();
		CalendarEntry calendarEntry = lcf.getCalendarEntry();
		String selectedEarnCode = lcf.getSelectedEarnCode();
		String leaveBlockId = lcf.getLeaveBlockId();
		
		String documentId = lcd != null ? lcd.getDocumentId() : "";
		
		LeaveBlock updatedLeaveBlock = null;
		updatedLeaveBlock = LmServiceLocator.getLeaveBlockService().getLeaveBlock(leaveBlockId);
        if (updatedLeaveBlock.isEditable()) {
            if (StringUtils.isNotBlank(lcf.getDescription())) {
                updatedLeaveBlock.setDescription(lcf.getDescription().trim());
            }
            if (!updatedLeaveBlock.getLeaveAmount().equals(lcf.getLeaveAmount())) {
                updatedLeaveBlock.setLeaveAmount(lcf.getLeaveAmount());
            }
            
            DateTime beginDate = null;
    		DateTime endDate = null;
            
            EarnCode earnCode =  HrServiceLocator.getEarnCodeService().getEarnCode(selectedEarnCode, updatedLeaveBlock.getLeaveLocalDate()); // selectedEarnCode = hrEarnCodeId
            if(earnCode != null && earnCode.getRecordMethod().equalsIgnoreCase(HrConstants.EARN_CODE_TIME)) {
            	if(lcf.getStartTime() != null && lcf.getEndTime() != null) {
        			beginDate = new DateTime(TKUtils.convertDateStringToTimestampWithoutZone(lcf.getStartDate(), lcf.getStartTime()).getTime());
        			endDate   = new DateTime(TKUtils.convertDateStringToTimestampWithoutZone(lcf.getEndDate(), lcf.getEndTime()).getTime());
        		}  else {
        			beginDate = new DateTime(TKUtils.formatDateTimeStringNoTimezone(lcf.getStartDate()));
        			endDate = new DateTime(TKUtils.formatDateTimeStringNoTimezone(lcf.getEndDate()));
        		}
            	updatedLeaveBlock.setBeginTimestamp(new Timestamp(beginDate.getMillis()));
            	updatedLeaveBlock.setEndTimestamp(new Timestamp(endDate.getMillis()));
            	updatedLeaveBlock.setLeaveAmount(TKUtils.getHoursBetween(beginDate.getMillis(), endDate.getMillis()));
            }
            
            if (!updatedLeaveBlock.getEarnCode().equals(earnCode.getEarnCode())) {
                updatedLeaveBlock.setEarnCode(earnCode.getEarnCode());
            }
            
            LmServiceLocator.getLeaveBlockService().updateLeaveBlock(updatedLeaveBlock, principalId);
            generateLeaveCalendarChangedNotification(principalId, targetPrincipalId, documentId, calendarEntry.getHrCalendarEntryId());
            
            lcf.setLeaveAmount(null);
            lcf.setDescription(null);
            lcf.setSelectedEarnCode(null);
    		// recalculate summary
    		if(lcf.getCalendarEntry() != null) {
    			LeaveSummary ls = LmServiceLocator.getLeaveSummaryService().getLeaveSummary(targetPrincipalId, calendarEntry);
    		    lcf.setLeaveSummary(ls);
    		}
        }
        return mapping.findForward("basic");
    }
	protected void setupDocumentOnFormContext(LeaveCalendarForm leaveForm,
			LeaveCalendarDocument lcd) {
		CalendarEntry futureCalEntry = null;
		String viewPrincipal = TKContext.getTargetPrincipalId();
		CalendarEntry calEntry = leaveForm.getCalendarEntry();
		
		Date startCalDate = null;

		// some leave calendar may not have leaveCalendarDocument created based on the jobs status of this employee
		if(lcd != null) {
			if (lcd.getDocumentHeader() != null) {
				TKContext.setCurrentLeaveCalendarDocumentId(lcd.getDocumentId());
				leaveForm.setDocumentId(lcd.getDocumentId());
			}
			TKContext.setCurrentLeaveCalendarDocument(lcd);
	        TKContext.setCurrentLeaveCalendarDocumentId(lcd.getDocumentId());
			leaveForm.setLeaveCalendarDocument(lcd);
	        leaveForm.setDocumentId(lcd.getDocumentId());
	        calEntry = lcd.getCalendarEntry();
		}
	// -- put condition if it is after current period
		boolean isFutureDate = calEntry != null && LocalDate.now().toDate().compareTo(calEntry.getEndPeriodDateTime()) <= 0;
		
		// fetch previous entry
        if (calEntry != null) {
            CalendarEntry calPreEntry = HrServiceLocator
                    .getCalendarEntryService()
                    .getPreviousCalendarEntryByCalendarId(
                            calEntry.getHrCalendarId(),
                            calEntry);
            if (calPreEntry != null) {
            	
            	// Check if service date of user is after the Calendar entry
                DateTime asOfDate = calPreEntry.getEndPeriodFullDateTime().minusDays(1);
        		PrincipalHRAttributes principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(viewPrincipal, asOfDate.toLocalDate());
        		
        		if(principalHRAttributes != null) {
        			startCalDate = principalHRAttributes.getServiceDate();
        			if(startCalDate != null) {
        				if(!(calPreEntry.getBeginPeriodDate().compareTo(startCalDate) < 0)) {
                     		leaveForm.setPrevCalEntryId(calPreEntry
                                    .getHrCalendarEntryId());
                		} 
                	} else {
                		leaveForm.setPrevCalEntryId(calPreEntry
                        .getHrCalendarEntryId());
        			}
        		}
            }

            int planningMonths = ActionFormUtils.getPlanningMonthsForEmployee(viewPrincipal);
            if(planningMonths != 0) {
                List<CalendarEntry> futureCalEntries = HrServiceLocator
                        .getCalendarEntryService()
                        .getFutureCalendarEntries(
                                calEntry.getHrCalendarId(),
                                new LocalDate().toDateTimeAtStartOfDay(),
                                planningMonths);

                if (futureCalEntries != null && !futureCalEntries.isEmpty()) {
                    futureCalEntry = futureCalEntries.get(futureCalEntries
                            .size() - 1);

                    CalendarEntry calNextEntry = HrServiceLocator
                            .getCalendarEntryService()
                            .getNextCalendarEntryByCalendarId(
                                    calEntry.getHrCalendarId(),
                                    calEntry);

                    if (calNextEntry != null
                            && futureCalEntries != null
                            && calNextEntry
                                    .getBeginPeriodDateTime()
                                    .compareTo(
                                            futureCalEntry
                                                    .getBeginPeriodDateTime()) <= 0) {
                        leaveForm.setNextCalEntryId(calNextEntry
                                .getHrCalendarEntryId());
                    }
                }
            }
        }
		if(leaveForm.getViewLeaveTabsWithNEStatus()) {
			if(isFutureDate) {
                setDocEditable(leaveForm, lcd);
			} else {
				// retrieve current pay calendar date
				CalendarEntry calendarEntry = HrServiceLocator.getCalendarService()
						.getCurrentCalendarDatesForLeaveCalendar(viewPrincipal, new LocalDate().toDateTimeAtStartOfDay());
				if(calendarEntry != null) {
					leaveForm.setCurrentPayCalStart(calendarEntry.getBeginPeriodLocalDateTime().toDateTime(HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback()));
					leaveForm.setCurrentPayCalEnd(calendarEntry.getEndPeriodLocalDateTime().toDateTime(HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback()));
				}
			}
		} else {
            setDocEditable(leaveForm, lcd);
		}
		leaveForm.setCalendarEntry(calEntry);
		if(calEntry != null) {
			leaveForm.setCalEntryId(calEntry.getHrCalendarEntryId());
		}
		leaveForm.setOnCurrentPeriod(ActionFormUtils.getOnCurrentPeriodFlag(calEntry));

	}

    private void setDocEditable(LeaveCalendarForm leaveForm, LeaveCalendarDocument lcd) {
    	leaveForm.setDocEditable(false);
    	if(lcd == null) {
    		// working on own calendar
    		 if(TKContext.getTargetPrincipalId().equals(GlobalVariables.getUserSession().getPrincipalId())) {
    			 leaveForm.setDocEditable(true); 
    		 } else {
    			 if(TKContext.isSystemAdmin()
                     || TKContext.isLocationAdmin()
                     || TKContext.isReviewer()
                     || TKContext.isAnyApprover()) {
    				 	leaveForm.setDocEditable(true);
    			 }
             }
    	} else {
	        if (TKContext.isSystemAdmin() && !StringUtils.equals(lcd.getPrincipalId(), GlobalVariables.getUserSession().getPrincipalId())) {
	            leaveForm.setDocEditable(true);
	        } else {
	            boolean docFinal = lcd.getDocumentHeader().getDocumentStatus().equals(HrConstants.ROUTE_STATUS.FINAL);
	            if (!docFinal) {
	                if(StringUtils.equals(lcd.getPrincipalId(), GlobalVariables.getUserSession().getPrincipalId())
	                        || TKContext.isSystemAdmin()
	                        || TKContext.isLocationAdmin()
	                        || TKContext.isReviewer()
	                        || TKContext.isAnyApprover()) {
	                    leaveForm.setDocEditable(true);
	                }
	
	                //if the leave Calendar has been approved by at least one of the approvers, the employee should not be able to edit it
	                if (StringUtils.equals(lcd.getPrincipalId(), GlobalVariables.getUserSession().getPrincipalId())
	                        && lcd.getDocumentHeader().getDocumentStatus().equals(HrConstants.ROUTE_STATUS.ENROUTE)) {
	                    Collection actions = KEWServiceLocator.getActionTakenService().findByDocIdAndAction(lcd.getDocumentHeader().getDocumentId(), HrConstants.DOCUMENT_ACTIONS.APPROVE);
	                    if(!actions.isEmpty()) {
	                        leaveForm.setDocEditable(false);
	                    }
	                }
	            }
	        }
    	}
    }
	
	public ActionForward gotoCurrentPayPeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveCalendarForm lcf = (LeaveCalendarForm) form;
		String viewPrincipal = TKContext.getTargetPrincipalId();
		CalendarEntry calendarEntry = HrServiceLocator.getCalendarService().getCurrentCalendarDatesForLeaveCalendar(viewPrincipal, new LocalDate().toDateTimeAtStartOfDay());
		lcf.setCalendarEntry(calendarEntry);
		if(calendarEntry != null) {
			lcf.setCalEntryId(calendarEntry.getHrCalendarEntryId());
		}
		lcf.setOnCurrentPeriod(ActionFormUtils.getOnCurrentPeriodFlag(calendarEntry));
	
		LeaveCalendarDocument lcd = null;
		// use jobs to find out if this leave calendar should have a document created or not
		boolean createFlag = LmServiceLocator.getLeaveCalendarService().shouldCreateLeaveDocument(viewPrincipal, calendarEntry);
		if(createFlag) {
			 lcd = LmServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(viewPrincipal, calendarEntry);
		}
		if (lcd != null) {
			lcf.setAssignmentDescriptions(HrServiceLocator.getAssignmentService().getAssignmentDescriptions(lcd));
		} else {
			List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(viewPrincipal, calendarEntry);
			lcf.setAssignmentDescriptions(HrServiceLocator.getAssignmentService().getAssignmentDescriptionsForAssignments(assignments));  
		}
		setupDocumentOnFormContext(lcf, lcd);
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
	        CalendarEntry ce = HrServiceLocator.getCalendarEntryService()
				.getCalendarEntry(request.getParameter("selectedPP").toString());
			if(ce != null) {
				String viewPrincipal = TKContext.getTargetPrincipalId();
				lcf.setCalEntryId(ce.getHrCalendarEntryId());
				LeaveCalendarDocument lcd = null;
				// use jobs to find out if this leave calendar should have a document created or not
				boolean createFlag = LmServiceLocator.getLeaveCalendarService().shouldCreateLeaveDocument(viewPrincipal, ce);
				if(createFlag) {
					 lcd = LmServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(viewPrincipal, ce);
				}
				if(lcd != null) {
					lcf.setAssignmentDescriptions(HrServiceLocator.getAssignmentService().getAssignmentDescriptions(lcd));
				} else {
					List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(viewPrincipal, ce);
					lcf.setAssignmentDescriptions(HrServiceLocator.getAssignmentService().getAssignmentDescriptionsForAssignments(assignments));  
				}
				setupDocumentOnFormContext(lcf, lcd);
			}
		}
		return mapping.findForward("basic");
	}
	
	private void generateLeaveCalendarChangedNotification(String principalId, String targetPrincipalId, String documentId, String hrCalendarEntryId) {
		if (!StringUtils.equals(principalId, targetPrincipalId)) {
			EntityNamePrincipalName person = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId);
			if (person != null && person.getDefaultName() != null) {
				String subject = "Leave Calendar Modification Notice";
				StringBuilder message = new StringBuilder();
				message.append("Your Leave Calendar was changed by ");
				message.append(person.getDefaultName().getCompositeNameUnmasked());
				message.append(" on your behalf.");
				message.append(SystemUtils.LINE_SEPARATOR);
				message.append(getLeaveCalendarURL(documentId, hrCalendarEntryId));
				
				HrServiceLocator.getKPMENotificationService().sendNotification(subject, message.toString(), targetPrincipalId);
			}
		}
	}
	
	private void generateLeaveBlockDeletionNotification(List<String> approverIdList, String employeeId, String userId, String dateString, String hrString) {
        EntityNamePrincipalName employee = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(employeeId);
        EntityNamePrincipalName user = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(userId);
		if (employee != null
                && user != null
                && employee.getDefaultName() != null
                && user.getDefaultName() != null) {
			String subject = "Leave Request Deletion Notice";
			StringBuilder message = new StringBuilder();
			message.append("An Approved leave request of ").append(hrString).append(" hours on Date ").append(dateString);
			message.append(" for ").append(employee.getDefaultName().getCompositeNameUnmasked()).append(" was deleted by ");
			message.append(user.getDefaultName().getCompositeNameUnmasked());
			for(String anId : approverIdList) {
				HrServiceLocator.getKPMENotificationService().sendNotification(subject, message.toString(), anId);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private String getLeaveCalendarURL(String documentId, String hrCalendarEntryId) {
		Properties params = new Properties();
		params.put("documentId", documentId);
		params.put("calEntryId", hrCalendarEntryId);
		return UrlFactory.parameterizeUrl(getApplicationBaseUrl() + "/LeaveCalendar.do", params);
	}
    
    /**
	 * Handles the PAYOUT action of balance transfers issued from the leave calendar with frequency "on demand".
	 * 
	 * This action should be triggered after the user submits to a prompt generated by clicking a "PAYOUT" button on the leave
	 * calendar. This button should only be displayed if, for the current pay period, a max balance has been reached
	 * and the max balance action frequency is set to "On-Demand". The prompt must allow the user to edit the transfer amount.
	 * It may or may not need to show the "to" and "from" accrual categories in the initial prompt, but could on a confirmation
	 * prompt - along with the transfer amount adjusted by the max balance conversion factor.
	 * 
	 * Balance transfers with frequency of leave approval should be handled during the submission of the
	 * leave calendar document for approval and should be automated.
	 * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward payoutOnDemandBalanceTransfer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	/**
    	 * TODO: create one new leave block, if applicable; the amount forfeited by this transfer action.
    	 * 
    	 * The amount transfered, pending adjustment via the max balance conversion factor, will be put into a pay out earn code
    	 * that can be redeemed/used by the employee at a later time.
    	 */
    	
    	return mapping.findForward("basic");
    }

    /**
     * Leave Payout
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward leavePayout(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        return mapping.findForward("basic");
    }
    
    public ActionForward docHandler(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = mapping.findForward("basic");
        String command = request.getParameter("command");
        
    	if (StringUtils.equals(command, "displayDocSearchView") || StringUtils.equals(command, "displayActionListView")) {
        	String docId = (String) request.getParameter("docId");
        	LeaveCalendarDocument leaveCalendarDocument = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(docId);
        	String timesheetPrincipalName = KimApiServiceLocator.getPersonService().getPerson(leaveCalendarDocument.getPrincipalId()).getPrincipalName();
        	
        	String principalId = TKContext.getTargetPrincipalId();
        	String principalName = KimApiServiceLocator.getPersonService().getPerson(principalId).getPrincipalName();
        	
        	StringBuilder builder = new StringBuilder();
        	if (!StringUtils.equals(principalName, timesheetPrincipalName)) {
        		if (StringUtils.equals(command, "displayDocSearchView")) {
            		builder.append("changeTargetPerson.do?methodToCall=changeTargetPerson");
            		builder.append("&documentId=");
            		builder.append(docId);
            		builder.append("&principalName=");
            		builder.append(timesheetPrincipalName);
            		builder.append("&targetUrl=LeaveCalendar.do");
            		builder.append("?docmentId=" + docId);
            		builder.append("&returnUrl=LeaveApproval.do");
            	} else {
            		builder.append("LeaveApproval.do");
            	}
        	} else {
        		builder.append("LeaveCalendar.do");
        		builder.append("?docmentId=" + docId);
        	}
        	
        	forward = new ActionRedirect(builder.toString());
        }
    	
    	return forward;
    }

}
