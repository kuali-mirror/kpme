package org.kuali.kpme.edo.reviewlayerdef.service;

import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.reviewlayerdef.EdoSuppReviewLayerDefinition;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EdoReviewLayerDefinitionService {

    // TODO resolve dependency of EdoAdminGroupMembersAction.processMemberRecords on this method
    public EdoReviewLayerDefinition getReviewLayerDefinition(BigDecimal reviewLayerDefinitionId);  // ALMOST DONE
    public EdoReviewLayerDefinition getReviewLayerDefinition(String workflowId, BigDecimal reviewLayerDefinitionId);  // IMPL done

    // public EdoReviewLayerDefinition getReviewLayerDefinition(String nodeName , BigDecimal routeLevel);  // DONE
    public EdoReviewLayerDefinition getReviewLayerDefinition(String workflowId, String nodeName, BigDecimal routeLevel);  // IMPL done

    // public EdoReviewLayerDefinition getReviewLayerDefinition(String nodeName);   // DONE
    public EdoReviewLayerDefinition getReviewLayerDefinition(String workflowId, String nodeName);  // IMPL done

    // no usages of this method found ??
    public EdoReviewLayerDefinition getReviewLayerDefinition(BigDecimal routeLevel, List<EdoReviewLayerDefinition> reviewLayerDefinitions);

    // no usages of this method found ??
    public EdoReviewLayerDefinition getReviewLayerDefinition(String nodeName, List<EdoReviewLayerDefinition> reviewLayerDefinitions);

    // need to rewrite the admin group forms from the ground up to eliminate this service method
    public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitions();

    public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitions(String workflowId);   // IMPL done

    // used in maintenance doc; keep or toss?
    public List<EdoReviewLayerDefinition> getReviewLayerDefinitions(String nodeName, String voteType, String reviewLetter);

    // public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitions(Set<String> nodeNames);  // DONE
    public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitions(String workflowId, Set<String> nodeNames);  // IMPL done

    // no usages
    //public List<String> getValidNodes();

    public List<String> getValidReviewLevelNodeNames();
    public List<String> getValidReviewLevelNodeNames(String workflowId);  // IMPL done

    // no usages
    //public BigDecimal getMaxRouteLevel();

    // no usages
    //public Map<BigDecimal, EdoReviewLayerDefinition> buildIdMap(Collection<EdoReviewLayerDefinition> reviewLayerDefinitions);

    // no alteration needed here; the filtered list of rev layer defs is passed in
    public Map<BigDecimal, EdoReviewLayerDefinition> buildReviewLevelMap(Collection<EdoReviewLayerDefinition> reviewLayerDefinitions);
    public Map<String, BigDecimal> buildReviewLevelByRouteMap(Collection<EdoReviewLayerDefinition> reviewLayerDefinitions);

    // no usages
    public Map<BigDecimal, EdoReviewLayerDefinition> buildRouteLevelMap(Collection<EdoReviewLayerDefinition> reviewLayerDefinitions);

    // no alteration needed
    public void saveOrUpdate(EdoReviewLayerDefinition reviewLayerDefinition);

    public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitionsToMax(BigDecimal maxRouteLevel);

    // no alteration needed; id passed in
    //get the supplemental nodes (authorized nodes)
    public List<String> getAuthorizedSupplementalNodes(BigDecimal reviewLayerDefinitionId);
    public EdoSuppReviewLayerDefinition getSuppReviewLayerDefinition(BigDecimal reviewLayerDefinitionId);

    // called in supplemental pending status service impl that is not used there
    //public EdoReviewLayerDefinition getReviewLayerDefinitionBySupplementalNode(String suppNodeName);

    public List<String> getDistinctWorkflowIds();

    public List<EdoReviewLayerDefinition> getReviewLayerDefinitionsByWorkflowId(String workflowId);
    public String getLevelQualifierByWorkflowId(String workflowId, String nodeName);
    public String getSuppLevelQualifierByWorkflowId(String workflowId, String nodeName);
    public Map<BigDecimal,String> getReviewLayerDefinitionDescriptionsByWorkflow(String workflowId);

    public List<EdoReviewLayerDefinition> getRouteLevelsWithReviewLayers();
}
