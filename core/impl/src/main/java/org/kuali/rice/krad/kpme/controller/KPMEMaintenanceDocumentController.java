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
package org.kuali.rice.krad.kpme.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Properties;


/**
 * This abstract class is the root of the KPME maintenance document controller hierarchy.
 * 
 */
public abstract class KPMEMaintenanceDocumentController extends MaintenanceDocumentController {
	
	private static final String KPME_DISAPPROVAL_NOTE_DIALOG = "KPMEDisapprovalNote-Dialog";	
	
	
	// this method will intercept all disapprove requests and show the dialog asking for user explanantion for the disapproval.
	// TODO: once the rice team implements the same functionality this method can be removed 
	@Override
	public ModelAndView disapprove(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView retVal = null;		
		
		// check if the dialog has not yet been answered by the user
		if (!hasDialogBeenAnswered(KPME_DISAPPROVAL_NOTE_DIALOG, form)) {
			// redirect back to client to display lightbox
			retVal = showDialog(KPME_DISAPPROVAL_NOTE_DIALOG, form, request, response);
		}
		else {
			// get the OK-CANCEL response entered by the user
			boolean clickedOK = getBooleanDialogResponse(KPME_DISAPPROVAL_NOTE_DIALOG, form, request, response);
			// clear all dialogs in order to display warning again
			form.getDialogManager().removeAllDialogs();
			if (clickedOK) {
				// check if reason for disapproval is given, if so allow the disapproval to go thru,
				// but if not then display dialog again
				if(StringUtils.isEmpty(((KPMEMaintenanceDocumentForm) form).getDisapprovalNoteText())) {
					retVal = showDialog(KPME_DISAPPROVAL_NOTE_DIALOG, form, request, response);
				}
				else {
					retVal = super.disapprove(form, result, request, response);
				}
			}
			else {
				// just show the form again since they dont wish to disapprove
				retVal = getUIFModelAndView(form);
			}
		}
        
        return retVal;
	}

    /**
     * Goes back to the return url. If form is accessed from the action list or doc search return to application url.
     */
    @RequestMapping(params = "methodToCall=close")
    public ModelAndView cancel(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {
        Properties props = new Properties();
        props.put(UifParameters.METHOD_TO_CALL, UifConstants.MethodToCallNames.REFRESH);

        String command = form.getCommand();
        String returnUrl = form.getReturnLocation();

        //if form is accessed from the action list or doc search return to application url
        if (StringUtils.equals(command, "displayDocSearchView")
                || StringUtils.equals(command, "displayActionListView")
                || StringUtils.equals(command, "displaySuperUserView")) {
            returnUrl = ConfigContext.getCurrentContextConfig().getProperty(KRADConstants.APPLICATION_URL_KEY);
        }
        return performRedirect(form, returnUrl, props);
    }
	
	
	@Override
    protected MaintenanceDocumentForm createInitialForm(HttpServletRequest request) {
        return new KPMEMaintenanceDocumentForm();
    }
	
	
	@Override
	protected String generateDisapprovalNote(DocumentFormBase form, boolean checkSensitiveData) {
		return ((KPMEMaintenanceDocumentForm) form).getDisapprovalNoteText();
    }
	

}