package org.kuali.kpme.edo.candidate.web;

import org.kuali.kpme.edo.base.web.EdoForm;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/9/12
 * Time: 9:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoCandidateAddForm extends EdoForm {
    public String lastName;
    public String firstName;
    public String currentRank;
    public String primaryDepartmentID;
    public String tnpDepartmentID;
    public int candidacyYear;
    public String candidacySchool;
    public String candidacyCampus;

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

    public String getCurrentRank() {
        return currentRank;
    }

    public void setCurrentRank(String currentRank) {
        this.currentRank = currentRank;
    }

    public String getPrimaryDepartmentID() {
        return primaryDepartmentID;
    }

    public void setPrimaryDepartmentID(String primaryDepartmentID) {
        this.primaryDepartmentID = primaryDepartmentID;
    }

    public String getTnpDepartmentID() {
        return tnpDepartmentID;
    }

    public void setTnpDepartmentID(String tnpDepartmentID) {
        this.tnpDepartmentID = tnpDepartmentID;
    }

    public int getCandidacyYear() {
        return candidacyYear;
    }

    public void setCandidacyYear(int candidacyYear) {
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
}
