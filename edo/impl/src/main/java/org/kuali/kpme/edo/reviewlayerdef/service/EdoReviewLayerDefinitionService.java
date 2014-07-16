package org.kuali.kpme.edo.reviewlayerdef.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.kpme.edo.api.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.reviewlayerdef.EdoSuppReviewLayerDefinition;

public interface EdoReviewLayerDefinitionService {
    // TODO resolve dependency of EdoAdminGroupMembersAction.processMemberRecords on this method
    public EdoReviewLayerDefinition getReviewLayerDefinitionById(String reviewLayerDefinitionId); 
    public EdoReviewLayerDefinition getReviewLayerDefinition(String workflowId, String nodeName);  // IMPL done
    // need to rewrite the admin group forms from the ground up to eliminate this service method
    public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitions();
    public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitions(String workflowId);   // IMPL done
    public List<EdoReviewLayerDefinition> getReviewLayerDefinitionsByWorkflowId(String workflowId);  
    // no alteration needed here; the filtered list of rev layer defs is passed in
    public Map<String, EdoReviewLayerDefinition> buildReviewLevelMap(Collection<EdoReviewLayerDefinition> reviewLayerDefinitions);
    public Map<String, String> buildReviewLevelByRouteMap(Collection<EdoReviewLayerDefinition> reviewLayerDefinitions);
    // used in maintenance doc; keep or toss?
    public List<EdoReviewLayerDefinition> getReviewLayerDefinitions(String nodeName, String voteType, String reviewLetter);
    public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitions(String workflowId, Set<String> nodeNames);  // IMPL done
    public List<String> getValidReviewLevelNodeNames();
    public List<String> getValidReviewLevelNodeNames(String workflowId);  // IMPL done
    // no alteration needed
    public void saveOrUpdate(EdoReviewLayerDefinition reviewLayerDefinition);
    public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitionsToMax(String maxRouteLevel);
    public Map<String,String> getReviewLayerDefinitionDescriptionsByWorkflow(String workflowId);
    public List<EdoReviewLayerDefinition> getRouteLevelsWithReviewLayers();
    public List<String> getDistinctWorkflowIds();
    public String getLevelQualifierByWorkflowId(String workflowId, String nodeName);
    
    // moved to here from EdoVoteRecordService
    public EdoReviewLayerDefinition findFirstNegativeReviewLayerByVote(String edoDossierID);
    
    // TODO: move the following three methods to 
    // no alteration needed; id passed to EdoSuppReviewLayerDefinition module
    //get the supplemental nodes (authorized nodes)
    public EdoSuppReviewLayerDefinition getSuppReviewLayerDefinition(BigDecimal reviewLayerDefinitionId);
    public List<String> getAuthorizedSupplementalNodes(String reviewLayerDefinitionId);
    public String getSuppLevelQualifierByWorkflowId(String workflowId, String nodeName);
    
    
 // commented out by IU
 	// public EdoReviewLayerDefinition getReviewLayerDefinition(String nodeName , BigDecimal routeLevel);  // DONE
 	// public EdoReviewLayerDefinition getReviewLayerDefinition(String nodeName);   // DONE
 	// no usages
     //public List<String> getValidNodes();
 	// public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitions(Set<String> nodeNames);  // DONE
 	// no usages
     //public BigDecimal getMaxRouteLevel();
     // no usages
     //public Map<BigDecimal, EdoReviewLayerDefinition> buildIdMap(Collection<EdoReviewLayerDefinition> reviewLayerDefinitions);
 	// called in supplemental pending status service impl that is not used there
     //public EdoReviewLayerDefinition getReviewLayerDefinitionBySupplementalNode(String suppNodeName);
 	 // no usages of this method found ??
     //public EdoReviewLayerDefinitionBo getReviewLayerDefinition(BigDecimal routeLevel, List<EdoReviewLayerDefinitionBo> reviewLayerDefinitions);
     // no usages of this method found ??
     //public EdoReviewLayerDefinitionBo getReviewLayerDefinition(String nodeName, List<EdoReviewLayerDefinitionBo> reviewLayerDefinitions);
 	// no usages
     //public Map<BigDecimal, EdoReviewLayerDefinitionBo> buildRouteLevelMap(Collection<EdoReviewLayerDefinitionBo> reviewLayerDefinitions);

 	// **************end of commented out by IU *************************//
 	
 	// commeted out by Tango
 	// TODO this is used by EdoSuppReviewLayerDefinition.java. 
     //public EdoReviewLayerDefinitionBo getReviewLayerDefinition(String workflowId, BigDecimal reviewLayerDefinitionId);
 	//public EdoReviewLayerDefinition getReviewLayerDefinition(String workflowId, String nodeName, String routeLevel); 
 	//
 	
}
