package org.kuali.kpme.edo.candidate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tcbradley
 * Date: 10/24/12
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoCandidate {
    private BigDecimal candidateID;
    private String lastName ;
    private String firstName;
    private String userName;
    private String currentRank;
    private String primaryDeptID ;
    private String tnpDeptID;
    private BigDecimal candidacyYear;
    private String candidacySchool;
    private String candidacyCampus;
    private String currentDossier;

    public BigDecimal getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(BigDecimal candidateID) {
        this.candidateID = candidateID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCurrentRank() {
        return currentRank;
    }

    public void setCurrentRank(String currentRank) {
        this.currentRank = currentRank;
    }

    public String getPrimaryDeptID() {
        return primaryDeptID;
    }

    public void setPrimaryDeptID(String primaryDeptID) {
        this.primaryDeptID = primaryDeptID;
    }

    public String getTnpDeptID() {
        return tnpDeptID;
    }

    public void setTnpDeptID(String tnpDeptID) {
        this.tnpDeptID = tnpDeptID;
    }

    public BigDecimal getCandidacyYear() {
        return candidacyYear;
    }

    public void setCandidacyYear(BigDecimal candidacyYear) {
        this.candidacyYear = candidacyYear;
    }

    public String getCandidacySchool() {
        return candidacySchool;
    }

    public void setCandidacySchool(String candidacySchool) {
        this.candidacySchool = candidacySchool;
    }

    public String getCandidacyCampus() {
        return candidacyCampus;
    }

    public void setCandidacyCampus(String candidacyCampus) {
        this.candidacyCampus = candidacyCampus;
    }

    public String getCurrentDossier() {
        return currentDossier;
    }

    public void setCurrentDossier(String currentDossier) {
        this.currentDossier = currentDossier;
    }

    public String getCandidateJSONString() {
        ArrayList<String> tmp = new ArrayList<String>();
        Type tmpType = new TypeToken<List<String>>() {}.getType();
        Gson gson = new Gson();

        tmp.add(this.getCandidateID().toString());
        tmp.add(this.getFirstName() + " " + this.getLastName());
        tmp.add(this.getLastName());
        tmp.add(this.getFirstName());
        tmp.add(this.getCurrentRank());
        tmp.add(this.getUserName());
        tmp.add(this.getPrimaryDeptID());
        tmp.add(this.getCandidacyCampus());
        tmp.add(this.getCandidacySchool());
        tmp.add(this.getCandidacyYear().toString());
        tmp.add(this.getCurrentDossier());
        // dossier ID
        return gson.toJson(tmp, tmpType).toString();
    }
}
