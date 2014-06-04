package org.kuali.kpme.edo.item.web;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.candidate.EdoCandidate;
import org.kuali.kpme.edo.candidate.EdoSelectedCandidate;
import org.kuali.kpme.edo.dossier.EdoCandidateDossier;
import org.kuali.kpme.edo.item.EdoItem;
import org.kuali.kpme.edo.item.EdoItemV;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.action.ActionItem;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.MessageMap;
import org.kuali.rice.krad.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * $HeadURL$ $Revision$ Created with IntelliJ IDEA. User: bradleyt Date: 3/18/13
 * Time: 11:38 AM
 */
public class EdoDownloadFileAction extends EdoAction {

	static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(EdoDownloadFileAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

        MessageMap msgmap = GlobalVariables.getMessageMap();
        String itemId = request.getParameter("itemID");
        EdoSelectedCandidate selectedCandidate = getItemSelectedCandidate(new BigDecimal(itemId));

        if (canViewDossier(selectedCandidate.getCandidateDossierID())) {

            HttpSession ssn = request.getSession();
            String nidParam = EdoConstants.EDO_DEFAULT_CHECKLIST_NODE_ID;  // set default node ID

            if (ObjectUtils.isNotNull(ssn.getAttribute("nid")) ) {
                nidParam = ssn.getAttribute("nid").toString();
            }
            String nidOnURL = request.getParameter("nid");
            // override node ID if it's provided
            if (StringUtils.isNotEmpty(nidOnURL)) {
                nidParam = nidOnURL;
            }
            int currentTreeNodeID = Integer.parseInt(nidParam.split("_")[2]);

            List<EdoItemV> itemList = EdoServiceLocator.getEdoItemVService().getItemList(selectedCandidate.getCandidateDossierID(), BigDecimal.valueOf(currentTreeNodeID));
            BigDecimal itemId1 = new BigDecimal(itemId);
            boolean found = false;
            for (EdoItemV item1 : itemList) {

                //comparison of big decimals
                int result = item1.getItemID().compareTo(itemId1);
                if( result == 0 ) {
                    found = true;
                    LOG.info("Download file action invoked");
                    if (StringUtils.isNotBlank(itemId)) {
                        ByteArrayOutputStream bao = new ByteArrayOutputStream();
                        try {
                            EdoItem item = EdoServiceLocator.getEdoItemService().getFile(BigDecimal.valueOf(Integer.parseInt(itemId)), bao);
                            String newFileName = item.getFileName().replaceAll(" ", "_").replaceAll(",", "_") + "_" + item.getFilenameFromPath();
                            String contentType = item.getContentType();
                            LOG.info("Starting file download [" + item.getFileName() + "] content-type: [" + contentType + "]");
                            response.setContentType(contentType);
                            response.addHeader("content-disposition", "filename=\"" + newFileName + "\"");
                            response.setContentLength(bao.size());
                            bao.writeTo(response.getOutputStream());
                            bao.close();
                            response.getOutputStream().flush();
                            response.getOutputStream().close();
                            LOG.info("Output stream closed and flushed");
                        }
                        catch (Exception e) {
                            LOG.error("Error: " + e.getMessage());
                            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.fileDownload.missing", item1.getFileName(), e.getMessage());
                        }
                    }
                }
            }
            if (!found) {
                LOG.info("Item ID not found in item list");
            }
        } else {
            LOG.info("Not authorized to view this dossier");
        }
        ActionForward fwd = mapping.findForward("basic");

        return fwd;
    }

    private EdoSelectedCandidate getItemSelectedCandidate(BigDecimal itemId) {

        EdoItemV item = EdoServiceLocator.getEdoItemVService().getEdoItem(itemId);
        EdoCandidateDossier dossier = EdoServiceLocator.getEdoCandidateDossierService().getCandidateDossier(item.getDossierID());
        EdoCandidate candidate = EdoServiceLocator.getCandidateService().getCandidateByUsername(dossier.getUsername());

        EdoSelectedCandidate selectedCandidate = new EdoSelectedCandidate(candidate, true);

        return selectedCandidate;
    }

    private boolean canViewDossier(BigDecimal dossierId) {
        boolean canViewDossier = false;
        EdoCandidateDossier candidateDossier = EdoServiceLocator.getEdoCandidateDossierService().getCandidateDossier(dossierId);

        // check for permission to view current dossier
        List<EdoCandidateDossier> dossierList = new LinkedList<EdoCandidateDossier>();

        // if this user is the candidate
        if (candidateDossier.getUsername().equals(EdoContext.getUser().getNetworkId())) {
            dossierList.add(candidateDossier);
        }
        // is this user a delegate for a candidate with a dossier? if so, add the dossier to the list
        List<String> candidatesForThisDelegate = EdoServiceLocator.getEdoMaintenanceService().getDelegatesCandidateList(EdoContext.getUser().getEmplId());
        for (String candidateEmplid : candidatesForThisDelegate) {
            if (KimApiServiceLocator.getIdentityService().getEntityByPrincipalName(candidateDossier.getUsername()).getId().equals(candidateEmplid)) {
                dossierList.add(candidateDossier);
            }
        }
        // is this user a guest of the dossier's candidate and is the dossier CLOSED
        List<String> guestList = EdoServiceLocator.getEdoMaintenanceService().getCandidateExistingGuests(candidateDossier.getDossierId());
        if (CollectionUtils.isNotEmpty(guestList)) {
            if (guestList.contains(EdoContext.getUser().getEmplId())) {
                dossierList.add(candidateDossier);
            }
        }

        // is this user an administrator with upload external letter permissions?
        if (EdoServiceLocator.getAuthorizationService().isAuthorizedToUploadExternalLetter_W(EdoContext.getPrincipalId(),candidateDossier.getDossierId().intValue())) {
            dossierList.add(candidateDossier);
        }

        // is this user a member of a group that has access to this dossier?
        if (StringUtils.isNotEmpty(candidateDossier.getDocumentId())) {
            try {
                //List<ActionRequestValue> actionRequestValues = new LinkedList<ActionRequestValue>();
            	List<ActionItem> actionRequestValues = new LinkedList<ActionItem>();

                List<String> groupIds = new LinkedList<String>();

                //actionRequestValues.addAll(KEWServiceLocator.getActionRequestService().findAllActionRequestsByDocumentId(candidateDossier.getDocumentId()));
                actionRequestValues.addAll(KewApiServiceLocator.getActionListService().getAllActionItems(candidateDossier.getDocumentId()));

                //Find the group ids to check.
                //for (ActionRequestValue actionRequestValue : actionRequestValues) {
                  for (ActionItem actionRequestValue : actionRequestValues) {
                    if (StringUtils.isNotEmpty(actionRequestValue.getGroupId())) {
                        groupIds.add(actionRequestValue.getGroupId());
                    }
                }

                for (String groupId : groupIds) {
                    if (isMemberOfGroup(EdoContext.getPrincipalId(), groupId)) {
                        dossierList.add(candidateDossier);
                        break;
                    }
                }
            } catch (Exception ex) {
                //Catch the exception.
            }
        }

        if (EdoServiceLocator.getEdoMaintenanceService().hasSuperUserRole(EdoContext.getPrincipalId())) {
            dossierList.add(candidateDossier);
        }

        if (CollectionUtils.isNotEmpty(dossierList)) {
            canViewDossier = true;
        }

        if (!canViewDossier) {
            MessageMap msgmap = GlobalVariables.getMessageMap();

            LOG.error("User does not have access rights to view this dossier.");
            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.access.dossier");

        }
        return canViewDossier;
    }

    private boolean isMemberOfGroup(String principalId, String groupId) {
        boolean isMember = false;

        List<String> allPrincipals = EdoContext.getPrincipalDelegators(principalId);

        for (String principal : allPrincipals) {
            isMember = isMember || KimApiServiceLocator.getGroupService().isMemberOfGroup(principal, groupId);
        }

        return isMember;

    }

}
