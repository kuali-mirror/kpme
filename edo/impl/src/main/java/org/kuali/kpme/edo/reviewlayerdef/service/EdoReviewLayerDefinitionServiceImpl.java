package org.kuali.kpme.edo.reviewlayerdef.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.reviewlayerdef.EdoSuppReviewLayerDefinition;
import org.kuali.kpme.edo.reviewlayerdef.dao.EdoReviewLayerDefinitionDao;

import java.math.BigDecimal;
import java.util.*;

public class EdoReviewLayerDefinitionServiceImpl implements EdoReviewLayerDefinitionService {

    private EdoReviewLayerDefinitionDao edoReviewLayerDefinitionDao;

    public EdoReviewLayerDefinition getReviewLayerDefinition(BigDecimal reviewLayerDefinitionId) {
        return this.edoReviewLayerDefinitionDao.getReviewLayerDefinition(reviewLayerDefinitionId);
    }

    public EdoReviewLayerDefinition getReviewLayerDefinition(String workflowId, BigDecimal reviewLayerDefinitionId) {
        return this.edoReviewLayerDefinitionDao.getReviewLayerDefinition(workflowId, reviewLayerDefinitionId);
    }

    public EdoReviewLayerDefinition getReviewLayerDefinition(String workflowId, String nodeName, BigDecimal routeLevel) {
        return this.edoReviewLayerDefinitionDao.getReviewLayerDefinition(workflowId, nodeName, routeLevel);
    }

    public EdoReviewLayerDefinition getReviewLayerDefinition(String workflowId, String nodeName) {
        return this.edoReviewLayerDefinitionDao.getReviewLayerDefinition(workflowId, nodeName);
    }

    //public EdoReviewLayerDefinition getReviewLayerDefinition(String nodeName) {
    //    return this.edoReviewLayerDefinitionDao.getReviewLayerDefinition(nodeName);
    //}

    public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitions(String workflowId) {
        return this.edoReviewLayerDefinitionDao.getReviewLayerDefinitions(workflowId);
    }

    public EdoReviewLayerDefinition getReviewLayerDefinition(BigDecimal routeLevel, List<EdoReviewLayerDefinition> reviewLayerDefinitions) {
        if (CollectionUtils.isNotEmpty(reviewLayerDefinitions) && routeLevel != null) {
            for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
                if (reviewLayerDefinition.getRouteLevel().equals(routeLevel)) {
                    return reviewLayerDefinition;
                }
            }
        }
        return null;
    }

    public EdoReviewLayerDefinition getReviewLayerDefinition(String nodeName, List<EdoReviewLayerDefinition> reviewLayerDefinitions) {
        if (CollectionUtils.isNotEmpty(reviewLayerDefinitions) && StringUtils.isNotEmpty(nodeName)) {
            for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
                if (reviewLayerDefinition.getNodeName().equals(nodeName)) {
                    return reviewLayerDefinition;
                }
            }
        }
        return null;
    }

    public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitions() {
        return this.edoReviewLayerDefinitionDao.getReviewLayerDefinitions();
    }

    public List<EdoReviewLayerDefinition> getReviewLayerDefinitions(String nodeName, String voteType, String reviewLetter) {
        return this.edoReviewLayerDefinitionDao.getReviewLayerDefinitions(nodeName, voteType, reviewLetter);
    }

    public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitions(Set<String> nodeNames) {
        return this.edoReviewLayerDefinitionDao.getReviewLayerDefinitions(nodeNames);
    }

    public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitions(String workflowId, Set<String> nodeNames) {
        return this.edoReviewLayerDefinitionDao.getReviewLayerDefinitions(workflowId, nodeNames);
    }

    /* public List<String> getValidNodes() {
        List<EdoReviewLayerDefinition> edoReviewLayerDefinitions = this.edoReviewLayerDefinitionDao.getReviewLayerDefinitions();
        List<String> validNodes = new LinkedList<String>();

        for (EdoReviewLayerDefinition edoReviewLayerDefinition : edoReviewLayerDefinitions) {
            validNodes.add(edoReviewLayerDefinition.getNodeName());
        }

        return validNodes;
    }  */

    public List<String> getValidReviewLevelNodeNames() {
        return this.edoReviewLayerDefinitionDao.getValidReviewLevelNodeNames();
    }

    public List<String> getValidReviewLevelNodeNames(String workflowId) {
        return this.edoReviewLayerDefinitionDao.getValidReviewLevelNodeNames(workflowId);
    }

    /* public BigDecimal getMaxRouteLevel() {
        return this.edoReviewLayerDefinitionDao.getMaxRouteLevel();
    }  */

    /* public Map<BigDecimal, EdoReviewLayerDefinition> buildIdMap(Collection<EdoReviewLayerDefinition> reviewLayerDefinitions) {
        Map<BigDecimal, EdoReviewLayerDefinition> idMap = new HashMap<BigDecimal, EdoReviewLayerDefinition>();
        if (CollectionUtils.isNotEmpty(reviewLayerDefinitions)) {
            for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
                idMap.put(reviewLayerDefinition.getReviewLayerDefinitionId(), reviewLayerDefinition);
            }
        }
        return idMap;
    }  */

    public Map<BigDecimal, EdoReviewLayerDefinition> buildReviewLevelMap(Collection<EdoReviewLayerDefinition> reviewLayerDefinitions) {
        Map<BigDecimal, EdoReviewLayerDefinition> levelMap = new HashMap<BigDecimal, EdoReviewLayerDefinition>();
        if (CollectionUtils.isNotEmpty(reviewLayerDefinitions)) {
            for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
                levelMap.put(reviewLayerDefinition.getReviewLevel(), reviewLayerDefinition);
            }
        }
        return levelMap;
    }

    public Map<String, BigDecimal> buildReviewLevelByRouteMap(Collection<EdoReviewLayerDefinition> reviewLayerDefinitions) {
        Map<String, BigDecimal> levelMap = new HashMap<String, BigDecimal>();
        if (CollectionUtils.isNotEmpty(reviewLayerDefinitions)) {
            for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
                levelMap.put(reviewLayerDefinition.getNodeName(), reviewLayerDefinition.getReviewLevel());
            }
        }
        return levelMap;
    }

    public Map<BigDecimal, EdoReviewLayerDefinition> buildRouteLevelMap(Collection<EdoReviewLayerDefinition> reviewLayerDefinitions) {
        Map<BigDecimal, EdoReviewLayerDefinition> levelMap = new HashMap<BigDecimal, EdoReviewLayerDefinition>();
        if (CollectionUtils.isNotEmpty(reviewLayerDefinitions)) {
            for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
                levelMap.put(reviewLayerDefinition.getRouteLevel(), reviewLayerDefinition);
            }
        }
        return levelMap;
    }

    /*public Map<String, String> buildNodeMap(Collection<EdoReviewLayerDefinition> reviewLayerDefinitions) {
        Map<String, String> nodeMap = new HashMap<String, String>();
        if (CollectionUtils.isNotEmpty(reviewLayerDefinitions)) {
            for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
                nodeMap.put(reviewLayerDefinition.getNodeName(), reviewLayerDefinition.getSuppNodeName());
            }
        }
        return nodeMap;
    }*/
    public List<String> getAuthorizedSupplementalNodes(BigDecimal reviewLayerDefinitionId) {
    
    	return this.edoReviewLayerDefinitionDao.getAuthorizedSupplementalNodes(reviewLayerDefinitionId);
    	
    	
    }
    public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitionsToMax(BigDecimal maxRouteLevel) {

        return this.edoReviewLayerDefinitionDao.getReviewLayerDefinitionsToMax(maxRouteLevel);
    }

    public void setEdoReviewLayerDefinitionDao(EdoReviewLayerDefinitionDao edoReviewLayerDefinitionDao) {
        this.edoReviewLayerDefinitionDao = edoReviewLayerDefinitionDao;
    }

    public void saveOrUpdate(EdoReviewLayerDefinition reviewLayerDefinition) {
        this.edoReviewLayerDefinitionDao.saveOrUpdate(reviewLayerDefinition);
    }
    public EdoSuppReviewLayerDefinition getSuppReviewLayerDefinition(BigDecimal reviewLayerDefinitionId) {
    	return this.edoReviewLayerDefinitionDao.getSuppReviewLayerDefinition(reviewLayerDefinitionId);
    }
    /* public EdoReviewLayerDefinition getReviewLayerDefinitionBySupplementalNode(String suppNodeName) {
        return this.edoReviewLayerDefinitionDao.getReviewLayerDefinitionBySupplementalNode(suppNodeName);
    }  */

    public List<String> getDistinctWorkflowIds() {
        return this.edoReviewLayerDefinitionDao.getDistinctWorkflowIds();
    }

    public List<EdoReviewLayerDefinition> getReviewLayerDefinitionsByWorkflowId(String workflowId) {
        return this.edoReviewLayerDefinitionDao.getReviewLayerDefinitionsByWorkflowId(workflowId);
    }

    public String getLevelQualifierByWorkflowId(String workflowId, String nodeName) {
        return this.edoReviewLayerDefinitionDao.getLevelQualifierByWorkflowId(workflowId, nodeName);
    }

    public String getSuppLevelQualifierByWorkflowId(String workflowId, String nodeName) {
        return this.edoReviewLayerDefinitionDao.getSuppLevelQualifierByWorkflowId(workflowId, nodeName);
    }

    public Map<BigDecimal,String> getReviewLayerDefinitionDescriptionsByWorkflow(String workflowId) {
        //List<String> levels = new LinkedList<String>();
        Map<BigDecimal,String> levelMap = new TreeMap<BigDecimal, String>();

        List<EdoReviewLayerDefinition> rldList = getReviewLayerDefinitionsByWorkflowId(workflowId);

        for (EdoReviewLayerDefinition rld : rldList) {
            levelMap.put(rld.getReviewLayerDefinitionId(),rld.getDescription());
            //levels.add(rld.getDescription());
        }
        return levelMap;
    }
    
    @Override 
    public List<EdoReviewLayerDefinition> getRouteLevelsWithReviewLayers() {
    	return edoReviewLayerDefinitionDao.getRouteLevelsWithReviewLayers();
    }
}
