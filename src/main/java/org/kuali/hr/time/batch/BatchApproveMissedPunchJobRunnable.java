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
package org.kuali.hr.time.batch;

import java.util.List;

import org.kuali.hr.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class BatchApproveMissedPunchJobRunnable extends BatchJobEntryRunnable {

	public BatchApproveMissedPunchJobRunnable(BatchJobEntry entry) {
        super(entry);
    }

	@Override
	public void doWork() throws Exception {
		String clockLogId = batchJobEntry.getClockLogId();
		if(clockLogId != null) {	// if clock log id is provided, just approve the specified missed punch document
			MissedPunchDocument document = TkServiceLocator.getMissedPunchService().getMissedPunchByClockLogId(clockLogId);
			if(document != null) {
				TkServiceLocator.getMissedPunchService().approveMissedPunch(document);
			}
		} else if( batchJobEntry.getDocumentId() != null) {
			MissedPunchDocument document = TkServiceLocator.getMissedPunchService().getMissedPunchByRouteHeader(batchJobEntry.getDocumentId());
			if(document != null) {
				TkServiceLocator.getMissedPunchService().approveMissedPunch(document);
			}
		} else { // if batch job does not have clock log id, approve all enrouted missed punch docs for the given pay period
			List<MissedPunchDocument> docList = TkServiceLocator.getMissedPunchService().getMissedPunchDocsByBatchJobEntry(batchJobEntry);
			for(MissedPunchDocument aDoc : docList) {
				TkServiceLocator.getMissedPunchService().approveMissedPunch(aDoc);
			}
		}
		
	}
}
