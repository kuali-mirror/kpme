package org.kuali.hr.lm.leave.approval.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.approval.web.ApprovalLeaveSummaryRow;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.person.TKPerson;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;

public class LeaveApprovalServiceImpl implements LeaveApprovalService{
	
	@Override
	public List<ApprovalLeaveSummaryRow> getLeaveApprovalSummaryRows(List<TKPerson> persons, CalendarEntries payCalendarEntries, List<String> headers) {
		Date payBeginDate = payCalendarEntries.getBeginPeriodDate();
		Date payEndDate = payCalendarEntries.getEndPeriodDate();
		List<ApprovalLeaveSummaryRow> rowList = new ArrayList<ApprovalLeaveSummaryRow>();		
		
		for(TKPerson aPerson : persons) {
			String principalId = aPerson.getPrincipalId();
			ApprovalLeaveSummaryRow aRow = new ApprovalLeaveSummaryRow();
			aRow.setName(aPerson.getPrincipalName());
			aRow.setPrincipalId(aPerson.getPrincipalId());
			
			String lastApprovedString = "No previous approved leave calendar information";
			LeaveCalendarDocumentHeader lastApprovedDoc = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getMaxEndDateApprovedLeaveCalendar(principalId);
			if(lastApprovedDoc != null) {
				lastApprovedString = "Last Approved: " + (new SimpleDateFormat("MMM yyyy")).format(lastApprovedDoc.getBeginDate());
			}
			aRow.setLastApproveMessage(lastApprovedString);
			
			LeaveCalendarDocumentHeader aDoc = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, payBeginDate, payEndDate);
			if(aDoc != null) {
				aRow.setDocumentId(aDoc.getDocumentId());
				aRow.setApprovalStatus(aDoc.getDocumentStatus());
			}
			List<LeaveCalendarDocumentHeader> docList = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getAllDelinquentDocumentHeadersForPricipalId(principalId);
			if(docList.size() > LMConstants.DELINQUENT_LEAVE_CALENDARS_LIMIT ) {
				aRow.setMoreThanOneCalendar(true);
			}
			
			List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, payBeginDate, payEndDate);
			aRow.setLeaveBlockList(leaveBlocks);
			Map<String, Map<String, BigDecimal>> leaveHoursToPayLabelMap = TkServiceLocator.getLeaveApprovalService().getLeaveHoursToPayDayMap(leaveBlocks, headers);
			aRow.setLeaveHoursToPayLabelMap(leaveHoursToPayLabelMap);
			
			rowList.add(aRow);
		}
		
		return rowList;
	}
	

	public Map<String, LeaveCalendarDocumentHeader> getLeaveDocumehtHeaderMap(List<TKPerson> persons, Date payBeginDate, Date payEndDate) {
		Map<String, LeaveCalendarDocumentHeader> leaveDocumentHeaderMap = new LinkedHashMap<String, LeaveCalendarDocumentHeader>();
		if (CollectionUtils.isNotEmpty(persons)) {
			for (TKPerson person : persons) {
				String principalId = person.getPrincipalId();
				LeaveCalendarDocumentHeader aHeader = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, payBeginDate, payEndDate);
				if(aHeader != null) {
					leaveDocumentHeaderMap.put(principalId, aHeader);
				}
				
			}
		}
		return leaveDocumentHeaderMap;
	}
	
	@Override
	public Map<String, Map<String, BigDecimal>> getLeaveHoursToPayDayMap(List<LeaveBlock> leaveBlocks,List<String> headers) {
		Map<String, Map<String, BigDecimal>> leaveHoursToPayLabelMap = new LinkedHashMap<String, Map<String, BigDecimal>>();
		
		for(String aString : headers) {
			leaveHoursToPayLabelMap.put(aString, null);
		}
		if(CollectionUtils.isNotEmpty(leaveBlocks)) {
			for(LeaveBlock lb : leaveBlocks) {
				Map<String, BigDecimal> dayHoursMap =  new LinkedHashMap<String, BigDecimal>();
				LocalDateTime leaveDate = (new DateTime(lb.getLeaveDate())).toLocalDateTime();
				String dateString = leaveDate.toString(TkConstants.DT_ABBREV_DATE_FORMAT);
				
				AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(lb.getAccrualCategoryId());
				if(ac != null && ac.getShowOnGrid().equals("Y")) {
					BigDecimal amount = lb.getLeaveAmount();
					if(leaveHoursToPayLabelMap.get(dateString) != null ) {
						dayHoursMap = leaveHoursToPayLabelMap.get(dateString);
						if(leaveHoursToPayLabelMap.get(dateString).get(ac.getAccrualCategory()) != null) {
							amount = leaveHoursToPayLabelMap.get(dateString).get(ac.getAccrualCategory()).add(lb.getLeaveAmount());
						}
					}
					dayHoursMap.put(ac.getAccrualCategory(), amount);
				}
				
				leaveHoursToPayLabelMap.put(dateString, dayHoursMap);
			}
		}
		return leaveHoursToPayLabelMap;
	}
	
	@Override
	public List<Map<String, Object>> getLaveApprovalDetailSectins(LeaveCalendarDocumentHeader lcdh) {
		
		List<Map<String, Object>> acRows = new ArrayList<Map<String, Object>>();
		
		String principalId = lcdh.getPrincipalId();
		CalendarEntries calendarEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesByBeginAndEndDate(lcdh.getBeginDate(), lcdh.getEndDate());
		if(calendarEntry != null) {
			Date beginDate = calendarEntry.getBeginPeriodDate();
			Date endDate = calendarEntry.getEndPeriodDate();
			
			List<String> headers = TkServiceLocator.getLeaveSummaryService().getHeaderForSummary(calendarEntry);
			List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, beginDate, endDate);
			Map<String, Map<String, BigDecimal>> leaveHoursToPayLabelMap = TkServiceLocator.getLeaveApprovalService().getLeaveHoursToPayDayMap(leaveBlocks, headers);

			//get all accrual categories of this employee
			PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, endDate);
			if(pha != null) {
				List<AccrualCategory> acList = TkServiceLocator.getAccrualCategoryService()
					.getActiveLeaveAccrualCategoriesForLeavePlan(pha.getLeavePlan(), new java.sql.Date(endDate.getTime()));
				List<BigDecimal> acDayDetails = new ArrayList<BigDecimal>();
				
				for(AccrualCategory ac : acList) {
					Map<String, Object> displayMap = new HashMap<String, Object>();
					BigDecimal totalAmount = BigDecimal.ZERO;
					displayMap.put("accrualCategory", ac.getAccrualCategory());
					int index = 0;
					for(String aDateString : headers) {
						acDayDetails.add(index, null);
						if(leaveHoursToPayLabelMap.get(aDateString) != null && leaveHoursToPayLabelMap.get(aDateString).containsKey(ac.getAccrualCategory())) {
							BigDecimal amount =  leaveHoursToPayLabelMap.get(aDateString).get(ac.getAccrualCategory());
							totalAmount = totalAmount.add(amount);
							acDayDetails.set(index, amount);
						}
						index++;
					}
					displayMap.put("periodUsage", totalAmount);
					displayMap.put("availableBalance", BigDecimal.ZERO);
					displayMap.put("daysDetail", acDayDetails);
					acRows.add(displayMap);
				}
			}
			
		}
		return acRows;
	}
}
