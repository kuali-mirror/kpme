package org.kuali.hr.time.workflow.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.hr.time.workflow.dao.TimesheetDocumentHeaderDao;

import java.util.Date;
import java.util.List;

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
    public TimesheetDocumentHeader getPrevOrNextDocumentHeader(String prevOrNext, String principalId, String documentId) {
        TimesheetDocument currentTimesheet = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
        TimesheetDocumentHeader tsdh;
        if (StringUtils.equals(prevOrNext, TkConstants.PREV_TIMESHEET)) {
            tsdh = documentHeaderDao.getPreviousDocumentHeader(principalId, currentTimesheet.getDocumentHeader().getPayBeginDate());
        } else {
            tsdh = documentHeaderDao.getNextDocumentHeader(principalId, currentTimesheet.getDocumentHeader().getPayEndDate());
        }
        // TODO: need to figure out how to handle the situation when there is no previous/next timesheet document header
        if (tsdh == null) {
            throw new RuntimeException("There is no " + prevOrNext + " timesheet");
        }

        return tsdh;
    }

    public List<TimesheetDocumentHeader> getDocumentHeaders(Date payBeginDate) {
        return documentHeaderDao.getDocumentHeaders(payBeginDate);
    }

    @Override
    public List<TimesheetDocumentHeader> getDocumentHeadersByField(String field, String value) {
        field = StringUtils.isNotBlank(field) ? field : "documentId";
        return documentHeaderDao.getDocumentHeadersByField(field, value);
    }

    @Override
    public List<String> getValueByField(String field, String value) {
        return documentHeaderDao.getValueByField(field, value);
    }

    @Override
    public List<TimesheetDocumentHeader> getSortedDocumentHeaders(String orderBy, String orderDirection, String rows) {
        orderBy = StringUtils.isNotBlank(orderBy) ? orderBy : "documentId";
        orderDirection = StringUtils.isNotBlank(orderDirection) ? orderDirection : "asc";
        rows = StringUtils.isNotBlank(rows) ? rows : "5";

        return documentHeaderDao.getSortedDocumentHeaders(orderBy, orderDirection, Integer.parseInt(rows));
    }


}
