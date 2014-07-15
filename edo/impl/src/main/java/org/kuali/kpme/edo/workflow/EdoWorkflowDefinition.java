package org.kuali.kpme.edo.workflow;

import java.util.Date;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 4/23/14
 * Time: 11:24 AM
 */
public class EdoWorkflowDefinition {

    private String workflowId;
    private String workflowName;
    private String workflowDescription;
    private String workflowUpdatedBy;
    private Date workflowUpdated;

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getWorkflowDescription() {
        return workflowDescription;
    }

    public void setWorkflowDescription(String workflowDescription) {
        this.workflowDescription = workflowDescription;
    }

    public String getWorkflowUpdatedBy() {
        return workflowUpdatedBy;
    }

    public void setWorkflowUpdatedBy(String workflowUpdatedBy) {
        this.workflowUpdatedBy = workflowUpdatedBy;
    }

    public Date getWorkflowUpdated() {
        return workflowUpdated;
    }

    public void setWorkflowUpdated(Date workflowUpdated) {
        this.workflowUpdated = workflowUpdated;
    }
}
