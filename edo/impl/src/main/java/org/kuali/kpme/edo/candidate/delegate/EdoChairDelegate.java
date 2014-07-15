package org.kuali.kpme.edo.candidate.delegate;

import org.joda.time.DateTime;

public class EdoChairDelegate {

    private String delegatePrincipalId;
    private String delegatePrincipalName;
    private String delegateFullName;
    private DateTime startDate;
    private DateTime endDate;
    public String getDelegatePrincipalId() {
        return delegatePrincipalId;
    }
    public void setDelegatePrincipalId(String delegatePrincipalId) {
        this.delegatePrincipalId = delegatePrincipalId;
    }
    public String getDelegatePrincipalName() {
        return delegatePrincipalName;
    }
    public void setDelegatePrincipalName(String delegatePrincipalName) {
        this.delegatePrincipalName = delegatePrincipalName;
    }

    public DateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }
    public DateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }
    public String getDelegateFullName() {
        return delegateFullName;
    }
    public void setDelegateFullName(String delegateFullName) {
        this.delegateFullName = delegateFullName;
    }



}
