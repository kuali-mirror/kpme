/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.workflow.dao;

import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

import java.util.Date;
import java.util.List;

public interface TimesheetDocumentHeaderDao {

	public void saveOrUpdate(TimesheetDocumentHeader documentHeader);

	public TimesheetDocumentHeader getTimesheetDocumentHeader(String documentId);

	public TimesheetDocumentHeader getTimesheetDocumentHeader(String principalId, Date PayBeginDate, Date payEndDate);

	public TimesheetDocumentHeader getPreviousDocumentHeader(String principalId, Date payBegindate);

    public TimesheetDocumentHeader getNextDocumentHeader(String principalId, Date payEndDate);

    public List<TimesheetDocumentHeader> getDocumentHeaders(Date payBeginDate);
    
    public void deleteTimesheetHeader(String documentId);
    
    public List<TimesheetDocumentHeader> getDocumentHeadersForPrincipalId(String principalId);
    
    public List<TimesheetDocumentHeader> getDocumentHeadersForYear(String principalId, String year);
    
    public TimesheetDocumentHeader getDocumentHeaderForDate(String principalId, Date asOfDate);
}
