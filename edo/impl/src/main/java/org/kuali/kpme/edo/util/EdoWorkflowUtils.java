package org.kuali.kpme.edo.util;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.kpme.edo.dossier.EdoDossier;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;

import java.util.Collection;

public final class EdoWorkflowUtils {

	private EdoWorkflowUtils() {}
	
	public static String generateApplicationContent(EdoDossier dossier) {
		StringBuilder sb = new StringBuilder();
		sb.append("<dossier>");
		sb.append("<principalId>");
		sb.append(dossier.getCandidatePrincipalId());
		sb.append("</principalId>");
		sb.append("<departmentId>");
		sb.append(dossier.getDepartmentID());
		sb.append("</departmentId>");
		sb.append("<schoolId>");
		sb.append(dossier.getSchoolID());
		sb.append("</schoolId>");
		sb.append("<campusId>");
		sb.append(dossier.getCampusCode());
		sb.append("</campusId>");
        sb.append("<workflowId>");
        sb.append(dossier.getWorkflowId());
        sb.append("</workflowId>");
        sb.append("</dossier>");
		return sb.toString();
	}

    public static String generateSuppDocApplicationContent(EdoDossier dossier, Collection<String> authorizedNodes) {
        StringBuilder sb = new StringBuilder();
        sb.append("<dossier>");
		sb.append("<principalId>");
		sb.append(dossier.getCandidatePrincipalId());
		sb.append("</principalId>");
		sb.append("<departmentId>");
		sb.append(dossier.getDepartmentID());
		sb.append("</departmentId>");
		sb.append("<schoolId>");
		sb.append(dossier.getSchoolID());
		sb.append("</schoolId>");
		sb.append("<campusId>");
		sb.append(dossier.getCampusCode());
		sb.append("</campusId>");
        sb.append("<workflowId>");
        sb.append(dossier.getWorkflowId());
        sb.append("</workflowId>");
        sb.append("<authorizedNodes>");
        if (CollectionUtils.isNotEmpty(authorizedNodes)) {
            for (String node : authorizedNodes) {
                sb.append("<authorizedNode>" + node + "</authorizedNode>");
            }
        }
        sb.append("</authorizedNodes>");
        sb.append("</dossier>");
        return sb.toString();
    }
    public static String generateReconsiderApplicationContent(EdoDossier dossier, Collection<EdoReviewLayerDefinition> moveNodes) {
		StringBuilder sb = new StringBuilder();
		sb.append("<dossier>");
		sb.append("<principalId>");
		sb.append(dossier.getCandidatePrincipalId());
		sb.append("</principalId>");
		sb.append("<departmentId>");
		sb.append(dossier.getDepartmentID());
		sb.append("</departmentId>");
		sb.append("<schoolId>");
		sb.append(dossier.getSchoolID());
		sb.append("</schoolId>");
		sb.append("<campusId>");
		sb.append(dossier.getCampusCode());
		sb.append("</campusId>");
        sb.append("<workflowId>");
        sb.append(dossier.getWorkflowId());
        sb.append("</workflowId>");
        sb.append("<dossierStatus>");
		sb.append(EdoConstants.DOSSIER_STATUS.RECONSIDERATION);
		sb.append("</dossierStatus>");
		sb.append("<moveNodes>");
        if (CollectionUtils.isNotEmpty(moveNodes)) {
            for (EdoReviewLayerDefinition node : moveNodes) {
                sb.append("<moveNodeRouteLevel>" + node.getRouteLevel() + "</moveNodeRouteLevel>"); 
                sb.append("<moveNodeReviewLevel>" + node.getReviewLevel() + "</moveNodeReviewLevel>");
            }
        }
        sb.append("</moveNodes>");
		sb.append("</dossier>");
		return sb.toString();
	}
}
