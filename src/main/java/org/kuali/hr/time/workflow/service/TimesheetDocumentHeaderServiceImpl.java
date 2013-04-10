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
package org.kuali.hr.time.workflow.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
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
    public TimesheetDocumentHeader getDocumentHeader(String principalId, DateTime payBeginDate, DateTime payEndDate) {
        return documentHeaderDao.getTimesheetDocumentHeader(principalId, payBeginDate, payEndDate);
    }

    public TimesheetDocumentHeader getPreviousDocumentHeader(String principalId, DateTime payBeginDate) {
        return documentHeaderDao.getPreviousDocumentHeader(principalId, payBeginDate);
    }
    
    public TimesheetDocumentHeader getNextDocumentHeader(String principalId, DateTime payEndDate) {
        return documentHeaderDao.getNextDocumentHeader(principalId, payEndDate);
    }

    @Override
    public TimesheetDocumentHeader getPrevOrNextDocumentHeader(String prevOrNext, String principalId) {
        TimesheetDocument currentTimesheet = TKContext.getCurrentTimesheetDocument();
        TimesheetDocumentHeader tsdh;
        if (StringUtils.equals(prevOrNext, TkConstants.PREV_TIMESHEET)) {
            tsdh = documentHeaderDao.getPreviousDocumentHeader(principalId, currentTimesheet.getDocumentHeader().getBeginDateTime());
        } else {
            tsdh = documentHeaderDao.getNextDocumentHeader(principalId, currentTimesheet.getDocumentHeader().getEndDateTime());
        }
        return tsdh;
    }
    
    @Override
    public List<TimesheetDocumentHeader> getDocumentHeaders(DateTime payBeginDate, DateTime payEndDate) {
        return documentHeaderDao.getDocumentHeaders(payBeginDate, payEndDate);
    }

	@Override
	public void deleteTimesheetHeader(String documentId) {
		documentHeaderDao.deleteTimesheetHeader(documentId);

	}
	
	@Override
	public List<TimesheetDocumentHeader> getDocumentHeadersForPrincipalId(String principalId) {
		return documentHeaderDao.getDocumentHeadersForPrincipalId(principalId);
	}
	
	public List<TimesheetDocumentHeader> getDocumentHeadersForYear(String principalId, String year) {
		return documentHeaderDao.getDocumentHeadersForYear(principalId, year);
	}
	
	public TimesheetDocumentHeader getDocumentHeaderForDate(String principalId, DateTime asOfDate) {
		return documentHeaderDao.getDocumentHeaderForDate(principalId, asOfDate);
	}

}
