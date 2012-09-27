package org.kuali.hr.lm.leave.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kew.api.KewApiConstants;



public class LeaveBlockDisplayAction extends TkAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.execute(mapping, form, request, response);
		LeaveBlockDisplayForm lbdf = (LeaveBlockDisplayForm) form;	
		List<String> accList = new ArrayList<String>();
        String principalId = TKUser.getCurrentTargetPerson().getPrincipalId();
		if(TKUser.getCurrentTargetPerson() != null) {
			lbdf.setTargetName(TKUser.getCurrentTargetPerson().getName());
		}
		
		if(lbdf.getNavString() == null) {
			lbdf.setYear(Calendar.getInstance().get(Calendar.YEAR));
		} else if(lbdf.getNavString().equals("NEXT")) {
			lbdf.setYear(lbdf.getYear() + 1);
		} else if(lbdf.getNavString().equals("PREV")) {
			lbdf.setYear(lbdf.getYear() - 1);
		}
		
		// List of leaveBlocks for selected year
		Calendar currCal = Calendar.getInstance();
		currCal.set(lbdf.getYear(), 0, 1);
		Date beginDate = TKUtils.getTimelessDate(currCal.getTime());
		currCal.set(lbdf.getYear(), 11, 31);
		Date endDate = TKUtils.getTimelessDate(currCal.getTime());
		
		Map<String, AccrualCategory> accrualCategoryMap = null;
		List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, beginDate, endDate);
		if(leaveBlocks != null && !leaveBlocks.isEmpty()) {
			String leavePlan = null;
			
			// find leave plan
			PrincipalHRAttributes principalHRAttributes = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, TKUtils.getCurrentDate());
			if(principalHRAttributes != null) {
				leavePlan = principalHRAttributes.getLeavePlan();
				List<AccrualCategory> accrualCategories = TkServiceLocator.getAccrualCategoryService().getActiveLeaveAccrualCategoriesForLeavePlan(leavePlan, TKUtils.getCurrentDate());
				if(accrualCategories != null && !accrualCategories.isEmpty()) {
					accrualCategoryMap = new HashMap<String,AccrualCategory>();
					for(AccrualCategory ac : accrualCategories) {
						if(ac.getShowOnGrid()!=null && ac.getShowOnGrid().equalsIgnoreCase("Y")) {
							// put in list or map
							accrualCategoryMap.put(ac.getLmAccrualCategoryId(), ac);
							accList.add(ac.getAccrualCategory());
						}
					}
				}
			}
			
			for(LeaveBlock leaveBlock : leaveBlocks) {
				if(leaveBlock.getRequestStatus() != null) {
					leaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS_STRINGS.get(leaveBlock.getRequestStatus()));
				} else if(leaveBlock.getAccrualGenerated() != null && leaveBlock.getAccrualGenerated()) { // accural generated = A -- Accural
					leaveBlock.setRequestStatus("");
				} else { // default USAGE
					leaveBlock.setRequestStatus("");
					// KPME-1690 : set if usage then amount should be appeared as negative
					leaveBlock.setLeaveAmount(leaveBlock.getLeaveAmount().multiply(new BigDecimal(-1)));
				}
				
				// set accrual balance map for the leaveblock
				Map<String, Double> accrualBalances = new TreeMap<String, Double>();
				if(accrualCategoryMap != null && !accrualCategoryMap.isEmpty()) {
					for(String accCatKey : accrualCategoryMap.keySet()){
						AccrualCategory accCat = accrualCategoryMap.get(accCatKey);
						Double accrualBalance = TkServiceLocator.getLeaveBlockService().calculateAccrualBalance(leaveBlock.getTimestamp(), accCatKey, principalId);
						accrualBalances.put(accCatKey, accrualBalance);
					}
				}
				
				leaveBlock.setAccrualBalances(accrualBalances);
				assignDocumentStatusToLeaveBlock(leaveBlock);
			}
		}
		Collections.sort(accList);
		lbdf.setAccrualCategoires(accList);
		lbdf.setLeaveEntries(leaveBlocks);

		List<LeaveBlockHistory> correctedLeaveEntries = TkServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistoriesForLeaveDisplay(principalId, beginDate, endDate, Boolean.TRUE);
		if(correctedLeaveEntries != null && !correctedLeaveEntries.isEmpty()) {
			for(LeaveBlockHistory leaveBlockHistory:correctedLeaveEntries) {
				if(leaveBlockHistory.getAction()!= null && leaveBlockHistory.getAction().equalsIgnoreCase(LMConstants.ACTION.DELETE)) {
					leaveBlockHistory.setPrincipalIdModified(leaveBlockHistory.getPrincipalIdDeleted());
					leaveBlockHistory.setTimestamp(leaveBlockHistory.getTimestampDeleted());
				}
			}
		}
		lbdf.setCorrectedLeaveEntries(correctedLeaveEntries);
		
		List<LeaveBlockHistory> inActiveLeaveEntries = TkServiceLocator.getLeaveBlockHistoryService() .getLeaveBlockHistoriesForLeaveDisplay(principalId, beginDate, endDate, Boolean.FALSE);
		List<LeaveBlockHistory> leaveEntries = null;
		if(inActiveLeaveEntries != null && !inActiveLeaveEntries.isEmpty()) {
			leaveEntries = new LinkedList<LeaveBlockHistory>();
			for(LeaveBlockHistory leaveBlockHistory:inActiveLeaveEntries) {
				if(leaveBlockHistory.getAccrualGenerated() == null || !leaveBlockHistory.getAccrualGenerated()) {
					if(leaveBlockHistory.getAction()!= null && leaveBlockHistory.getAction().equalsIgnoreCase(LMConstants.ACTION.DELETE)) {
						leaveBlockHistory.setPrincipalIdModified(leaveBlockHistory.getPrincipalIdDeleted());
						leaveBlockHistory.setTimestamp(leaveBlockHistory.getTimestampDeleted());
					}
					this.assignDocumentStatusToLeaveBlock(leaveBlockHistory);
					// if it is not generated by accrual then add it to the inactivelist
					leaveEntries.add(leaveBlockHistory);
				}
			}
		}
		lbdf.setInActiveLeaveEntries(leaveEntries);
		
	    return forward;
	}

	private void assignDocumentStatusToLeaveBlock(LeaveBlock leaveBlock) {
		//lookup document associated with this leave block and assign document status
		if(StringUtils.isNotEmpty(leaveBlock.getDocumentId())) {
			LeaveCalendarDocumentHeader lcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(leaveBlock.getDocumentId());
			if(lcdh != null ) {
				leaveBlock.setDocumentStatus(KewApiConstants.DOCUMENT_STATUSES.get(lcdh.getDocumentStatus()));
			}
		}
	}
}
