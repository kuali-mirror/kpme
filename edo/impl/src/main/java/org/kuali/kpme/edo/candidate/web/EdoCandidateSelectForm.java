package org.kuali.kpme.edo.candidate.web;

import org.kuali.kpme.edo.base.web.EdoForm;

import java.util.LinkedList;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/13/12
 * Time: 9:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoCandidateSelectForm extends EdoForm {
    private String selectedAoe;
    private Integer cid;
    private String candidateUsername;
    private Integer dossier;
    List<String> previousNodes = new LinkedList<String>();
    List<String> futureNodes = new LinkedList<String>();

    public String getSelectedAoe() {
        return selectedAoe;
    }

    public void setSelectedAoe(String selectedAoe) {
        this.selectedAoe = selectedAoe;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCandidateUsername() {
        return candidateUsername;
    }

    public void setCandidateUsername(String candidateUsername) {
        this.candidateUsername = candidateUsername;
    }

    public Integer getDossier() {
        return dossier;
    }

    public void setDossier(Integer dossier) {
        this.dossier = dossier;
    }

    public List<String> getPreviousNodes() {
        return previousNodes;
    }

    public void setPreviousNodes(List<String> previousNodes) {
        this.previousNodes = previousNodes;
    }

    public List<String> getFutureNodes() {
        return futureNodes;
    }

    public void setFutureNodes(List<String> futureNodes) {
        this.futureNodes = futureNodes;
    }
}
