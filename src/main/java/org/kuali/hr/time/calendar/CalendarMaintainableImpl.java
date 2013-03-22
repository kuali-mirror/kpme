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
        CacheUtils.flushCache(CalendarEntry.CACHE_NAME);
	}

}
