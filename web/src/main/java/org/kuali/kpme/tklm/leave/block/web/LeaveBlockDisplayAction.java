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
package org.kuali.kpme.tklm.leave.block.web;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.kuali.kpme.core.bo.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.bo.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.block.LeaveBlockHistory;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.summary.LeaveSummary;
import org.kuali.kpme.tklm.leave.summary.LeaveSummaryRow;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.rice.kew.api.KewApiConstants;

public class LeaveBlockDisplayAction extends KPMEAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.execute(mapping, form, request, response);
		
		LeaveBlockDisplayForm lbdf = (LeaveBlockDisplayForm) form;	

		PrincipalHRAttributes principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(HrContext.getTargetPrincipalId(), LocalDate.now());
		String leavePlan = (principalHRAttributes != null) ? principalHRAttributes.getLeavePlan() : null;

		if (lbdf.getNavString() == null) {
			lbdf.setYear(LocalDate.now().getYear());
		} else if(lbdf.getNavString().equals("NEXT")) {
			lbdf.setYear(lbdf.getYear() + 1);
		} else if(lbdf.getNavString().equals("PREV")) {
			lbdf.setYear(lbdf.getYear() - 1);
		}
		LocalDate beginDate = new LocalDate(lbdf.getYear(), 1, 1);
		LocalDate serviceDate = (principalHRAttributes != null) ? principalHRAttributes.getServiceLocalDate() : beginDate;
		LocalDate endDate = new LocalDate(lbdf.getYear(), 12, 31);

		lbdf.setAccrualCategories(getAccrualCategories(leavePlan));
		lbdf.setLeaveEntries(getLeaveEntries(HrContext.getTargetPrincipalId(), serviceDate, beginDate, endDate, lbdf.getAccrualCategories()));

		List<LeaveBlockHistory> correctedLeaveEntries = LmServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistoriesForLeaveDisplay(HrContext.getTargetPrincipalId(), beginDate, endDate, Boolean.TRUE);
		if (correctedLeaveEntries != null) {
			for (LeaveBlockHistory leaveBlockHistory : correctedLeaveEntries) {
				if (leaveBlockHistory.getAction() != null && leaveBlockHistory.getAction().equalsIgnoreCase(HrConstants.ACTION.DELETE)) {
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
		
		List<LeaveBlockHistory> inActiveLeaveEntries = LmServiceLocator.getLeaveBlockHistoryService() .getLeaveBlockHistoriesForLeaveDisplay(HrContext.getTargetPrincipalId(), beginDate, endDate, Boolean.FALSE);
		List<LeaveBlockHistory> leaveEntries = null;
		if (inActiveLeaveEntries != null) {
			leaveEntries = new ArrayList<LeaveBlockHistory>();
			for (LeaveBlockHistory leaveBlockHistory:inActiveLeaveEntries) {
				if (leaveBlockHistory.getAccrualGenerated() == null || !leaveBlockHistory.getAccrualGenerated()) {
					if (leaveBlockHistory.getAction()!= null) {
                        if (leaveBlockHistory.getAction().equalsIgnoreCase(HrConstants.ACTION.DELETE)) {
                            leaveBlockHistory.setPrincipalIdModified(leaveBlockHistory.getPrincipalIdDeleted());
                            leaveBlockHistory.setTimestamp(leaveBlockHistory.getTimestampDeleted());
                        } else if (leaveBlockHistory.getAction().equalsIgnoreCase(HrConstants.ACTION.MODIFIED)) {
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
						leaveBlockHistory.setRequestStatus(HrConstants.REQUEST_STATUS_STRINGS.get(leaveBlockHistory.getRequestStatus()));
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
