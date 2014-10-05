package org.kuali.kpme.edo.submitButton.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.candidate.guest.web.EdoAssignCandidateGuestForm;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.submitButton.EdoSubmitButton;
import org.kuali.kpme.edo.util.EdoUser;
import org.kuali.rice.krad.util.ObjectUtils;



public class EdoDisplaySubmitButtonAction extends EdoAction {
	
	 public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 EdoDisplaySubmitButtonForm edoDisplaySubmitButtonForm = (EdoDisplaySubmitButtonForm) form;
		 edoDisplaySubmitButtonForm.setSubmitButtonList(EdoServiceLocator.getEdoDisplaySubmitButtonService().getEdoSubmitButtonList());
	      return super.execute(mapping, form, request, response);
	    }
	
	
	 public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 EdoDisplaySubmitButtonForm edoDisplaySubmitButtonForm = (EdoDisplaySubmitButtonForm) form;
		 EdoSubmitButton edoSubmitButton = EdoServiceLocator.getEdoDisplaySubmitButtonService().getEdoSubmitButton(edoDisplaySubmitButtonForm.getCampusCode());
	        if (ObjectUtils.isNotNull(edoSubmitButton)) {
				 edoSubmitButton.setCampusCode(edoDisplaySubmitButtonForm.getCampusCode());
				 edoSubmitButton.setActiveFlag(edoDisplaySubmitButtonForm.getActiveFlag());
				 boolean isdded = EdoServiceLocator.getEdoDisplaySubmitButtonService().saveOrUpdate(edoSubmitButton);
				 edoDisplaySubmitButtonForm.setAdded(isdded);
	        }
	        else {
	        	  EdoSubmitButton edoSubButton = new EdoSubmitButton();
		          edoSubButton.setCampusCode(edoDisplaySubmitButtonForm.getCampusCode());
		          edoSubButton.setActiveFlag(edoDisplaySubmitButtonForm.getActiveFlag());
		          //EdoServiceLocator.getEdoDisplaySubmitButtonService().saveOrUpdate(edoSubButton);
		          boolean isdded = EdoServiceLocator.getEdoDisplaySubmitButtonService().saveOrUpdate(edoSubButton);
				 edoDisplaySubmitButtonForm.setAdded(isdded);
	        	
	        }
			 
	        edoDisplaySubmitButtonForm.setSubmitButtonList(EdoServiceLocator.getEdoDisplaySubmitButtonService().getEdoSubmitButtonList());

	     return mapping.findForward("basic");
	    }
	 
	  public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
			 EdoDisplaySubmitButtonForm edoDisplaySubmitButtonForm = (EdoDisplaySubmitButtonForm) form;
	        resetDelegateFormState(edoDisplaySubmitButtonForm);
	        return mapping.findForward("basic");
	    }
	  
	  private void resetDelegateFormState(EdoDisplaySubmitButtonForm edoDisplaySubmitButtonForm) {
	        if (ObjectUtils.isNotNull(edoDisplaySubmitButtonForm)) {
	        	edoDisplaySubmitButtonForm.setCampusCode(null);
	        	edoDisplaySubmitButtonForm.setActiveFlag(null);
	        	edoDisplaySubmitButtonForm.setAdded(false);
	        	
	        }
	    }

}
