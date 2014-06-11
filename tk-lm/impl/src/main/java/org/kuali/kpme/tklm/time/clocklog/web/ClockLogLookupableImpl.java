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
package org.kuali.kpme.tklm.time.clocklog.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.tklm.time.clocklog.ClockLogBo;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.krad.uif.view.LookupView;
import org.kuali.rice.krad.web.form.LookupForm;

public class ClockLogLookupableImpl extends KPMELookupableImpl {

	@Override
	public void initSuppressAction(LookupForm lookupForm) {
		((LookupView) lookupForm.getView()).setSuppressActions(false);
	}

	@Override
	public List<?> getSearchResults(LookupForm form, Map<String, String> searchCriteria, boolean unbounded) {
		List<ClockLogBo> results = new ArrayList<ClockLogBo>();
		List<?> searchResults = super.getSearchResults(form, searchCriteria, unbounded);
		for (Object searchResult : searchResults) {
			if(searchResult != null) {
				ClockLogBo aClockLog = (ClockLogBo) searchResult;
				aClockLog.setClockedByMissedPunch(TkServiceLocator.getClockLogService().isClockLogCreatedByMissedPunch(aClockLog.getTkClockLogId()));
				results.add(aClockLog);
			}
		}

		return results;
	}

}