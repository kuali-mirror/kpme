package org.kuali.hr.lm.leave.web;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;

public class LeaveBlockDisplayAction extends TkAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.execute(mapping, form, request, response);
		LeaveBlockDisplayForm lbdf = (LeaveBlockDisplayForm) form;	
		
		String principalId = TKContext.getUser().getPrincipalId();
		if(TKContext.getUser().getCurrentTargetPerson() != null) {
			lbdf.setTargetName(TKContext.getUser().getCurrentTargetPerson().getName());
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
		
		
		List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, beginDate, endDate);
		if(leaveBlocks != null && !leaveBlocks.isEmpty()) {
			for(LeaveBlock leaveBlock : leaveBlocks) {
				if(leaveBlock.getRequestStatus() != null) {
					leaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS_STRINGS.get(leaveBlock.getRequestStatus()));
				} else if(leaveBlock.getAccrualGenerated() != null && leaveBlock.getAccrualGenerated()) { // accural generated = A -- Accural
					leaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.ACCURAL);
				} else { // default USAGE
					leaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.USAGE);
				}
			}
		}
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
		if(inActiveLeaveEntries != null && !inActiveLeaveEntries.isEmpty()) {
			for(LeaveBlockHistory leaveBlockHistory:inActiveLeaveEntries) {
				if(leaveBlockHistory.getAction()!= null && leaveBlockHistory.getAction().equalsIgnoreCase(LMConstants.ACTION.DELETE)) {
					leaveBlockHistory.setPrincipalIdModified(leaveBlockHistory.getPrincipalIdDeleted());
					leaveBlockHistory.setTimestamp(leaveBlockHistory.getTimestampDeleted());
				}
			}
		}
		lbdf.setInActiveLeaveEntries(inActiveLeaveEntries);
		
	    return forward;
	}
}
