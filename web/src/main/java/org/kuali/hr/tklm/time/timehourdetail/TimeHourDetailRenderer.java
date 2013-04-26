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
package org.kuali.hr.tklm.time.timehourdetail;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.bo.principal.PrincipalHRAttributes;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.common.TKContext;
import org.kuali.hr.tklm.common.TkConstants;
import org.kuali.hr.tklm.leave.service.LmServiceLocator;
import org.kuali.hr.tklm.time.service.TkServiceLocator;
import org.kuali.hr.tklm.time.timeblock.TimeBlock;
import org.kuali.hr.tklm.time.timeblock.TimeHourDetail;
import org.kuali.hr.tklm.time.workflow.TimesheetDocumentHeader;

public class TimeHourDetailRenderer {
    private TimeHourDetail timeHourDetail;
    private boolean overtimeEarnCode;

    public TimeHourDetailRenderer(TimeHourDetail d) {
        this.timeHourDetail = d;
        List<String> ovtEarnCodes = HrServiceLocator.getEarnCodeService().getOvertimeEarnCodesStrs(TKContext.getCurrentTimesheetDocument().getAsOfDate());
        setOvertimeEarnCode(ovtEarnCodes.contains(d.getEarnCode()));
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
			if(timeBlock.getEarnCode().equals(TkConstants.HOLIDAY_EARN_CODE)) {
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
