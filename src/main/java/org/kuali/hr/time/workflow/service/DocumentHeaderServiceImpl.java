package org.kuali.hr.time.workflow.service;

import java.util.Date;

import org.kuali.hr.time.workflow.TkDocumentHeader;
import org.kuali.hr.time.workflow.dao.DocumentHeaderDao;

public class DocumentHeaderServiceImpl implements DocumentHeaderService {

	private DocumentHeaderDao documentHeaderDao;

	public void setDocumentHeaderDao(DocumentHeaderDao documentHeaderDao) {
		this.documentHeaderDao = documentHeaderDao;
	}

	@Override
	public TkDocumentHeader getDocumentHeader(Long documentId) {
		return documentHeaderDao.getDocumentHeader(documentId);
	}

	@Override
	public void saveOrUpdate(TkDocumentHeader documentHeader) {
		documentHeaderDao.saveOrUpdate(documentHeader);
	}

	@Override
	public TkDocumentHeader getDocumentHeader(String principalId, Date payEndDate) {
		return documentHeaderDao.getDocumentHeader(principalId, payEndDate);
	}
}
