package org.kuali.kpme.edo.group;

import java.math.BigDecimal;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 12/18/13
 * Time: 10:27 AM
 */
public class EdoGroupDefinition {

    private BigDecimal groupId;
    private String workflowId;
    private String workflowLevel;
    private String dossierType;
    private String workflowType;
    private String kimTypeName;
    private String kimRoleName;

    public BigDecimal getGroupId() {
        return groupId;
    }

    public void setGroupId(BigDecimal groupId) {
        this.groupId = groupId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowLevel() {
        return workflowLevel;
    }

    public void setWorkflowLevel(String workflowLevel) {
        this.workflowLevel = workflowLevel;
    }

    public String getDossierType() {
        return dossierType;
    }

    public void setDossierType(String dossierType) {
        this.dossierType = dossierType;
    }

    public String getWorkflowType() {
        return workflowType;
    }

    public void setWorkflowType(String workflowType) {
        this.workflowType = workflowType;
    }

    public String getKimTypeName() {
        return kimTypeName;
    }

    public void setKimTypeName(String kimTypeName) {
        this.kimTypeName = kimTypeName;
    }

    public String getKimRoleName() {
        return kimRoleName;
    }

    public void setKimRoleName(String kimRoleName) {
        this.kimRoleName = kimRoleName;
    }
}
