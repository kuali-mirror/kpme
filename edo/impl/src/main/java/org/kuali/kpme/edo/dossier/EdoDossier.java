package org.kuali.kpme.edo.dossier;

import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.api.dossier.type.EdoDossierType;
import org.kuali.kpme.edo.dossier.type.EdoDossierTypeBo;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 1/14/13
 * Time: 9:05 AM
 */

public class EdoDossier {

    private BigDecimal dossierID;
    private BigDecimal dossierTypeID;
    private BigDecimal checkListID;
    private String     aoeCode;
    private String     campusCode;
    private String     departmentID;
    private String     schoolID;
    private String     currentRank;
    private String     rankSought;
    private Date       dueDate;
    private Date       createDate;
    private String     createdBy;
    private Date       lastUpdated;
    private String     updatedBy;
    private String     documentID;
    private String     dossierStatus;
    private String     candidateUsername;
    private String     secondaryUnit;
    private String     workflowId;

    private EdoDossierType dossierType;
    // methods

    public BigDecimal getDossierID() {
        return dossierID;
    }

    public void setDossierID(BigDecimal dossierID) {
        this.dossierID = dossierID;
    }

    public BigDecimal getDossierTypeID() {
        return dossierTypeID;
    }

    public void setDossierTypeID(BigDecimal dossierTypeID) {
        this.dossierTypeID = dossierTypeID;
    }

    public EdoDossierType getDossierType() {
        if (ObjectUtils.isNull(dossierType) && ObjectUtils.isNotNull(dossierTypeID)) {
            this.dossierType = EdoServiceLocator.getEdoDossierTypeService().getEdoDossierTypeById(dossierTypeID.toString());
            return dossierType;
        } else {
            return this.dossierType;
        }
    }

    public BigDecimal getCheckListID() {
        return checkListID;
    }

    public void setCheckListID(BigDecimal checkListID) {
        this.checkListID = checkListID;
    }

    public String getAoeCode() {
        return aoeCode;
    }

    public String getAoeString() {
        return EdoConstants.AREA_OF_EXCELLENCE.get(this.aoeCode);
    }
    public void setAoeCode(String aoeCode) {
        this.aoeCode = aoeCode;
    }

    public String getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(String campusCode) {
        this.campusCode = campusCode;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public String getCurrentRank() {
        return currentRank;
    }

    public void setCurrentRank(String currentRank) {
        this.currentRank = currentRank;
    }

    public String getRankSought() {
        return rankSought;
    }

    public void setRankSought(String rankSought) {
        this.rankSought = rankSought;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getDossierStatus() {
        return dossierStatus;
    }

    public void setDossierStatus(String dossierStatus) {
        this.dossierStatus = dossierStatus;
    }

    public String getCandidateUsername() {
        return candidateUsername;
    }

    public void setCandidateUsername(String candidateUsername) {
        this.candidateUsername = candidateUsername;
    }
        
    public String getSecondaryUnit() {
		return secondaryUnit;
	}

	public void setSecondaryUnit(String secondaryUnit) {
		this.secondaryUnit = secondaryUnit;
	}

	public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getCandidatePrincipalId() {
    	Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(this.getCandidateUsername());
    	if(principal == null) {
    		throw new IllegalArgumentException("Principal not found in KIM");
    	}
    	return principal.getPrincipalId();
    }
}
