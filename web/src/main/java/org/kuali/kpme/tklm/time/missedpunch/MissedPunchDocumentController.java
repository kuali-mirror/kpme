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
package org.kuali.kpme.tklm.time.missedpunch;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.SequenceAccessorService;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.DocumentControllerBase;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/missedPunch")
public class MissedPunchDocumentController extends DocumentControllerBase {
	
	private transient SequenceAccessorService sequenceAccessorService;
	
	@Override
	protected DocumentFormBase createInitialForm(HttpServletRequest request) {
		return new MissedPunchForm();
	}
	
	@Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
    	ModelAndView modelAndView = super.start(form, result, request, response);
    	
    	MissedPunchForm missedPunchForm = (MissedPunchForm) form;
    	MissedPunch missedPunch = missedPunchForm.getMissedPunch();
    	
        TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(missedPunch.getTimesheetDocumentId());
        if (timesheetDocument != null) {
        	missedPunch.setPrincipalId(timesheetDocument.getPrincipalId());
        	missedPunch.setTimesheetDocumentId(missedPunch.getTimesheetDocumentId());
        }
	
        ClockLog lastClock = TkServiceLocator.getClockLogService().getLastClockLog(HrContext.getTargetPrincipalId());
        if (lastClock != null && !StringUtils.equals(lastClock.getClockAction(), TkConstants.CLOCK_OUT)) {
        	MissedPunch lastMissedPunch = TkServiceLocator.getMissedPunchService().getMissedPunchByClockLogId(lastClock.getTkClockLogId());
	        if (lastMissedPunch != null) {
	        	missedPunch.setJobNumber(lastMissedPunch.getJobNumber());
	        	missedPunch.setWorkArea(lastMissedPunch.getWorkArea());
	        	missedPunch.setTask(lastMissedPunch.getTask());
	        } else {
	        	missedPunch.setJobNumber(lastClock.getJobNumber());
	        	missedPunch.setWorkArea(lastClock.getWorkArea());
	        	missedPunch.setTask(lastClock.getTask());
	        }
        }
        
        missedPunchForm.setAssignmentReadOnly(isAssignmentReadOnly(missedPunch));
	    
	    return modelAndView;
	}
	
	@Override
    protected void createDocument(DocumentFormBase form) throws WorkflowException {
        super.createDocument(form);
        
		MissedPunchForm missedPunchForm = (MissedPunchForm) form;
		MissedPunchDocument missedPunchDocument = (MissedPunchDocument) missedPunchForm.getDocument();
		
	    if (StringUtils.isEmpty(missedPunchDocument.getDocumentHeader().getDocumentDescription())) {
	    	missedPunchDocument.getDocumentHeader().setDocumentDescription("Missed Punch: " + missedPunchForm.getMissedPunch().getPrincipalId());
	    }
	    
	    missedPunchDocument.setTkMissedPunchId(missedPunchForm.getMissedPunch().getTkMissedPunchId());
	    missedPunchDocument.setMissedPunch(missedPunchForm.getMissedPunch());
    }
	
    @RequestMapping(params = "methodToCall=submit")
    public ModelAndView submit(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MissedPunchForm missedPunchForm = (MissedPunchForm) form;
		
    	createDocument(missedPunchForm);
    	
    	ModelAndView modelAndView = save(missedPunchForm, result, request, response);

	    if (GlobalVariables.getMessageMap().hasNoErrors()) {
	    	modelAndView = route(missedPunchForm, result, request, response);
	    	missedPunchForm.setMissedPunchSubmitted(true);
	    }
	    
		return modelAndView;
    }
    
	@Override
    @RequestMapping(params = "methodToCall=route")
    public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = super.route(form, result, request, response);
    	
		MissedPunchForm missedPunchForm = (MissedPunchForm) form;
		MissedPunchDocument missedPunchDocument = (MissedPunchDocument) missedPunchForm.getDocument();
		MissedPunch missedPunch = (MissedPunch) missedPunchDocument.getMissedPunch();
		TkServiceLocator.getMissedPunchService().addClockLog(missedPunch, TKUtils.getIPAddressFromRequest(request));
        
    	return modelAndView;
    }

	@Override
    @RequestMapping(params = "methodToCall=approve")
    public ModelAndView approve(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MissedPunchDocument missedPunchDocument = (MissedPunchDocument) form.getDocument();
        MissedPunch missedPunch = (MissedPunch) missedPunchDocument.getMissedPunch();
    	TkServiceLocator.getMissedPunchService().updateClockLog(missedPunch, TKUtils.getIPAddressFromRequest(request));
        
    	return super.approve(form, result, request, response);
    }

	protected boolean isAssignmentReadOnly(MissedPunch missedPunch) {
		boolean isAssignmentReadOnly = false;
		
        if (StringUtils.isNotBlank(missedPunch.getClockAction())) {
        	Set<String> availableActions = TkConstants.CLOCK_AVAILABLE_ACTION_MAP.get(missedPunch.getClockAction());
        	if (availableActions.contains(TkConstants.CLOCK_OUT)) {
    			isAssignmentReadOnly = true;
    		}
        } else {
            ClockLog lastClock = TkServiceLocator.getClockLogService().getLastClockLog(HrContext.getTargetPrincipalId());
            if (lastClock != null) {
            	if (!StringUtils.equals(lastClock.getClockAction(), TkConstants.CLOCK_OUT)) {
            		isAssignmentReadOnly = true;
            	}
            }
        }
        
        return isAssignmentReadOnly;
	}
	
    protected SequenceAccessorService getSequenceAccessorService() {
        if (sequenceAccessorService == null) {
            sequenceAccessorService = KRADServiceLocator.getSequenceAccessorService();
        }
        return sequenceAccessorService;
    }

    public void setSequenceAccessorService(SequenceAccessorService sequenceAccessorService) {
        this.sequenceAccessorService = sequenceAccessorService;
    }

}
