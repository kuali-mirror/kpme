package org.kuali.hr.time.workflow.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.hr.time.workflow.dao.TimesheetDocumentHeaderDao;

public class TimesheetDocumentHeaderServiceImpl implements TimesheetDocumentHeaderService {

    private TimesheetDocumentHeaderDao documentHeaderDao;

    public void setTimesheetDocumentHeaderDao(TimesheetDocumentHeaderDao documentHeaderDao) {
        this.documentHeaderDao = documentHeaderDao;
    }

    @Override
    public TimesheetDocumentHeader getDocumentHeader(String documentId) {
        return documentHeaderDao.getTimesheetDocumentHeader(documentId);
    }

    @Override
    public void saveOrUpdate(TimesheetDocumentHeader documentHeader) {
        documentHeaderDao.saveOrUpdate(documentHeader);
    }

    @Override
    public TimesheetDocumentHeader getDocumentHeader(String principalId, Date payBeginDate, Date payEndDate) {
        return documentHeaderDao.getTimesheetDocumentHeader(principalId, payBeginDate, payEndDate);
    }

    // this is used by the overtime rules
    public TimesheetDocumentHeader getPreviousDocumentHeader(String principalId, Date payBeginDate) {
        return documentHeaderDao.getPreviousDocumentHeader(principalId, payBeginDate);
    }

    @Override
    public TimesheetDocumentHeader getPrevOrNextDocumentHeader(String prevOrNext, String principalId) {
        TimesheetDocument currentTimesheet = TKContext.getCurrentTimesheetDoucment();
        TimesheetDocumentHeader tsdh;
        if (StringUtils.equals(prevOrNext, TkConstants.PREV_TIMESHEET)) {
            tsdh = documentHeaderDao.getPreviousDocumentHeader(principalId, currentTimesheet.getDocumentHeader().getPayBeginDate());
        } else {
            tsdh = documentHeaderDao.getNextDocumentHeader(principalId, currentTimesheet.getDocumentHeader().getPayEndDate());
        }
        return tsdh;
    }

    public List<TimesheetDocumentHeader> getDocumentHeaders(Date payBeginDate) {
        return documentHeaderDao.getDocumentHeaders(payBeginDate);
    }

	@Override
	public void deleteTimesheetHeader(String documentId) {
		documentHeaderDao.deleteTimesheetHeader(documentId);

	}
}
