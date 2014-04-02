package org.kuali.rice.krad.kpme.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;


/**
 * This abstract class is the root of the KPME maintenance document controller hierarchy.
 * 
 */
public abstract class KPMEMaintenanceDocumentController extends MaintenanceDocumentController {
	
	// this method will intercept all disapprove requests and show the dialog asking for user explanantion for the disapproval.
	@Override
	public ModelAndView disapprove(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// todo add dialog logic here
		return super.disapprove(form, result, request, response);		
	}
	
}