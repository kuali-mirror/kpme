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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;

public class PayPeriodEndBatchJob extends BatchJob {
    private Logger LOG = Logger.getLogger(PayPeriodEndBatchJob.class);
    private CalendarEntries calendarEntry;

    public PayPeriodEndBatchJob(CalendarEntries calendarEntry) {
        this.setBatchJobName(TkConstants.BATCH_JOB_NAMES.PAY_PERIOD_END);
        this.setHrPyCalendarEntryId(calendarEntry.getHrCalendarEntriesId());
        this.calendarEntry = calendarEntry;
    }

    @Override
    public void doWork() {
    	List<ClockLog> lstOpenClockLogs = TkServiceLocator.getClockLogService().getOpenClockLogs(calendarEntry);
    	for(ClockLog cl : lstOpenClockLogs){
    		populateBatchJobEntry(cl);
    	}
    	
    }


    @Override
    protected void populateBatchJobEntry(Object o) {
    	ClockLog cl = (ClockLog)o;
        String ip = this.getNextIpAddressInCluster();
        if(StringUtils.isNotBlank(ip)){
            //insert a batch job entry here
        	//TODO finish this
//            BatchJobEntry entry = this.createBatchJobEntry(this.getBatchJobName(), ip, cl.getPrincipalId(), null);
//            TkServiceLocator.getBatchJobEntryService().saveBatchJobEntry(entry);
        } else {
            LOG.info("No ip found in cluster to assign batch jobs");
        }
    }

}
