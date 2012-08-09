package org.kuali.hr.lm.workflow.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.lm.workflow.dao.LeaveCalendarDocumentHeaderDao;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

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
    public LeaveCalendarDocumentHeader getDocumentHeader(String principalId, Date beginDate, Date endDate) {
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
	        	lcdh = leaveCalendarDocumentHeaderDao.getPreviousDocumentHeader(principalId, currentLeaveCalendar.getLeaveCalendarDocumentHeader().getBeginDate());
	        } else {
	        	lcdh = leaveCalendarDocumentHeaderDao.getNextDocumentHeader(principalId, currentLeaveCalendar.getLeaveCalendarDocumentHeader().getEndDate());
	        }
	        return lcdh;
	}
	
	@Override
	public LeaveCalendarDocumentHeader getMaxEndDateApprovedLeaveCalendar(String principalId) {
		return leaveCalendarDocumentHeaderDao.getMaxEndDateApprovedLeaveCalendar(principalId);
	}

	@Override
	public LeaveCalendarDocumentHeader getMinBeginDatePendingLeaveCalendar(String principalId) {
		return leaveCalendarDocumentHeaderDao.getMinBeginDatePendingLeaveCalendar(principalId);
	}
}
