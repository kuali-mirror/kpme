/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.batch;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.quartz.SchedulerException;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BatchJobLookupableImpl extends LookupableImpl {

    @Override
    protected Collection<?> executeSearch(Map<String, String> searchCriteria, List<String> wildcardAsLiteralSearchCriteria, boolean bounded, Integer searchResultsLimit) {
		Date fromDate = null;
		Date toDate = null;
		String fromDateString = searchCriteria.get("startDate");
		String toDateString = searchCriteria.get("endDate");
		String jobName = searchCriteria.get("name");
		String jobStatus = searchCriteria.get("status");
		String hrCalendarEntryId = searchCriteria.get("hrCalendarEntryId");
        if (StringUtils.isNotEmpty(fromDateString)) {
            DateTime fromDateTime =  HrConstants.DateTimeFormats.BASIC_DATE_FORMAT.parseDateTime(fromDateString);
            fromDate = fromDateTime.toDate();
        }
        if (StringUtils.isNotEmpty(toDateString)) {
            DateTime toDateTime =  HrConstants.DateTimeFormats.BASIC_DATE_FORMAT.parseDateTime(toDateString);
            toDate = toDateTime.toDate();
        }
        try {
            return TkServiceLocator.getBatchJobService().getJobs(jobName, jobStatus, hrCalendarEntryId, fromDate, toDate);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
	}

	
}
