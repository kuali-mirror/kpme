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
package org.kuali.kpme.tklm.time.timehourdetail;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.bo.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;

public class TimeHourDetailRenderer {
    private TimeHourDetail timeHourDetail;
    private boolean overtimeEarnCode;

    public TimeHourDetailRenderer(TimeHourDetail d) {
        this.timeHourDetail = d;

        TimeBlock timeBlock = TkServiceLocator.getTimeBlockService().getTimeBlock(timeHourDetail.getTkTimeBlockId());
        if (timeBlock != null) {
	        List<String> ovtEarnCodes = HrServiceLocator.getEarnCodeService().getOvertimeEarnCodesStrs(timeBlock.getBeginDateTime().toLocalDate());
	        if(ovtEarnCodes != null && !ovtEarnCodes.isEmpty()){
	        	setOvertimeEarnCode(ovtEarnCodes.contains(d.getEarnCode()));
	        }
        }
    }

    public TimeHourDetail getTimeHourDetail() {
        return timeHourDetail;
    }

    public String getTkTimeHourDetailId() {
        return timeHourDetail.getTkTimeHourDetailId();
    }

    public String getTitle() {
        return timeHourDetail.getEarnCode();
    }

    public String getHours() {
        return timeHourDetail.getHours().toString();
    }

    public String getAmount() {
        return timeHourDetail.getAmount().toString();
    }
    
    public String getHolidayName() {
		String holidayDesc = "";
		TimeBlock timeBlock = TkServiceLocator.getTimeBlockService().getTimeBlock(timeHourDetail.getTkTimeBlockId());
		
		if ( timeBlock != null ){
			if(timeBlock.getEarnCode().equals(HrConstants.HOLIDAY_EARN_CODE)) {
				String documentId = timeBlock.getDocumentId();
				TimesheetDocumentHeader docHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
				PrincipalHRAttributes principalCalendar = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(docHeader.getPrincipalId(), timeBlock.getBeginDateTime().toLocalDate());
				
				if(principalCalendar != null && StringUtils.isNotEmpty(principalCalendar.getLeavePlan())) {
					holidayDesc = LmServiceLocator.getSysSchTimeOffService().getSSTODescriptionForDate(principalCalendar.getLeavePlan(), timeBlock.getBeginDateTime().toLocalDate());
				}
			}
		}
			
		return holidayDesc;
	}

	public boolean isOvertimeEarnCode() {
		return overtimeEarnCode;
	}

	public void setOvertimeEarnCode(boolean overtimeEarnCode) {
		this.overtimeEarnCode = overtimeEarnCode;
	}

}
