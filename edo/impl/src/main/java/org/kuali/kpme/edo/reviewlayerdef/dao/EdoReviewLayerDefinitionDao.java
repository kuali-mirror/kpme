package org.kuali.kpme.edo.reviewlayerdef.dao;

import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.reviewlayerdef.EdoSuppReviewLayerDefinition;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface EdoReviewLayerDefinitionDao {

    public EdoReviewLayerDefinition getReviewLayerDefinition(BigDecimal reviewLayerDefinitionID);
    public EdoReviewLayerDefinition getReviewLayerDefinition(String workflowId, BigDecimal reviewLayerDefinitionID);
    public EdoReviewLayerDefinition getReviewLayerDefinition(String workflowId, String nodeName, BigDecimal routeLevel);
    public EdoReviewLayerDefinition getReviewLayerDefinition(String workflowId, String nodeName);

    public List<EdoReviewLayerDefinition> getReviewLayerDefinitions();
    public List<EdoReviewLayerDefinition> getReviewLayerDefinitions(Set<String> nodeNames);
    public List<EdoReviewLayerDefinition> getReviewLayerDefinitions(String workflowId);
    public List<EdoReviewLayerDefinition> getReviewLayerDefinitions(String nodeName, String voteType, String reviewLetter);
    public List<EdoReviewLayerDefinition> getReviewLayerDefinitions(String workflowId, Set<String> nodeNames);

    public void saveOrUpdate(EdoReviewLayerDefinition reviewLayerDefinition);

    public List<String> getValidReviewLevelNodeNames();
    public List<String> getValidReviewLevelNodeNames(String workflowId);


    //public EdoReviewLayerDefinition getReviewLayerDefinition(String nodeName, BigDecimal routeLevel);
    //public EdoReviewLayerDefinition getReviewLayerDefinition(String nodeName);

    //public BigDecimal getMaxRouteLevel();
    public List<EdoReviewLayerDefinition> getReviewLayerDefinitionsToMax(BigDecimal maxReviewLevel);
    //supplemental auth nodes
    public List<String> getAuthorizedSupplementalNodes(BigDecimal reviewLayerDefinitionId);
    public EdoSuppReviewLayerDefinition getSuppReviewLayerDefinition(BigDecimal reviewLayerDefinitionID);
    public EdoReviewLayerDefinition getReviewLayerDefinitionBySupplementalNode(String suppNodeName);
    public List<String> getDistinctWorkflowIds();
    public List<EdoReviewLayerDefinition> getReviewLayerDefinitionsByWorkflowId(String workflowId);
    public String getLevelQualifierByWorkflowId(String workflowId, String nodeName);
    public String getSuppLevelQualifierByWorkflowId(String workflowId, String nodeName);
    public List<EdoReviewLayerDefinition> getRouteLevelsWithReviewLayers();
}
