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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ObjectUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.calendar.exportCalendar.CalendarEvent;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
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

	String fileName;
	@Override
	protected UifFormBase createInitialForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return new LeaveBlockForm();
	}

	@Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
    	LeaveBlockForm leaveBlockForm = (LeaveBlockForm) form;
        ModelAndView modelAndView = super.start(leaveBlockForm, result, request, response);
        return modelAndView;
	}
	
	@RequestMapping(params = "methodToCall=submit")
    public ModelAndView submit(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		if (fromDate != null && !fromDate.equals("") && toDate != null && !toDate.equals("")) {
			DateTime beginDate = TKUtils.formatDateTimeStringNoTimezone(fromDate);
			DateTime endDate = TKUtils.formatDateTimeStringNoTimezone(toDate);
			if (beginDate != null && endDate != null) {
				StringBuffer sb = exportApprovedLeaves(beginDate.toLocalDate(), endDate.toLocalDate());
				if (sb != null) {
					try {
						File file = new File(fileName);
						FileUtils.writeStringToFile(file, sb.toString());
						InputStream is = new FileInputStream(file);
						long length = file.length();

						byte[] calendar = new byte[(int) length];
			        	int offset = 0;
			        	int numRead = 0;
			        	while (offset < calendar.length && (numRead = is.read(calendar, offset, calendar.length - offset)) >= 0) {
			            	offset += numRead;
			        	}
			        	if (offset < calendar.length) {
			        		throw new IOException("Could not completely read file " + fileName);
			        	}
			        	is.close();
			        	response.setContentType("text/calendar");
		            	response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		            	response.getOutputStream().write(calendar); 
		            	response.setContentLength(calendar.length);
		            	response.getOutputStream().flush();
		            	response.getOutputStream().close();
		            	if(file.exists()){
		            		file.delete();
		            	}
					}catch(Exception e){
						//IO EXCEPTION
						e.printStackTrace();
					}
				}
			}
		}
		return null;
    }
	
	public StringBuffer exportApprovedLeaves(LocalDate beginDate,LocalDate endDate){
		String principalId = HrContext.getTargetPrincipalId();
		List<LeaveBlock> approvedLeaves = getLeaveBlocksWithRequestStatus(principalId, beginDate, endDate, HrConstants.REQUEST_STATUS.APPROVED);
		CalendarEvent mycal = new CalendarEvent();
		fileName = mycal.generateFilename();
		StringBuffer writer = new StringBuffer();
		writer.append(mycal.calendarHeader());
    	if(approvedLeaves!=null && !approvedLeaves.isEmpty()){
        	for (LeaveBlock leaveBlock : approvedLeaves) {
        		String desc = leaveBlock.getDescription();
        		if(desc == null){
        			desc = "";
        		}
        		String uid = "" + leaveBlock.getBlockId() + leaveBlock.getObjectId();
        		String event = mycal.createEvent(leaveBlock.getAssignmentTitle(),leaveBlock.getLeaveDate(),leaveBlock.getLeaveDate(),"000000","000000",leaveBlock.getEarnCode() + "(" + leaveBlock.getLeaveAmount() + ")\n" + desc, uid);
        		writer.append(event);
			}		
    	}
    	writer.append(mycal.calendarFooter());
		return writer;
    }

	private List<LeaveBlock> getLeaveBlocksWithRequestStatus(String principalId, LocalDate beginDate, LocalDate endDate, String requestStatus) {
		List<LeaveBlock> plannedLeaves = LmServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR, requestStatus, beginDate, endDate);
		
        Collections.sort(plannedLeaves, new Comparator<LeaveBlock>() {
            @Override
            public int compare(LeaveBlock leaveBlock1, LeaveBlock leaveBlock2) {
                return ObjectUtils.compare(leaveBlock1.getLeaveDate(), leaveBlock2.getLeaveDate());
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
