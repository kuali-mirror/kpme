package org.kuali.hr.time.calendar;

import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.hr.core.cache.CacheUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;

/**
 * Leverage this class to implement hooks when maintenance is done on a
 * Calendar.
 * 
 * @author djunk
 * 
 */
public class CalendarMaintainableImpl extends KualiMaintainableImpl {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(CalendarMaintainableImpl.class);

	@Override
	public void addNewLineToCollection(String collectionName) {
		super.addNewLineToCollection(collectionName);
	}

	@Override
	public Map<String, String> populateNewCollectionLines(Map<String, String> fieldValues, MaintenanceDocument maintenanceDocument, String methodToCall) {
		return super.populateNewCollectionLines(fieldValues, maintenanceDocument, methodToCall);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processAfterAddLine(String colName, Class colClass) {
		super.processAfterAddLine(colName, colClass);
	}

	@Override
	public void saveBusinessObject() {
		super.saveBusinessObject();
		Calendar calendar = (Calendar) this.getBusinessObject();
		LOG.info("Saved pay calendar: " + calendar.getHrCalendarId());
        CacheUtils.flushCache(Calendar.CACHE_NAME);
        CacheUtils.flushCache(CalendarEntries.CACHE_NAME);
	}

}
