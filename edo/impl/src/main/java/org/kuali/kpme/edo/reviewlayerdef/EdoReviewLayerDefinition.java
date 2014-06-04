package org.kuali.kpme.edo.reviewlayerdef;

import org.kuali.kpme.edo.EdoBusinessObject;

import java.math.BigDecimal;

public class EdoReviewLayerDefinition extends EdoBusinessObject implements Comparable<EdoReviewLayerDefinition> {

    private static final long serialVersionUID = -4931968769900946949L;
    private BigDecimal reviewLayerDefinitionId;
    private String nodeName;
    private String voteType;
    private String description;
    private boolean reviewLetter;
    private BigDecimal reviewLevel;
    private BigDecimal routeLevel;
    private String workflowId;
    private String workflowQualifier;

    public int compareTo(EdoReviewLayerDefinition edoReviewLayerDefinition) {
        return reviewLayerDefinitionId.compareTo(edoReviewLayerDefinition.getReviewLayerDefinitionId());
    }

    public BigDecimal getReviewLayerDefinitionId() {
        return reviewLayerDefinitionId;
    }

    public void setReviewLayerDefinitionId(BigDecimal reviewLayerDefinitionId) {
        this.reviewLayerDefinitionId = reviewLayerDefinitionId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getVoteType() {
        return voteType;
    }

    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isReviewLetter() {
        return reviewLetter;
    }

    public void setReviewLetter(boolean reviewLetter) {
        this.reviewLetter = reviewLetter;
    }

    public BigDecimal getReviewLevel() {
        return reviewLevel;
    }

    public void setReviewLevel(BigDecimal reviewLevel) {
        this.reviewLevel = reviewLevel;
    }

    public BigDecimal getRouteLevel() {
        return routeLevel;
    }

    public void setRouteLevel(BigDecimal routeLevel) {
        this.routeLevel = routeLevel;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowQualifier() {
        return workflowQualifier;
    }

    public void setWorkflowQualifier(String workflowQualifier) {
        this.workflowQualifier = workflowQualifier;
    }

    @Override
    protected BigDecimal getUniqueKey() {
        return reviewLayerDefinitionId;
    }

    @Override
    public BigDecimal getId() {
        return getReviewLayerDefinitionId();
    }

    @Override
    public void setId(BigDecimal id) {
        setReviewLayerDefinitionId(id);
    }
}
