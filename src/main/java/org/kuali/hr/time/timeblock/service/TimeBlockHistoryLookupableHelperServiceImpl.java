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
package org.kuali.hr.time.timeblock.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.rice.krad.bo.BusinessObject;

public class TimeBlockHistoryLookupableHelperServiceImpl extends TimeBlockLookupableHelperServiceImpl {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
	@Override
    public List<? extends BusinessObject> getSearchResults(java.util.Map<String, String> fieldValues) {

        String docStatus = "", beginDateString = "";

        if (fieldValues.containsKey(DOC_STATUS_ID)) {
            docStatus = fieldValues.get(DOC_STATUS_ID);
            fieldValues.remove(DOC_STATUS_ID);
        }
        if (fieldValues.containsKey(BEGIN_DATE_ID)) {
            beginDateString = fieldValues.get(BEGIN_DATE_ID);
            fieldValues.remove(BEGIN_DATE_ID);
        }
        List<TimeBlockHistory> objectList = (List<TimeBlockHistory>) super.getSearchResults(fieldValues);

        if (!objectList.isEmpty()) {
            Iterator<TimeBlockHistory> itr = objectList.iterator();
            while (itr.hasNext()) {
                TimeBlockHistory tb = itr.next();

                if (StringUtils.isNotEmpty(docStatus)) {
                    if (tb.getTimesheetDocumentHeader() == null) {
                        itr.remove();
                        continue;
                    } else {
                        if (tb.getTimesheetDocumentHeader().getDocumentStatus() != null) {
                            if (!tb.getTimesheetDocumentHeader().getDocumentStatus().equals(docStatus)) {
                                itr.remove();
                                continue;
                            }
                        } else {
                            itr.remove();
                            continue;
                        }
                    }
                }
                if(StringUtils.isNotEmpty(beginDateString)) {
					if(tb.getBeginDate() != null) {
						if(!this.checkDate(tb, tb.getBeginDate(), beginDateString)) {
							itr.remove();
							continue;
						} 
					} else {
						itr.remove();
						continue;
					}
				}
            }
        }

        sortByTimeBlockId(objectList);

        return objectList;
    }

    private void sortByTimeBlockId(List<TimeBlockHistory> objectList) {
        Collections.sort(objectList, new Comparator<TimeBlockHistory>() { // Sort the Time Blocks
            @Override
            public int compare(TimeBlockHistory timeBlockHistory, TimeBlockHistory timeBlockHistory1) {
                return timeBlockHistory.getTkTimeBlockId().compareTo(timeBlockHistory1.getTkTimeBlockId());
            }
        });
    }
}
