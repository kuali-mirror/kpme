package org.kuali.kpme.edo.candidate.delegate.web;

import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.base.web.EdoForm;
import org.kuali.kpme.edo.candidate.delegate.EdoChairDelegate;
import org.kuali.rice.kim.api.role.RoleMembership;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EdoAssignChairDelegateForm extends EdoForm {

    private static final long serialVersionUID = 1L;
    private String userId;
    private String emplToAdd;
    private String emplToDelete;
    private String name;
    private String emplId;
    private boolean isChairDelegateAdded = false;
    private boolean isAlreadyChairDelegate = false;
    private boolean isChairDelegateDeleted = false;
    private Map<String, String> chairDelegates = new HashMap<String, String>();
    private Date startDate;
    private Date endDate;
    //edo-114
    private List<RoleMembership> chairDelegatesInfo = new ArrayList<RoleMembership>();
    //edo candidate delegate object
    private List<EdoChairDelegate> edoChairDelegatesInfo = new ArrayList<EdoChairDelegate>();
    private List<String> validDelegateRoles = new ArrayList<String>();


    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        isChairDelegateAdded = false;
        isChairDelegateDeleted = false;
        isAlreadyChairDelegate = false;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmplToAdd() {
        return emplToAdd;
    }


    public void setEmplToAdd(String emplToAdd) {
        this.emplToAdd = emplToAdd;
    }



    public String getEmplToDelete() {
        return emplToDelete;
    }

    public void setEmplToDelete(String emplToDelete) {
        this.emplToDelete = emplToDelete;
    }

    public boolean isChairDelegateAdded() {
        return isChairDelegateAdded;
    }

    public void setChairDelegateAdded(boolean isChairDelegateAdded) {
        this.isChairDelegateAdded = isChairDelegateAdded;
    }

    public boolean isAlreadyChairDelegate() {
        return isAlreadyChairDelegate;
    }

    public void setAlreadyChairDelegate(boolean isAlreadyChairDelegate) {
        this.isAlreadyChairDelegate = isAlreadyChairDelegate;
    }

    public boolean isChairDelegateDeleted() {
        return isChairDelegateDeleted;
    }

    public void setChairDelegateDeleted(boolean isChairDelegateDeleted) {
        this.isChairDelegateDeleted = isChairDelegateDeleted;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getEmplId() {
        return emplId;
    }


    public void setEmplId(String emplId) {
        this.emplId = emplId;
    }



    public Map<String, String> getChairDelegates() {
        return chairDelegates;
    }

    public void setChairDelegates(Map<String, String> chairDelegates) {
        this.chairDelegates = chairDelegates;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<RoleMembership> getChairDelegatesInfo() {
        return chairDelegatesInfo;
    }

    public void setChairDelegatesInfo(
            List<RoleMembership> chairDelegatesInfo) {
        this.chairDelegatesInfo = chairDelegatesInfo;
    }

    public List<EdoChairDelegate> getEdoChairDelegatesInfo() {
        return edoChairDelegatesInfo;
    }

    public void setEdoChairDelegatesInfo(
            List<EdoChairDelegate> edoChairDelegatesInfo) {
        this.edoChairDelegatesInfo = edoChairDelegatesInfo;
    }

    public List<String> getValidDelegateRoles() {
        return validDelegateRoles;
    }

    public void setValidDelegateRoles(List<String> validDelegateRoles) {
        this.validDelegateRoles = validDelegateRoles;
    }
}
