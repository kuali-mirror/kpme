package org.kuali.kpme.tklm.time.batch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.web.form.LookupForm;
import org.quartz.SchedulerException;

public class BatchJobLookupableImpl extends LookupableImpl {

	@Override
	protected List<?> getSearchResults(LookupForm form,
			Map<String, String> searchCriteria, boolean unbounded) {
		// TODO Auto-generated method stub
		Date fromDate = null;
		Date toDate = null;
		String fromDateString = searchCriteria.get("startDate");
		String toDateString = searchCriteria.get("endDate");
		String jobName = searchCriteria.get("name");
		String jobStatus = searchCriteria.get("status");
		String hrCalendarEntryId = searchCriteria.get("hrCalendarEntryId");
		try {
			if (ObjectUtils.isNotNull(fromDateString) && StringUtils.isNotEmpty(fromDateString.trim())) {
	            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	            dateFormat.setLenient(false);
	            fromDate = dateFormat.parse(fromDateString);
	        }
			if (ObjectUtils.isNotNull(toDateString) && StringUtils.isNotEmpty(toDateString.trim())) {
	            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	            dateFormat.setLenient(false);
	            toDate = dateFormat.parse(toDateString);
	        }
			return TkServiceLocator.getBatchJobService().getJobs(jobName, jobStatus, hrCalendarEntryId, fromDate, toDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
}
