package org.kuali.kpme.edo.reviewlayerdef.dao;

import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinitionBo;
import org.kuali.kpme.edo.reviewlayerdef.EdoSuppReviewLayerDefinitionBo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface EdoReviewLayerDefinitionDao {
	// commented out by IU
	//public EdoReviewLayerDefinition getReviewLayerDefinition(String nodeName, BigDecimal routeLevel);
    //public EdoReviewLayerDefinition getReviewLayerDefinition(String nodeName);
    //public BigDecimal getMaxRouteLevel();
	// end of commented out by IU
	
	// commented out by Tango
	//public EdoReviewLayerDefinitionBo getReviewLayerDefinition(String workflowId, BigDecimal reviewLayerDefinitionId);
	//public EdoReviewLayerDefinitionBo getReviewLayerDefinition(String workflowId, String nodeName, String routeLevel);
	// ********************* end of commented out by Tango ***********************
	
    public EdoReviewLayerDefinitionBo getReviewLayerDefinitionById(String reviewLayerDefinitionId);
    public EdoReviewLayerDefinitionBo getReviewLayerDefinition(String workflowId, String nodeName);
    public List<EdoReviewLayerDefinitionBo> getReviewLayerDefinitions();
    
    public List<EdoReviewLayerDefinitionBo> getReviewLayerDefinitions(Set<String> nodeNames);
    public List<EdoReviewLayerDefinitionBo> getReviewLayerDefinitions(String workflowId);
    public List<EdoReviewLayerDefinitionBo> getReviewLayerDefinitions(String nodeName, String voteType, String reviewLetter);
    public List<EdoReviewLayerDefinitionBo> getReviewLayerDefinitions(String workflowId, Set<String> nodeNames);
    public void saveOrUpdate(EdoReviewLayerDefinitionBo reviewLayerDefinition);
    public List<String> getValidReviewLevelNodeNames();
    public List<String> getValidReviewLevelNodeNames(String workflowId);


    
    public List<EdoReviewLayerDefinitionBo> getReviewLayerDefinitionsToMax(String maxReviewLevel);
    //supplemental auth nodes
    public EdoReviewLayerDefinitionBo getReviewLayerDefinitionBySupplementalNode(String suppNodeName);
    public List<String> getDistinctWorkflowIds();
    public List<EdoReviewLayerDefinitionBo> getReviewLayerDefinitionsByWorkflowId(String workflowId);
    public String getLevelQualifierByWorkflowId(String workflowId, String nodeName);
    public List<EdoReviewLayerDefinitionBo> getRouteLevelsWithReviewLayers();
    
    // KPME-3711 Moved these to EdoSuppReviewLayerDefinition
    /*
    public List<String> getAuthorizedSupplementalNodes(String reviewLayerDefinitionId);
    public EdoSuppReviewLayerDefinitionBo getSuppReviewLayerDefinition(String reviewLayerDefinitionId);
    public String getSuppLevelQualifierByWorkflowId(String workflowId, String nodeName); */
}
