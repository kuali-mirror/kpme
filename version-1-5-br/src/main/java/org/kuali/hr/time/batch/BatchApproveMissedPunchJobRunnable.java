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
