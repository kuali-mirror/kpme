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
package org.kuali.kpme.tklm.leave.workflow.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.kpme.core.util.TKContext;
import org.kuali.kpme.core.util.TkConstants;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.kpme.tklm.leave.workflow.dao.LeaveCalendarDocumentHeaderDao;

public class LeaveCalendarDocumentHeaderServiceImpl implements LeaveCalendarDocumentHeaderService {

    private LeaveCalendarDocumentHeaderDao leaveCalendarDocumentHeaderDao;

    public LeaveCalendarDocumentHeaderDao getLeaveCalendarDocumentHeaderDao() {
        return leaveCalendarDocumentHeaderDao;
    }

    public void setLeaveCalendarDocumentHeaderDao(LeaveCalendarDocumentHeaderDao leaveCalendarDocumentHeaderDao) {
        this.leaveCalendarDocumentHeaderDao = leaveCalendarDocumentHeaderDao;
    }

    @Override
    public LeaveCalendarDocumentHeader getDocumentHeader(String documentId) {
        return leaveCalendarDocumentHeaderDao.getLeaveCalendarDocumentHeader(documentId);
    }

    @Override
    public LeaveCalendarDocumentHeader getDocumentHeader(String principalId, DateTime beginDate, DateTime endDate) {
        return leaveCalendarDocumentHeaderDao.getLeaveCalendarDocumentHeader(principalId,  beginDate,  endDate);
    }

    @Override
    public void saveOrUpdate(LeaveCalendarDocumentHeader leaveCalendarDocumentHeader) {
        leaveCalendarDocumentHeaderDao.saveOrUpdate(leaveCalendarDocumentHeader);
    }

	@Override
	public LeaveCalendarDocumentHeader getPrevOrNextDocumentHeader(
			String prevOrNext, String principalId) {
		    LeaveCalendarDocument currentLeaveCalendar = TKContext.getCurrentLeaveCalendarDocument();
	        LeaveCalendarDocumentHeader lcdh;
	        if (StringUtils.equals(prevOrNext, TkConstants.PREV_TIMESHEET)) {
	        	lcdh = leaveCalendarDocumentHeaderDao.getPreviousDocumentHeader(principalId, currentLeaveCalendar.getDocumentHeader().getBeginDateTime());
	        } else {
	        	lcdh = leaveCalendarDocumentHeaderDao.getNextDocumentHeader(principalId, currentLeaveCalendar.getDocumentHeader().getEndDateTime());
	        }
	        return lcdh;
	}
	
	@Override
    public List<LeaveCalendarDocumentHeader> getDocumentHeaders(DateTime beginDate, DateTime endDate) {
        return leaveCalendarDocumentHeaderDao.getDocumentHeaders(beginDate, endDate);
    }
	
	@Override
	public LeaveCalendarDocumentHeader getMaxEndDateApprovedLeaveCalendar(String principalId) {
		return leaveCalendarDocumentHeaderDao.getMaxEndDateApprovedLeaveCalendar(principalId);
	}

	@Override
	public LeaveCalendarDocumentHeader getMinBeginDatePendingLeaveCalendar(String principalId) {
		return leaveCalendarDocumentHeaderDao.getMinBeginDatePendingLeaveCalendar(principalId);
	}
	
	@Override
	public List<LeaveCalendarDocumentHeader> getAllDocumentHeadersForPricipalId(String principalId){
		return leaveCalendarDocumentHeaderDao.getAllDocumentHeadersForPricipalId(principalId);
	}
	
	@Override
	public List<LeaveCalendarDocumentHeader> getSubmissionDelinquentDocumentHeaders(String principalId, DateTime beforeDate) {
		return leaveCalendarDocumentHeaderDao.getSubmissionDelinquentDocumentHeaders(principalId, beforeDate);
	}
	
	@Override
	public List<LeaveCalendarDocumentHeader> getApprovalDelinquentDocumentHeaders(String principalId){
		return leaveCalendarDocumentHeaderDao.getApprovalDelinquentDocumentHeaders(principalId);
	}

    public void deleteLeaveCalendarHeader(String documentId){
        leaveCalendarDocumentHeaderDao.deleteLeaveCalendarHeader(documentId);
    }
    @Override
    public List<LeaveCalendarDocumentHeader> getAllDocumentHeadersInRangeForPricipalId(String principalId, DateTime beginDate, DateTime endDate) {
    	return leaveCalendarDocumentHeaderDao.getAllDocumentHeadersInRangeForPricipalId(principalId, beginDate, endDate);
    }
}
