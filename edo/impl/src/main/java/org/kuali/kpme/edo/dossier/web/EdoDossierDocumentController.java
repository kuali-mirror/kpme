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
package org.kuali.kpme.edo.dossier.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.edo.dossier.EdoDossierBo;
import org.kuali.kpme.edo.dossier.EdoDossierDocument;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.krad.datadictionary.validation.result.DictionaryValidationResult;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.SequenceAccessorService;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.web.controller.TransactionalDocumentControllerBase;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/edoDossier")
public class EdoDossierDocumentController extends TransactionalDocumentControllerBase {
	
	private transient SequenceAccessorService sequenceAccessorService;
	
	@Override
	protected DocumentFormBase createInitialForm(HttpServletRequest request) {
		return new EdoDossierForm();
	}
	
	@Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request, HttpServletResponse response) {
    	
		EdoDossierForm edoDossierForm = (EdoDossierForm) form;
		EdoDossierBo edoDossier = edoDossierForm.getEdoDossier();

        return super.start(edoDossierForm, request, response);
	}
	
//	@Override
//    protected void createDocument(DocumentFormBase form) throws WorkflowException {
//        super.createDocument(form);
//        
//        EdoDossierForm edoDossierForm = (EdoDossierForm) form;
//        EdoDossierDocument edoDossierDocument = (EdoDossierDocument) edoDossierForm.getDocument();
//		
//	    if (StringUtils.isEmpty(edoDossierDocument.getDocumentHeader().getDocumentDescription())) {
//	    	edoDossierDocument.getDocumentHeader().setDocumentDescription(edoDossierForm.getEdoDossier().getCandidatePrincipalName()+" ("+edoDossierForm.getEdoDossier().getCandidatePrincipalName() +")");
//	    }
//	    
//	    edoDossierDocument.setEdoDossierID(edoDossierForm.getEdoDossier().getEdoDossierID());
//	    edoDossierDocument.setEdoDossier(edoDossierForm.getEdoDossier());
//    }

    @RequestMapping(params = "methodToCall=submit")
    public ModelAndView submit(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	EdoDossierForm edoDossierForm = (EdoDossierForm) form;
    	EdoDossierBo edoDossier = edoDossierForm.getEdoDossier();
        DictionaryValidationResult validationResults = KRADServiceLocatorWeb.getDictionaryValidationService().validate(((EdoDossierForm) form).getEdoDossier());
    	if (validationResults.getNumberOfErrors() == 0) {
        	
        	// call customized validation on the missed punch before creating document
        	/*boolean validFlag = new MissedPunchDocumentRule().runValidation(aMissedPunch);
        	if(validFlag) {
	            try {
	                createDocument(missedPunchForm);
	                save(missedPunchForm, result, request, response);
	
	                if (StringUtils.isNotBlank(missedPunchForm.getMissedPunch().getNote())) {
	                    Document doc = missedPunchForm.getDocument();
	                    Note note = new Note();
	                    note.setNoteText(missedPunchForm.getMissedPunch().getNote());
	                    note.setAuthorUniversalIdentifier(HrContext.getPrincipalId());
	                    note.setNotePostedTimestampToCurrent();
	                    doc.setNotes(Collections.<Note>singletonList(note));
	                }
	                ModelAndView modelAndView = route(missedPunchForm, result, request, response);
	                missedPunchForm.setMissedPunchSubmitted(true);
	
	                return modelAndView;
	
	            } catch (ValidationException exception) {
	                //ignore
	            }
        	}*/
        }
        return getUIFModelAndView(form);
    }

//	@Override
//    @RequestMapping(params = "methodToCall=route")
//    public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
//
//		EdoDossierForm edoDossierForm = (EdoDossierForm) form;
//		EdoDossierDocument edoDossierDocument = (EdoDossierDocument) edoDossierForm.getDocument();
//		EdoDossierBo edoDossier = (EdoDossierBo) edoDossierDocument.getEdoDossier();
//		//edoDossier = EdoDossierBo.from(TkServiceLocator.getEdoDossierService().addClockLog(EdoDossierBo.to(edoDossier), TKUtils.getIPAddressFromRequest(request)));
//		
//		edoDossierDocument.setEdoDossier(edoDossier);
//		edoDossierForm.setDocument(edoDossierDocument);
//   	
//    	return super.route(edoDossierForm, result, request, response);
//    }
//	

	@Override
    @RequestMapping(params = "methodToCall=approve")
    public ModelAndView approve(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EdoDossierDocument edoDossierDocument = (EdoDossierDocument) form.getDocument();
        EdoDossierBo edoDossier = edoDossierDocument.getEdoDossier();
    	//edoDossier = EdoDossierBo.from(EdoServiceLocator.getEdoDossierService().updateClockLog(EdoDossierBo.to(edoDossier), TKUtils.getIPAddressFromRequest(request)));
        
    	return super.approve(form, result, request, response);
    }

	
    protected SequenceAccessorService getSequenceAccessorService() {
        if (sequenceAccessorService == null) {
            sequenceAccessorService = KNSServiceLocator.getSequenceAccessorService();
        }
        return sequenceAccessorService;
    }

    public void setSequenceAccessorService(SequenceAccessorService sequenceAccessorService) {
        this.sequenceAccessorService = sequenceAccessorService;
    }

    /*@Override
    @RequestMapping(params = "methodToCall=docHandler")
    public ModelAndView docHandler(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = super.docHandler(form, result, request, response);

        //return getUIFModelAndView(form);
        //List<? extends Component> pageComponents = ((MissedPunchForm)form).getView().getItems().get(0).getItems();
        List<? extends Component> pageComponents = ((CollectionGroup) form.getView().getItems().get(0)).getItems();
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
    }*/

    @Override
    @RequestMapping(params = "methodToCall=back")
    public ModelAndView back(@ModelAttribute("KualiForm") UifFormBase form,
                             BindingResult result, HttpServletRequest request,
                             HttpServletResponse response) {
        if (!StringUtils.contains(form.getReturnLocation(), "dataObjectClassName="+ EdoDossierBo.class.getName())) {
            form.setReturnLocation(UifConstants.NO_RETURN);
        }
        return super.back(form, result, request, response);
    }

}
