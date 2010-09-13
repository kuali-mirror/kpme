package org.kuali.hr.time.workflow.service;

import java.util.Date;

import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.hr.time.workflow.dao.TimesheetDocumentHeaderDao;

public class TimesheetDocumentHeaderServiceImpl implements TimesheetDocumentHeaderService {

	private TimesheetDocumentHeaderDao documentHeaderDao;

	public void setTimesheetDocumentHeaderDao(TimesheetDocumentHeaderDao documentHeaderDao) {
		this.documentHeaderDao = documentHeaderDao;
	}

	@Override
	public TimesheetDocumentHeader getDocumentHeader(Long documentId) {
		return documentHeaderDao.getTimesheetDocumentHeader(documentId);
	}

	@Override
	public void saveOrUpdate(TimesheetDocumentHeader documentHeader) {
		documentHeaderDao.saveOrUpdate(documentHeader);
	}

	@Override
	public TimesheetDocumentHeader getDocumentHeader(String principalId, Date payEndDate) {
		return documentHeaderDao.getTimesheetDocumentHeader(principalId, payEndDate);
	}
}
