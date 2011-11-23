package org.kuali.hr.lm.workflow.service;

import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.lm.workflow.dao.LeaveCalendarDocumentHeaderDao;

import java.util.Date;

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
}
