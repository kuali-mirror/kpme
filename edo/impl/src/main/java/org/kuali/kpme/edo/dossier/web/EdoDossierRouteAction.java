package org.kuali.kpme.edo.dossier.web;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.kpme.edo.api.candidate.EdoCandidate;
import org.kuali.kpme.edo.api.item.EdoItem;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.base.web.EdoForm;
import org.kuali.kpme.edo.item.EdoItemBo;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.util.EdoRule;
import org.kuali.kpme.edo.workflow.DossierProcessDocumentHeader;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.action.ActionType;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.MessageMap;
import org.kuali.rice.krad.util.ObjectUtils;

public class EdoDossierRouteAction extends EdoAction {

    public ActionForward routeDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoDossierRouteForm edoDossierRouteForm = (EdoDossierRouteForm)form;
        MessageMap msgmap = GlobalVariables.getMessageMap();
        boolean isRouted = false;

        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(edoDossierRouteForm.getCandidateUsername());

        if ( principal != null ) {
            if (EdoRule.isDossierReadyForRoute(BigDecimal.valueOf(edoDossierRouteForm.getDossierId()) ) ) {
                isRouted = EdoServiceLocator.getEdoDossierService().routeDocument(principal.getPrincipalId(), edoDossierRouteForm.getDossierId(), edoDossierRouteForm.getDossierType());
            } else {
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.route.ready");
            }
        }
        // update the selectedCandidate instance with the new dossier status
        if (isRouted) {
            EdoContext.getSelectedCandidate().setDossierStatus(EdoServiceLocator.getEdoDossierService().getEdoDossierById(edoDossierRouteForm.getDossierId().toString()).getDossierStatus());
        } else {
            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.route.general");
        }

        String prevPage = request.getHeader("REFERER");
        return new ActionRedirect(prevPage);
    }

    public ActionForward submitForSignOff(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String prevPage = request.getHeader("REFERER");
        return new ActionRedirect(prevPage);
    }

    public ActionForward returnToCandidate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoDossierRouteForm edoDossierRouteForm = (EdoDossierRouteForm)form;
        MessageMap msgmap = GlobalVariables.getMessageMap();
        boolean isRouted = false;
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(edoDossierRouteForm.getCandidateUsername());

        if (principal != null) {
            isRouted = EdoServiceLocator.getEdoDossierService().returnToCandidate(principal.getPrincipalId(), edoDossierRouteForm.getDossierId(), edoDossierRouteForm.getDossierType());
        }

        // update the selectedCandidate instance with the new dossier status
        if (isRouted) {
            EdoContext.getSelectedCandidate().setDossierStatus(EdoServiceLocator.getEdoDossierService().getEdoDossierById(edoDossierRouteForm.getDossierId().toString()).getDossierStatus());
        } else {
            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.route.general");
        }

        String prevPage = request.getHeader("REFERER");
        return new ActionRedirect(prevPage);
    }

    public ActionForward superUserApprove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoDossierRouteForm edoDossierRouteForm = (EdoDossierRouteForm)form;
        MessageMap msgmap = GlobalVariables.getMessageMap();
        boolean isRouted = false;
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(edoDossierRouteForm.getCandidateUsername());

        if (principal != null) {
            EdoServiceLocator.getEdoDossierService().superUserAction(principal.getPrincipalId(), edoDossierRouteForm.getDossierId(), edoDossierRouteForm.getDossierType(), ActionType.SU_APPROVE, edoDossierRouteForm.getNode());
        }

        // update the selectedCandidate instance with the new dossier status
        if (isRouted) {
            EdoContext.getSelectedCandidate().setDossierStatus(EdoServiceLocator.getEdoDossierService().getEdoDossierById(edoDossierRouteForm.getDossierId().toString()).getDossierStatus());
        } else {
            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.route.general");
        }

        String prevPage = request.getHeader("REFERER");
        return new ActionRedirect(prevPage);
    }

    public ActionForward superUserReturn(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoDossierRouteForm edoDossierRouteForm = (EdoDossierRouteForm)form;
        MessageMap msgmap = GlobalVariables.getMessageMap();
        boolean isRouted = false;
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(edoDossierRouteForm.getCandidateUsername());

        if (principal != null) {
            EdoServiceLocator.getEdoDossierService().superUserAction(principal.getPrincipalId(), edoDossierRouteForm.getDossierId(), edoDossierRouteForm.getDossierType(), ActionType.SU_RETURN_TO_PREVIOUS, edoDossierRouteForm.getNode());
        }

        // update the selectedCandidate instance with the new dossier status
        if (isRouted) {
            EdoContext.getSelectedCandidate().setDossierStatus(EdoServiceLocator.getEdoDossierService().getEdoDossierById(edoDossierRouteForm.getDossierId().toString()).getDossierStatus());
        } else {
            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.route.general");
        }

        String prevPage = request.getHeader("REFERER");
        return new ActionRedirect(prevPage);
    }

    public ActionForward docHandler(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoForm edoForm = (EdoForm)form;
        if(StringUtils.equals(request.getParameter(KewApiConstants.COMMAND_PARAMETER), KewApiConstants.DOCSEARCH_COMMAND)
                || StringUtils.equals(request.getParameter(KewApiConstants.COMMAND_PARAMETER), KewApiConstants.SUPERUSER_COMMAND)
                || StringUtils.equals(request.getParameter(KewApiConstants.COMMAND_PARAMETER), KewApiConstants.ACTIONLIST_COMMAND)) {
            return displayDocSearchView(mapping, edoForm, request, response);
        }

        return super.execute(mapping, form, request, response);
    }

    public ActionForward displayDocSearchView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String returnAction = "EdoIndex.do";

        String documentId = request.getParameter(KewApiConstants.DOCUMENT_ID_PARAMETER);
        WorkflowDocument workflowDocument = WorkflowDocumentFactory.loadDocument(EdoContext.getPrincipalId(), documentId);

        if (workflowDocument != null) {
            Principal principal = KimApiServiceLocator.getIdentityService().getPrincipal(workflowDocument.getInitiatorPrincipalId());
            if (principal != null) {
                EdoCandidate candidate = EdoServiceLocator.getCandidateService().getCandidateByUsername(principal.getPrincipalName());
                if (candidate != null) {
                    ActionRedirect rd = new ActionRedirect(mapping.findForward("candidateSelectRedirect"));
                    rd.addParameter("nid", "Dcklst_0_0");
                    rd.addParameter("cid", candidate.getEdoCandidateId().toString());
                    rd.addParameter("dossier", workflowDocument.getApplicationDocumentId());
                    return rd;
                }
            }
        }

        System.out.println("FOUND EDO " + workflowDocument.getApplicationDocumentId());
        return new ActionRedirect(returnAction);
    }
    public ActionForward routeSupplementalDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoDossierRouteForm edoDossierRouteForm = (EdoDossierRouteForm)form;
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(edoDossierRouteForm.getCandidateUsername());
          if (principal != null) {
        	 
           Boolean routed =  EdoServiceLocator.getEdoDossierService().routeSupplementalDocument(principal.getPrincipalId(), edoDossierRouteForm.getDossierId(), edoDossierRouteForm.getDossierType());
          
           if(routed) {
        	  //update edo_item_t table - set addendum_routed to 0 for supplemental category
        	  String edoChecklistItemID = EdoServiceLocator.getChecklistItemService().getChecklistItemByDossierID(edoDossierRouteForm.getDossierId()+"", EdoConstants.EDO_SUPPLEMENTAL_ITEM_CATEGORY_NAME).getEdoChecklistItemId();
              List<EdoItem> edoItems = EdoServiceLocator.getEdoItemService().getPendingItemsByDossierId(edoDossierRouteForm.getDossierId()+"", edoChecklistItemID);
              if(!edoItems.isEmpty()) {
               for(EdoItem edoItem : edoItems) {
            	   EdoItemBo edoItemBo = EdoItemBo.from(edoItem);
            	   //update
            	   edoItemBo.setRouted(true);
            	   EdoServiceLocator.getEdoItemService().saveOrUpdate(edoItemBo);
               	}
              }
        	 
           }
            
          }

        String prevPage = request.getHeader("REFERER");
        return new ActionRedirect(prevPage);
    }
    public ActionForward approveSupplemental(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//approve and stay in the same page
    	  EdoDossierRouteForm edoDossierRouteForm = (EdoDossierRouteForm)form;
          Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(edoDossierRouteForm.getCandidateUsername());
          String suppType = null;
          if(StringUtils.equals(edoDossierRouteForm.getDossierType(), "Tenure") || StringUtils.equals(edoDossierRouteForm.getDossierType(), "Tenure+Promotion"))   {
        	  suppType = "Tenure Supplemental";
          }
          if(StringUtils.equals(edoDossierRouteForm.getDossierType(), "Promotion"))   {
        	  suppType = "Promotion Supplemental";
          }
          if (principal != null) {
            	
            	EdoServiceLocator.getEdoDossierService().approveSupplemental(principal.getPrincipalId(), edoDossierRouteForm.getDossierId(), suppType);
            }
    	String prevPage = request.getHeader("REFERER");
        return new ActionRedirect(prevPage);
    	
    }
    public ActionForward approveSupplementalWithAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	//approve and stay in the same page
  	  EdoDossierRouteForm edoDossierRouteForm = (EdoDossierRouteForm)form;
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(edoDossierRouteForm.getCandidateUsername());
        String suppType = null;
        if(StringUtils.equals(edoDossierRouteForm.getDossierType(), "Tenure") || StringUtils.equals(edoDossierRouteForm.getDossierType(), "Tenure+Promotion"))   {
      	  suppType = "Tenure Supplemental";
        }
        if(StringUtils.equals(edoDossierRouteForm.getDossierType(), "Promotion"))   {
      	  suppType = "Promotion Supplemental";
        }
        if (principal != null) {
          	
          	EdoServiceLocator.getEdoDossierService().approveSupplemental(principal.getPrincipalId(), edoDossierRouteForm.getDossierId(), suppType);
          }
        //notify the current level of newly uploaded review letter
        DossierProcessDocumentHeader dossierDocumentHeader = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeader(edoDossierRouteForm.getDossierId());
        if (ObjectUtils.isNotNull(dossierDocumentHeader)) {
            //Get the parent workflow document (dossier).
        	WorkflowDocument dossierWorkflowDocument = WorkflowDocumentFactory.loadDocument(dossierDocumentHeader.getPrincipalId(), dossierDocumentHeader.getDocumentId());
            //Get the parent workflow document route level name.
            DocumentRouteHeaderValue dossierRouteHeader = KEWServiceLocator.getRouteHeaderService().getRouteHeader(dossierWorkflowDocument.getDocumentId());
            //make a service call to notify the current level where the parent edoc is 
            String contentSingle = "This is an FYI email notification:" 
            		+ EdoContext.getSelectedCandidate().getCandidateUsername() 
            		+ "e-dossier is currently under review at your level. In response to the addition of new materials by" + EdoContext.getSelectedCandidate().getCandidateUsername() + ", a formal response has been offered by an earlier level of review. Please consider these responses. No other action is required.";
					
			String contentMultiple = "This is an FYI email notification:"
					+ "In response to the addition of new materials by "+ EdoContext.getSelectedCandidate().getCandidateUsername() 
					+ "a formal response has been offered by an earlier level of review. Please consider these responses and notify others at your level (i.e., committee members) to do so as well. No other action is required.";
			
        	EdoServiceLocator.getEdoSupplementalPendingStatusService().notifyCurrentEdossierLevelForSuppDoc(dossierRouteHeader.getDocumentId(), dossierRouteHeader.getCurrentRouteLevelName(), contentSingle, contentMultiple );
        }
        String prevPage = request.getHeader("REFERER");
        return new ActionRedirect(prevPage);
  	
  }
    public ActionForward routeReconsiderDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoDossierRouteForm edoDossierRouteForm = (EdoDossierRouteForm)form;
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(edoDossierRouteForm.getCandidateUsername());

       
        if (principal != null) {
        	 Boolean routed =  EdoServiceLocator.getEdoDossierService().routeReconsiderDocument(principal.getPrincipalId(), edoDossierRouteForm.getDossierId(), edoDossierRouteForm.getDossierType());
       
        if(routed) {
     	   //update edo_item_t table - set addendum_routed to 0 for Reconsider category           
           String edoChecklistItemID = EdoServiceLocator.getChecklistItemService().getChecklistItemByDossierID(edoDossierRouteForm.getDossierId()+"", EdoConstants.EDO_RECONSIDERATION_ITEM_CATEGORY_NAME).getEdoChecklistItemId();
           List<EdoItem> edoItems = EdoServiceLocator.getEdoItemService().getPendingItemsByDossierId(edoDossierRouteForm.getDossierId()+"", edoChecklistItemID);
           if(!edoItems.isEmpty()) {
             for(EdoItem edoItem : edoItems) {
               EdoItemBo edoItemBo = EdoItemBo.from(edoItem);
         	   //update
               edoItemBo.setRouted(true);
         	   EdoServiceLocator.getEdoItemService().saveOrUpdate(edoItemBo);
            	}
             }
     	 
           }
        }

        String prevPage = request.getHeader("REFERER");
        return new ActionRedirect(prevPage);
    }
}
