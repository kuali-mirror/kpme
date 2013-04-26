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
package org.kuali.hr.tklm.time.warning;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.time.service.TkServiceLocator;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;

public class TkWarningServiceImpl implements TkWarningService {
    
    @Override
    public List<String> getWarnings(TimesheetDocument td) {
    	List<String> warnings = new ArrayList<String>();
    	
       // warnings.addAll(TkServiceLocator.getTimeOffAccrualService().validateAccrualHoursLimit(td));
        
        warnings.addAll(HrServiceLocator.getEarnCodeGroupService().warningTextFromEarnCodeGroupsOfDocument(td));
        
        if (td != null && CollectionUtils.isNotEmpty(td.getTimeBlocks())) {
        	warnings.addAll(TkServiceLocator.getClockLogService().getUnapprovedIPWarning(td.getTimeBlocks()));
        }
        
        return warnings;
    }

}
