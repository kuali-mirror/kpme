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

import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kuali.kpme.core.api.bo.service.HrBusinessObjectService;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/kpme/effectiveDateMaintenance")
public class EffectiveDateMaintenanceController extends KPMEMaintenanceDocumentController {
	
	private static final String KPME_EFFECTIVE_DATE_WARNING_DIALOG = "KPMEEffectiveDateWarning-Dialog";
	private static final String HR_BUSINESS_OBJECT_SERVICE_NAME = "hrBusinessObjectService";


	
	@Override
	@RequestMapping(params = "methodToCall=save")
	public ModelAndView save(@ModelAttribute("KualiForm") final DocumentFormBase form, final BindingResult result, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		return showEffectiveDateWarningIfNeeded(form, result, request, response, new Callable<ModelAndView>() {
				@Override
				public ModelAndView call() throws Exception {
					// register the superclass method as the handler
					return EffectiveDateMaintenanceController.super.save(form, result, request, response);
				}
		});        
	}
	
	@Override
	@RequestMapping(params = "methodToCall=route")
	public ModelAndView route(@ModelAttribute("KualiForm") final DocumentFormBase form, final BindingResult result, final HttpServletRequest request, final HttpServletResponse response) {
		// the try-catch below was needed since route was declared to throw no exceptions in the superclass.
		try {
			return showEffectiveDateWarningIfNeeded(form, result, request, response, new Callable<ModelAndView>() {
					@Override
					public ModelAndView call() throws Exception {
						// register the superclass method as the handler
						return EffectiveDateMaintenanceController.super.route(form, result, request, response);
					}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return getUIFModelAndView(form);
		}
	}
	
	@Override
	@RequestMapping(params = "methodToCall=blanketApprove")
	public ModelAndView blanketApprove(@ModelAttribute("KualiForm") final DocumentFormBase form, final BindingResult result, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		return showEffectiveDateWarningIfNeeded(form, result, request, response, new Callable<ModelAndView>() {
				@Override
				public ModelAndView call() throws Exception {
					// register the superclass method as the handler
					return EffectiveDateMaintenanceController.super.blanketApprove(form, result, request, response);
				}
		});        
	}
	
	@Override
	@RequestMapping(params = "methodToCall=approve")
	public ModelAndView approve(@ModelAttribute("KualiForm") final DocumentFormBase form, final BindingResult result, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		return showEffectiveDateWarningIfNeeded(form, result, request, response, new Callable<ModelAndView>() {
				@Override
				public ModelAndView call() throws Exception {
					// register the superclass method as the handler
					return EffectiveDateMaintenanceController.super.approve(form, result, request, response);
				}
		});        
	}
	
	@Override
	@RequestMapping(params = "methodToCall=complete")
	public ModelAndView complete(@ModelAttribute("KualiForm") final DocumentFormBase form, final BindingResult result, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		return showEffectiveDateWarningIfNeeded(form, result, request, response, new Callable<ModelAndView>() {
				@Override
				public ModelAndView call() throws Exception {
					// register the superclass method as the handler
					return EffectiveDateMaintenanceController.super.complete(form, result, request, response);
				}
		});        
	}
	
	
	// This method contains the common pre-handler logic to show the effective date warning dialog (if needed) for any particular handler action that we wish to 'intercept'. 
	// Currently this set of actions includes 'save', 'route' 'approve', 'complete' and 'blanket approve'. 
	// The callable parameter encapsulates the actual super class handler method to be invoked in case of either an absence of warning or user's confirmation on the warning.
	protected ModelAndView showEffectiveDateWarningIfNeeded(@ModelAttribute("KualiForm") DocumentFormBase form, 
											BindingResult result, 
											HttpServletRequest request, 
											HttpServletResponse response, 
											Callable<ModelAndView> callable) throws Exception {
		
		ModelAndView retVal = null;		
				
		// first check if the dialog has already been answered by the user; this is an optimization to avoid checking existence of newer BO version 
		// in case its the second pass through (due to user input on the dialog shown in the first pass).
		if (!hasDialogBeenAnswered(KPME_EFFECTIVE_DATE_WARNING_DIALOG, form)) {
			if (doesNewerVersionExist((MaintenanceDocument) form.getDocument())) {
				// redirect back to client to display lightbox
				retVal = showDialog(KPME_EFFECTIVE_DATE_WARNING_DIALOG, form, request, response);
			} 
			else {
				// just call the registered handler method; no warning is to be shown
				retVal = callable.call();
			}
		}
		else {
			// get the response entered by the user
			boolean areYouSure = getBooleanDialogResponse(KPME_EFFECTIVE_DATE_WARNING_DIALOG, form, request, response);
			// clear all dialogs in order to display warning again
			form.getDialogManager().removeAllDialogs();
			if (areYouSure) {
				// call the registered handler method; user has confirmed
				retVal = callable.call();
			}
			else {
				// just show the form; user does not wish to execute whichever method that triggered this dialog
				retVal = getUIFModelAndView(form);
			}
		}
        
        return retVal;
	}
	
	public HrBusinessObjectService getHrBusinessObjectService() {
		return HrServiceLocator.getService(HR_BUSINESS_OBJECT_SERVICE_NAME);		
	}


	// This method will first check for availability of business keys for the BO and if so it simply delegates 
	// to the newer-version checking logic in the hr business object service impl obtained via the getter. 
	// It will throw an exception if the businesss keys are unavailable for any maintenance operation other than 'create new'. 
	// It will return false if businesss keys are unavailable for 'create new'.
    protected boolean doesNewerVersionExist(MaintenanceDocument document) throws Exception {
    	boolean retVal = false;
    	// get the new bo from the maintenance document
    	HrBusinessObject newBo = (HrBusinessObject) document.getNewMaintainableObject().getDataObject();
    	// check if all Business Key Values are available
    	if(newBo.areAllBusinessKeyValuesAvailable()) {
        	// use the hr BO service to check if a new version exists
    		retVal = getHrBusinessObjectService().doesNewerVersionExist(newBo);
    	}
    	else if(!document.isNew()){
    		// since its not a 'create new' operation all keys should have been present, throw an exception
    		throw new Exception("All Business keys are not available for " + newBo);
    	}
		return retVal;
    }
    
	
}