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
package org.kuali.kpme.tklm.leave.block.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.bcel.generic.NEW;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ObjectUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlockBo;
import org.kuali.kpme.tklm.leave.calendar.exportCalendar.CalendarEvent;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/leaveBlock")
public class LeaveBlockController extends UifControllerBase {
	
	int eventCount=0;
	String fileName;
	StringBuffer sb=new StringBuffer();
	
	@Override
	protected UifFormBase createInitialForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return new LeaveBlockForm();
	}

	@Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request, HttpServletResponse response) {
    	LeaveBlockForm leaveBlockForm = (LeaveBlockForm) form;
        return super.start(leaveBlockForm, request, response);
	}
	
	@RequestMapping(params = "methodToCall=download")
	public ModelAndView download(@ModelAttribute("KualiForm") UifFormBase form,BindingResult result, HttpServletRequest request,HttpServletResponse response) {
		if (sb != null) {
			try {
				response.setContentType("text/calendar");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + fileName);
				response.getWriter().write(sb.toString());
				response.setContentLength(sb.toString().getBytes().length);
				response.getWriter().flush();
				response.getWriter().close();
				sb.setLength(0);
			} catch (Exception e) {
				// IO EXCEPTION
				e.printStackTrace();
			}
		}
		return getUIFModelAndView(form);
	}
	
	@RequestMapping(params = "methodToCall=submit")
    public ModelAndView submit(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
		eventCount=0;
		sb.setLength(0);
		LeaveBlockForm leaveBlockForm = (LeaveBlockForm)form;
		if(leaveBlockForm.getFromDate()!=null && leaveBlockForm.getToDate()!=null && !leaveBlockForm.getFromDate().equals("") && !leaveBlockForm.getToDate().equals("")){
			DateTime beginDate = new DateTime(leaveBlockForm.getFromDate());
			DateTime endDate = new DateTime(leaveBlockForm.getToDate());
			if (beginDate != null && endDate != null) {
				exportApprovedLeaves(beginDate.toLocalDate(), endDate.toLocalDate());
				leaveBlockForm.setCalendarCount(String.valueOf(eventCount));
			}
		}
		return getUIFModelAndView(leaveBlockForm);
	}
	
	public void exportApprovedLeaves(LocalDate beginDate,LocalDate endDate){
		String principalId = HrContext.getTargetPrincipalId();
		List<LeaveBlock> approvedLeaves = getLeaveBlocksWithRequestStatus(principalId, beginDate, endDate, HrConstants.REQUEST_STATUS.APPROVED);
		CalendarEvent mycal = new CalendarEvent();
		fileName = mycal.generateFilename();

		sb.append(mycal.calendarHeader());
    	if(approvedLeaves!=null && !approvedLeaves.isEmpty()){
        	for (LeaveBlock leaveBlock : approvedLeaves) {
        		String desc = leaveBlock.getDescription();
        		if(desc == null){
        			desc = "";
        		}
        		String uid = "" + leaveBlock.getBlockId() + leaveBlock.getObjectId();
        		String event = mycal.createEvent(leaveBlock.getAssignmentTitle(),leaveBlock.getLeaveDateTime(),leaveBlock.getLeaveDateTime(),"000000","000000",leaveBlock.getEarnCode() + "(" + leaveBlock.getLeaveAmount() + ")\n" + desc, uid);
        		sb.append(event);
        		eventCount++;
			}		
    	}
    	sb.append(mycal.calendarFooter());
    }

	private List<LeaveBlock> getLeaveBlocksWithRequestStatus(String principalId, LocalDate beginDate, LocalDate endDate, String requestStatus) {
		List<LeaveBlock> plannedLeaves = LmServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR, requestStatus, beginDate, endDate);
		
        Collections.sort(plannedLeaves, new Comparator<LeaveBlock>() {
            @Override
            public int compare(LeaveBlock leaveBlock1, LeaveBlock leaveBlock2) {
                return ObjectUtils.compare(leaveBlock1.getLeaveDateTime(), leaveBlock2.getLeaveDateTime());
            }
        });
        if(plannedLeaves!=null && !plannedLeaves.isEmpty()){
        	return plannedLeaves;
        }else{
        	return null;
        }
	}
	
	@Override
    @RequestMapping(params = "methodToCall=back")
    public ModelAndView back(@ModelAttribute("KualiForm") UifFormBase form,BindingResult result, HttpServletRequest request,HttpServletResponse response) {
        return super.back(form, result, request, response);
    }
	
}
