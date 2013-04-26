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
package org.kuali.hr.tklm.leave.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.LocalDate;
import org.kuali.hr.core.bo.accrualcategory.AccrualCategory;
import org.kuali.hr.core.bo.principal.PrincipalHRAttributes;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.core.web.HrAction;
import org.kuali.hr.tklm.common.TKContext;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.block.LeaveBlockHistory;
import org.kuali.hr.tklm.leave.service.LmServiceLocator;
import org.kuali.hr.tklm.leave.summary.LeaveSummary;
import org.kuali.hr.tklm.leave.summary.LeaveSummaryRow;
import org.kuali.hr.tklm.leave.util.LMConstants;
import org.kuali.hr.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.rice.kew.api.KewApiConstants;

public class LeaveBlockDisplayAction extends HrAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.execute(mapping, form, request, response);
		
		LeaveBlockDisplayForm lbdf = (LeaveBlockDisplayForm) form;	

		PrincipalHRAttributes principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(TKContext.getTargetPrincipalId(), LocalDate.now());
		String leavePlan = (principalHRAttributes != null) ? principalHRAttributes.getLeavePlan() : null;

		Calendar currentCalendar = Calendar.getInstance();
		if (lbdf.getNavString() == null) {
			lbdf.setYear(currentCalendar.get(Calendar.YEAR));
		} else if(lbdf.getNavString().equals("NEXT")) {
			lbdf.setYear(lbdf.getYear() + 1);
		} else if(lbdf.getNavString().equals("PREV")) {
			lbdf.setYear(lbdf.getYear() - 1);
		}
		currentCalendar.set(lbdf.getYear(), 0, 1);
		LocalDate serviceDate = (principalHRAttributes != null) ? principalHRAttributes.getServiceLocalDate() : LocalDate.fromCalendarFields(currentCalendar);
		LocalDate beginDate = LocalDate.fromCalendarFields(currentCalendar);
		currentCalendar.set(lbdf.getYear(), 11, 31);
		LocalDate endDate = LocalDate.fromCalendarFields(currentCalendar);

		lbdf.setAccrualCategories(getAccrualCategories(leavePlan));
		lbdf.setLeaveEntries(getLeaveEntries(TKContext.getTargetPrincipalId(), serviceDate, beginDate, endDate, lbdf.getAccrualCategories()));

		List<LeaveBlockHistory> correctedLeaveEntries = LmServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistoriesForLeaveDisplay(TKContext.getTargetPrincipalId(), beginDate, endDate, Boolean.TRUE);
		if (correctedLeaveEntries != null) {
			for (LeaveBlockHistory leaveBlockHistory : correctedLeaveEntries) {
				if (leaveBlockHistory.getAction() != null && leaveBlockHistory.getAction().equalsIgnoreCase(LMConstants.ACTION.DELETE)) {
					leaveBlockHistory.setPrincipalIdModified(leaveBlockHistory.getPrincipalIdDeleted());
					leaveBlockHistory.setTimestamp(leaveBlockHistory.getTimestampDeleted());
				}
				// Set Description
				if(leaveBlockHistory.getDescription() == null || leaveBlockHistory.getDescription().trim().isEmpty()) {
					leaveBlockHistory.setDescription(this.retrieveDescriptionAccordingToLeaveType(leaveBlockHistory.getLeaveBlockType()));
				}
			}
		}
		lbdf.setCorrectedLeaveEntries(correctedLeaveEntries);
		
		List<LeaveBlockHistory> inActiveLeaveEntries = LmServiceLocator.getLeaveBlockHistoryService() .getLeaveBlockHistoriesForLeaveDisplay(TKContext.getTargetPrincipalId(), beginDate, endDate, Boolean.FALSE);
		List<LeaveBlockHistory> leaveEntries = null;
		if (inActiveLeaveEntries != null) {
			leaveEntries = new ArrayList<LeaveBlockHistory>();
			for (LeaveBlockHistory leaveBlockHistory:inActiveLeaveEntries) {
				if (leaveBlockHistory.getAccrualGenerated() == null || !leaveBlockHistory.getAccrualGenerated()) {
					if (leaveBlockHistory.getAction()!= null) {
                        if (leaveBlockHistory.getAction().equalsIgnoreCase(LMConstants.ACTION.DELETE)) {
                            leaveBlockHistory.setPrincipalIdModified(leaveBlockHistory.getPrincipalIdDeleted());
                            leaveBlockHistory.setTimestamp(leaveBlockHistory.getTimestampDeleted());
                        } else if (leaveBlockHistory.getAction().equalsIgnoreCase(LMConstants.ACTION.MODIFIED)) {
                            leaveBlockHistory.setPrincipalIdModified(leaveBlockHistory.getPrincipalIdDeleted());
                        }
                    }

					this.assignDocumentStatusToLeaveBlock(leaveBlockHistory);
					// if it is not generated by accrual then add it to the inactivelist
					// Set Description
					if(leaveBlockHistory.getDescription() == null || leaveBlockHistory.getDescription().trim().isEmpty()) {
						leaveBlockHistory.setDescription(this.retrieveDescriptionAccordingToLeaveType(leaveBlockHistory.getLeaveBlockType()));
					}
					if (StringUtils.isNotBlank(leaveBlockHistory.getRequestStatus())) {
						leaveBlockHistory.setRequestStatus(LMConstants.REQUEST_STATUS_STRINGS.get(leaveBlockHistory.getRequestStatus()));
					}
					leaveEntries.add(leaveBlockHistory);
				}
			}
		}
		lbdf.setInActiveLeaveEntries(leaveEntries);
		
	    return forward;
	}
	
	private List<AccrualCategory> getAccrualCategories(String leavePlan) {
		List<AccrualCategory> accrualCategories = new ArrayList<AccrualCategory>();
		
		List<AccrualCategory> allAccrualCategories = HrServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(leavePlan, LocalDate.now());
		if (allAccrualCategories != null) {
			for (AccrualCategory ac : allAccrualCategories) {
				if (StringUtils.equalsIgnoreCase(ac.getShowOnGrid(), "Y")) {
					accrualCategories.add(ac);
				}
			}
			Collections.sort(accrualCategories, new Comparator<AccrualCategory>() {
				@Override
				public int compare(AccrualCategory o1, AccrualCategory o2) {
					return ObjectUtils.compare(o1.getAccrualCategory(), o2.getAccrualCategory());
				}
			});
		}
		
		return accrualCategories;
	}
	
	private List<LeaveBlockDisplay> getLeaveEntries(String principalId, LocalDate serviceDate, LocalDate beginDate, LocalDate endDate, List<AccrualCategory> accrualCategories) {
		List<LeaveBlockDisplay> leaveEntries = new ArrayList<LeaveBlockDisplay>();
		
		List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, beginDate, endDate);
		
		for (LeaveBlock leaveBlock : leaveBlocks) {
            if (!leaveBlock.getLeaveBlockType().equals(LMConstants.LEAVE_BLOCK_TYPE.CARRY_OVER)) {
			    leaveEntries.add(new LeaveBlockDisplay(leaveBlock));
            }
		}
		Collections.sort(leaveEntries, new Comparator<LeaveBlockDisplay>() {
			@Override
			public int compare(LeaveBlockDisplay o1, LeaveBlockDisplay o2) {
				return ObjectUtils.compare(o1.getLeaveDate(), o2.getLeaveDate());
			}
		});
		
		SortedMap<String, BigDecimal> accrualBalances = getPreviousAccrualBalances(principalId, serviceDate, beginDate, accrualCategories);
				
		for (LeaveBlockDisplay leaveEntry : leaveEntries) {
			for (AccrualCategory accrualCategory : accrualCategories) {
				if (!accrualBalances.containsKey(accrualCategory.getAccrualCategory())) {
					accrualBalances.put(accrualCategory.getAccrualCategory(), BigDecimal.ZERO);
				}
				BigDecimal currentAccrualBalance = accrualBalances.get(accrualCategory.getAccrualCategory());
				
				if (StringUtils.equals(leaveEntry.getAccrualCategory(), accrualCategory.getAccrualCategory())) {
					BigDecimal accruedBalance = currentAccrualBalance.add(leaveEntry.getLeaveAmount());
					accrualBalances.put(accrualCategory.getAccrualCategory(), accruedBalance);
				}
				
				leaveEntry.setAccrualBalance(accrualCategory.getAccrualCategory(), accrualBalances.get(accrualCategory.getAccrualCategory()));
			}
		}
		
		return leaveEntries;
	}
	
	private SortedMap<String, BigDecimal> getPreviousAccrualBalances(String principalId, LocalDate serviceDate, LocalDate beginDate, List<AccrualCategory> accrualCategories) {
		SortedMap<String, BigDecimal> previousAccrualBalances = new TreeMap<String, BigDecimal>();

        LeaveSummary leaveSummary = LmServiceLocator.getLeaveSummaryService().getLeaveSummaryAsOfDateWithoutFuture(principalId, beginDate);

        for (LeaveSummaryRow row : leaveSummary.getLeaveSummaryRows()) {
            previousAccrualBalances.put(row.getAccrualCategory(), row.getLeaveBalance());
        }
		
		return previousAccrualBalances;
	}

	private void assignDocumentStatusToLeaveBlock(LeaveBlock leaveBlock) {
		//lookup document associated with this leave block and assign document status
		if(StringUtils.isNotEmpty(leaveBlock.getDocumentId())) {
			LeaveCalendarDocumentHeader lcdh = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(leaveBlock.getDocumentId());
			if(lcdh != null ) {
				leaveBlock.setDocumentStatus(KewApiConstants.DOCUMENT_STATUSES.get(lcdh.getDocumentStatus()));
			}
		}
	}
	
	private String retrieveDescriptionAccordingToLeaveType(String leaveType) {
		String description = null;
		description = LMConstants.LEAVE_BLOCK_TYPE_MAP.get(leaveType);
		return description;
	}
}
