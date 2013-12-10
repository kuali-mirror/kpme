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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.earncode.security.EarnCodeSecurityContract;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetailRendererContract;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;

import java.util.ArrayList;
import java.util.List;

public class TimeHourDetailRenderer implements TimeHourDetailRendererContract {
    private TimeHourDetail timeHourDetail;
    private boolean overtimeEarnCode;

    public TimeHourDetailRenderer(TimeHourDetail d) {
        this.timeHourDetail = d;

        TimeBlock tb = TkServiceLocator.getTimeBlockService().getTimeBlock(timeHourDetail.getTkTimeBlockId());
        if(tb != null) {
            List<? extends EarnCodeContract> overtimeEarnCodeObjs = HrServiceLocator.getEarnCodeService().getOvertimeEarnCodes(LocalDate.now());
            List<String> overtimeEarnCodeStrings = HrServiceLocator.getEarnCodeService().getOvertimeEarnCodesStrs(tb.getBeginDateTime().toLocalDate());
            List<String> eligibleOvertimeEarnCodeListStrings = new ArrayList<String>();

            JobContract job = HrServiceLocator.getJobService().getJob(HrContext.getTargetPrincipalId(), tb.getJobNumber(), tb.getEndDateTime().toLocalDate());
            if(job != null) {
                for (EarnCodeContract earnCode : overtimeEarnCodeObjs) {
                    String employee = HrContext.isActiveEmployee() ? "Y" : null;
                    String approver = HrContext.isApprover() ? "Y" : null;
                    String payrollProcessor = HrContext.isPayrollProcessor() ? "Y" : null;

                    List<? extends EarnCodeSecurityContract> securityList = HrServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurityList(job.getDept(), job.getHrSalGroup(), earnCode.getEarnCode(), employee, approver, payrollProcessor, job.getLocation(),
                            "Y", tb.getEndDateTime().toLocalDate());
                    if(CollectionUtils.isNotEmpty(securityList)) {
                        eligibleOvertimeEarnCodeListStrings.add(earnCode.getEarnCode());
                    }
                }
            }

            /*
            KPME-3029 checks to see if user can make a change to the overtime earncode before flagging it as overtime,
            by either having > 1 earncode opt or having an opt that is different than the timeHourDetail earncode
            */
            if((CollectionUtils.isNotEmpty(eligibleOvertimeEarnCodeListStrings) && CollectionUtils.isNotEmpty(overtimeEarnCodeStrings)) && overtimeEarnCodeStrings.contains(d.getEarnCode()) && (eligibleOvertimeEarnCodeListStrings.size() > 1 || !eligibleOvertimeEarnCodeListStrings.contains(d.getEarnCode()))){
                setOvertimeEarnCode(true);
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
				PrincipalHRAttributesContract principalCalendar = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(docHeader.getPrincipalId(), timeBlock.getBeginDateTime().toLocalDate());
				
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
