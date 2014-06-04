package org.kuali.kpme.edo.candidate;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.dossier.EdoDossier;
import org.kuali.kpme.edo.dossier.type.EdoDossierType;
import org.kuali.kpme.edo.util.EdoConstants;

import java.math.BigDecimal;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/8/12
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoSelectedCandidate {
    private boolean isSelected;
    private BigDecimal candidateID;
    private String candidateLastname;
    private String candidateFirstname;
    private String candidateUsername;
    private String candidateCampusCode;
    private String candidateSchoolID;
    private String candidateDepartmentID;
    private BigDecimal candidateDossierID;
    private String aoe;
    private String rankSought;
    private String dossierTypeCode;
    private String dossierStatus;
    private String dossierTypeName;
    private String dossierTypeFlag;
    private String dossierWorkflowId;

    private String aoeDescription;

    public String getDossierTypeName() {
        return dossierTypeName;
    }

    public void setDossierTypeName(String dossierTypeName) {
        this.dossierTypeName = dossierTypeName;
    }

    public EdoSelectedCandidate() {}

    public EdoSelectedCandidate(EdoCandidate edoCandidate, Boolean isSelected) {
        EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getCurrentDossier(edoCandidate.getUserName());
        EdoDossierType dossierType = EdoServiceLocator.getEdoDossierTypeService().getEdoDossierType(dossier.getDossierTypeID());

        setSelected(isSelected);
        setCandidateID(edoCandidate.getCandidateID());
        setCandidateLastname(edoCandidate.getLastName());
        setCandidateFirstname(edoCandidate.getFirstName());
        setCandidateUsername(edoCandidate.getUserName());
        setCandidateCampusCode(edoCandidate.getCandidacyCampus());
        setCandidateDepartmentID(edoCandidate.getPrimaryDeptID());
        setCandidateSchoolID(edoCandidate.getCandidacySchool());
        setAoe(dossier.getAoeCode());
        setDossierTypeCode(dossierType.getDossierTypeCode());
        setCandidateDossierID(dossier.getDossierID());
        setDossierWorkflowId(dossier.getWorkflowId());
    }

    public String getDossierWorkflowId() {
        return dossierWorkflowId;
    }

    public void setDossierWorkflowId(String dossierWorkflowId) {
        this.dossierWorkflowId = dossierWorkflowId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCandidateCampusCode() {
        return candidateCampusCode;
    }

    public void setCandidateCampusCode(String candidateCampusCode) {
        this.candidateCampusCode = candidateCampusCode;
    }

    public String getCandidateSchoolID() {
        return candidateSchoolID;
    }

    public void setCandidateSchoolID(String candidateSchoolID) {
        this.candidateSchoolID = candidateSchoolID;
    }

    public String getCandidateDepartmentID() {
        return candidateDepartmentID;
    }

    public void setCandidateDepartmentID(String candidateDeparmentID) {
        this.candidateDepartmentID = candidateDeparmentID;
    }

    public BigDecimal getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(BigDecimal candidateID) {
        this.candidateID = candidateID;
    }

    public String getCandidateFirstname() {
        return candidateFirstname;
    }

    public void setCandidateFirstname(String candidateFirstname) {
        this.candidateFirstname = candidateFirstname;
    }

    public String getCandidateLastname() {
        return candidateLastname;
    }

    public void setCandidateLastname(String candidateLastname) {
        this.candidateLastname = candidateLastname;
    }

    public String getCandidateUsername() {
        return candidateUsername;
    }

    public void setCandidateUsername(String candidateUsername) {
        this.candidateUsername = candidateUsername;
    }

    public BigDecimal getCandidateDossierID() {
        return candidateDossierID;
    }

    public void setCandidateDossierID(BigDecimal candidateDossierID) {
        this.candidateDossierID = candidateDossierID;
    }

    public String getRankSought() {
        return rankSought;
    }

    public void setRankSought(String rankSought) {
        this.rankSought = rankSought;
    }

    public String getAoe() {
        return aoe;
    }

    public void setAoe(String aoe) {
        this.aoe = aoe;
    }

    public String getDossierTypeCode() {
        return dossierTypeCode;
    }

    public void setDossierTypeCode(String dossierTypeCode) {
        this.dossierTypeCode = dossierTypeCode;
        setDossierTypeFlag();
    }

    public String getDossierTypeFlag() {
        return dossierTypeFlag;
    }

    public void setDossierTypeFlag() {
        if (this.dossierTypeCode.startsWith("T")) {
            this.dossierTypeFlag = "Yes";
        } else {
            this.dossierTypeFlag = "No";
        }
    }

    public String getDossierStatus() {
        return dossierStatus;
    }

    public void setDossierStatus(String dossierStatus) {
        this.dossierStatus = dossierStatus;
    }

    public String getAoeDescription() {
        String aoeDescription = StringUtils.EMPTY;

        if (StringUtils.isNotBlank(this.aoe)) {
            String aoeValue = EdoConstants.AREA_OF_EXCELLENCE.get(this.aoe);
            if (StringUtils.isNotBlank(aoeValue)) {
                aoeDescription = aoeValue;
            }
        }

        return aoeDescription;
    }
}
