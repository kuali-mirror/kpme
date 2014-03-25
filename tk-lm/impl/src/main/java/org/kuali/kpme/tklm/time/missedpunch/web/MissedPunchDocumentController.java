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
package org.kuali.kpme.tklm.time.missedpunch.web;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.api.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunch;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunchDocument;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.bo.Note;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.exception.ValidationException;
import org.kuali.rice.krad.rules.rule.event.SaveDocumentEvent;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.SequenceAccessorService;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.widget.Disclosure;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.TransactionalDocumentControllerBase;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/missedPunch")
public class MissedPunchDocumentController extends TransactionalDocumentControllerBase {
	
	private transient SequenceAccessorService sequenceAccessorService;
	
	@Override
	protected DocumentFormBase createInitialForm(HttpServletRequest request) {
		return new MissedPunchForm();
	}
	
	@Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
    	
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

        return super.start(missedPunchForm, result, request, response);
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
        ModelAndView modelAndView = null;
        try {
            missedPunchForm.getDocument().validateBusinessRules(new SaveDocumentEvent( missedPunchForm.getDocument()));

            if (GlobalVariables.getMessageMap().hasNoErrors()) {
                modelAndView = save(missedPunchForm, result, request, response);

                if (StringUtils.isNotBlank(missedPunchForm.getMissedPunch().getNote())) {
                    Document doc = missedPunchForm.getDocument();
                    Note note = new Note();
                    note.setNoteText(missedPunchForm.getMissedPunch().getNote());
                    note.setAuthorUniversalIdentifier(HrContext.getPrincipalId());
                    note.setNotePostedTimestampToCurrent();
                    doc.setNotes(Collections.<Note>singletonList(note));
                }
	    	modelAndView = route(missedPunchForm, result, request, response);
	    	missedPunchForm.setMissedPunchSubmitted(true);

		return modelAndView;
    }
    

        } catch (ValidationException exception) {
            //ignore
        }
        return getUIFModelAndView(form);
    }

	@Override
    @RequestMapping(params = "methodToCall=route")
    public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

		MissedPunchForm missedPunchForm = (MissedPunchForm) form;
		MissedPunchDocument missedPunchDocument = (MissedPunchDocument) missedPunchForm.getDocument();
		MissedPunch missedPunch = (MissedPunch) missedPunchDocument.getMissedPunch();
		TkServiceLocator.getMissedPunchService().addClockLog(missedPunch, TKUtils.getIPAddressFromRequest(request));
		
		missedPunchDocument.setMissedPunch(missedPunch);
		missedPunchForm.setDocument(missedPunchDocument);
   	
    	return super.route(missedPunchForm, result, request, response);
    }

	@Override
    @RequestMapping(params = "methodToCall=approve")
    public ModelAndView approve(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MissedPunchDocument missedPunchDocument = (MissedPunchDocument) form.getDocument();
        MissedPunch missedPunch = missedPunchDocument.getMissedPunch();
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

    @Override
    @RequestMapping(params = "methodToCall=docHandler")
    public ModelAndView docHandler(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = super.docHandler(form, result, request, response);

        //return getUIFModelAndView(form);
        List<? extends Component> pageComponents = ((MissedPunchForm)form).getView().getItems().get(0).getItems();
        for (Component c : pageComponents)  {
            if (c instanceof CollectionGroup) {
                CollectionGroup collGroup = (CollectionGroup)c;
                if (CollectionUtils.isNotEmpty(collGroup.getItems())) {
                    Disclosure disclosure = collGroup.getDisclosure();
                    disclosure.setDefaultOpen(true);
                }
            }
        }
        return mav;
    }

    @Override
    @RequestMapping(params = "methodToCall=back")
    public ModelAndView back(@ModelAttribute("KualiForm") UifFormBase form,
                             BindingResult result, HttpServletRequest request,
                             HttpServletResponse response) {
        if (!StringUtils.contains(form.getReturnLocation(), "dataObjectClassName="+MissedPunch.class.getName())) {
            form.setReturnLocation(UifConstants.NO_RETURN);
        }
        return super.back(form, result, request, response);
    }

}
