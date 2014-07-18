package org.kuali.kpme.edo.reviewlayerdef.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.kpme.edo.api.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.api.reviewlayerdef.EdoSuppReviewLayerDefinition;
import org.kuali.kpme.edo.api.vote.EdoVoteRecord;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinitionBo;
import org.kuali.kpme.edo.reviewlayerdef.EdoSuppReviewLayerDefinitionBo;
import org.kuali.kpme.edo.reviewlayerdef.dao.EdoReviewLayerDefinitionDao;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class EdoReviewLayerDefinitionServiceImpl implements EdoReviewLayerDefinitionService {

    private EdoReviewLayerDefinitionDao edoReviewLayerDefinitionDao;

    public EdoReviewLayerDefinition getReviewLayerDefinitionById(String edoReviewLayerDefinitionId) {
    	EdoReviewLayerDefinitionBo edoReviewLayerDefinitionBo = edoReviewLayerDefinitionDao.getReviewLayerDefinitionById(edoReviewLayerDefinitionId);
    	
    	if ( edoReviewLayerDefinitionBo == null){
    		
    		return null;
    	}
    	
    	EdoReviewLayerDefinition.Builder builder = EdoReviewLayerDefinition.Builder.create(edoReviewLayerDefinitionBo);
    	
    	return builder.build();
    }

    public EdoReviewLayerDefinition getReviewLayerDefinition(String workflowId, String nodeName) {
    	EdoReviewLayerDefinitionBo edoReviewLayerDefinitionBo = edoReviewLayerDefinitionDao.getReviewLayerDefinition(workflowId, nodeName);
    	
    	if ( edoReviewLayerDefinitionBo == null){
    		return null;
    	}
    	
    	EdoReviewLayerDefinition.Builder builder = EdoReviewLayerDefinition.Builder.create(edoReviewLayerDefinitionBo);
    	
    	return builder.build();
    }

    public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitions() {
    	
    	return ModelObjectUtils.transform(edoReviewLayerDefinitionDao.getReviewLayerDefinitions(), EdoReviewLayerDefinitionBo.toImmutable); 
    }
    
    public List<EdoReviewLayerDefinition> getReviewLayerDefinitionsByWorkflowId(String workflowId) {
    	
    	return ModelObjectUtils.transform(edoReviewLayerDefinitionDao.getReviewLayerDefinitionsByWorkflowId(workflowId), EdoReviewLayerDefinitionBo.toImmutable); 
    }
    
    public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitions(String workflowId) {
    	
    	return ModelObjectUtils.transform(edoReviewLayerDefinitionDao.getReviewLayerDefinitions(workflowId), EdoReviewLayerDefinitionBo.toImmutable);
    }
 
    // for lookuphelper
    public List<EdoReviewLayerDefinition> getReviewLayerDefinitions(String nodeName, String voteType, String reviewLetter) {
    	
    	return ModelObjectUtils.transform(edoReviewLayerDefinitionDao.getReviewLayerDefinitions(nodeName, voteType, reviewLetter), EdoReviewLayerDefinitionBo.toImmutable);
    }
    
    public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitions(String workflowId, Set<String> nodeNames) {
    	
    	return ModelObjectUtils.transform(edoReviewLayerDefinitionDao.getReviewLayerDefinitions(workflowId, nodeNames), EdoReviewLayerDefinitionBo.toImmutable);
    }
    
    public Collection<EdoReviewLayerDefinition> getReviewLayerDefinitionsToMax(String maxRouteLevel) {
    	
    	return ModelObjectUtils.transform(edoReviewLayerDefinitionDao.getReviewLayerDefinitionsToMax(maxRouteLevel), EdoReviewLayerDefinitionBo.toImmutable);
    }
    
    @Override 
    public List<EdoReviewLayerDefinition> getRouteLevelsWithReviewLayers() {
    	
    	return ModelObjectUtils.transform(edoReviewLayerDefinitionDao.getRouteLevelsWithReviewLayers(), EdoReviewLayerDefinitionBo.toImmutable);
    }

    public Collection<EdoReviewLayerDefinitionBo> getReviewLayerDefinitions(Set<String> nodeNames) {
        return this.edoReviewLayerDefinitionDao.getReviewLayerDefinitions(nodeNames);
    }

    

    public List<String> getValidReviewLevelNodeNames() {
        return this.edoReviewLayerDefinitionDao.getValidReviewLevelNodeNames();
    }

    public List<String> getValidReviewLevelNodeNames(String workflowId) {
        return this.edoReviewLayerDefinitionDao.getValidReviewLevelNodeNames(workflowId);
    }

    public Map<String, EdoReviewLayerDefinition> buildReviewLevelMap(Collection<EdoReviewLayerDefinition> reviewLayerDefinitions) {
        Map<String, EdoReviewLayerDefinition> levelMap = new HashMap<String, EdoReviewLayerDefinition>();
        
        if (CollectionUtils.isNotEmpty(reviewLayerDefinitions)) {
            for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
                levelMap.put(reviewLayerDefinition.getReviewLevel(), reviewLayerDefinition);
            }
        }
        return levelMap;
    }

    public Map<String, String> buildReviewLevelByRouteMap(Collection<EdoReviewLayerDefinition> reviewLayerDefinitions) {
        Map<String, String> levelMap = new HashMap<String, String>();
        if (CollectionUtils.isNotEmpty(reviewLayerDefinitions)) {
            for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
                levelMap.put(reviewLayerDefinition.getNodeName(), reviewLayerDefinition.getReviewLevel());
            }
        }
        return levelMap;
    }

    public List<String> getAuthorizedSupplementalNodes(String reviewLayerDefinitionId) {
    
    	return this.edoReviewLayerDefinitionDao.getAuthorizedSupplementalNodes(reviewLayerDefinitionId);	
    }
    

    public void setEdoReviewLayerDefinitionDao(EdoReviewLayerDefinitionDao edoReviewLayerDefinitionDao) {
        this.edoReviewLayerDefinitionDao = edoReviewLayerDefinitionDao;
    }

    public void saveOrUpdate(EdoReviewLayerDefinition reviewLayerDefinition) {
    	
    	EdoReviewLayerDefinitionBo bo = EdoReviewLayerDefinitionBo.from(reviewLayerDefinition);
    	
        this.edoReviewLayerDefinitionDao.saveOrUpdate(bo);
    }
    
    public EdoSuppReviewLayerDefinition getSuppReviewLayerDefinition(String reviewLayerDefinitionId) {
    	return EdoSuppReviewLayerDefinitionBo.to(edoReviewLayerDefinitionDao.getSuppReviewLayerDefinition(reviewLayerDefinitionId));
    }
   
    public List<String> getDistinctWorkflowIds() {
        return this.edoReviewLayerDefinitionDao.getDistinctWorkflowIds();
    }

    

    public String getLevelQualifierByWorkflowId(String workflowId, String nodeName) {
        return this.edoReviewLayerDefinitionDao.getLevelQualifierByWorkflowId(workflowId, nodeName);
    }

    public String getSuppLevelQualifierByWorkflowId(String workflowId, String nodeName) {
        return this.edoReviewLayerDefinitionDao.getSuppLevelQualifierByWorkflowId(workflowId, nodeName);
    }

    public Map<String,String> getReviewLayerDefinitionDescriptionsByWorkflow(String workflowId) {
        //List<String> levels = new LinkedList<String>();
        Map<String,String> levelMap = new TreeMap<String, String>();

        List<EdoReviewLayerDefinition> rldList = getReviewLayerDefinitionsByWorkflowId(workflowId);

        for (EdoReviewLayerDefinition rld : rldList) {
            levelMap.put(rld.getEdoReviewLayerDefinitionId(), rld.getDescription());
            //levels.add(rld.getDescription());
        }
        return levelMap;
    }
    
    public EdoReviewLayerDefinition findFirstNegativeReviewLayerByVote(String edoDossierId) {
        EdoReviewLayerDefinition rld = null;
        String workflowId = EdoServiceLocator.getEdoDossierService().getEdoDossierById(edoDossierId).getWorkflowId();

        Collection<EdoReviewLayerDefinition> reviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(workflowId);
        List<EdoReviewLayerDefinition> voteRecordLayerDefinitions = new LinkedList<EdoReviewLayerDefinition>();

        for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
            if (!reviewLayerDefinition.getVoteType().equals(EdoConstants.VOTE_TYPE_NONE)) {
                voteRecordLayerDefinitions.add(reviewLayerDefinition);
            }
        }
        for (EdoReviewLayerDefinition voteRLD : voteRecordLayerDefinitions) {
            EdoVoteRecord voteRecord = EdoServiceLocator.getEdoVoteRecordService().getVoteRecordMostCurrentRound(edoDossierId, voteRLD.getEdoReviewLayerDefinitionId());
            if (EdoServiceLocator.getEdoVoteRecordService().isNegativeVote(voteRecord)) {
                if (rld == null) {
                    rld = voteRLD;
                    continue;
                }
                if (voteRLD.getReviewLevel().compareTo(rld.getReviewLevel()) == -1 ) {
                    rld = voteRLD;
                }
            }
        }
        return rld;
    }
    

    /*
    public EdoReviewLayerDefinitionBo getReviewLayerDefinition(String workflowId, BigDecimal reviewLayerDefinitionId) {
        return this.edoReviewLayerDefinitionDao.getReviewLayerDefinition(workflowId, reviewLayerDefinitionId);
    }*/

    /* no use
    public EdoReviewLayerDefinition getReviewLayerDefinition(String workflowId, String nodeName, String routeLevel) {
    	EdoReviewLayerDefinitionBo edoReviewLayerDefinitionBo = edoReviewLayerDefinitionDao.getReviewLayerDefinition(workflowId, nodeName, routeLevel);
    	
    	if ( edoReviewLayerDefinitionBo == null){
    		return null;
    	}
    	
    	EdoReviewLayerDefinition.Builder builder = EdoReviewLayerDefinition.Builder.create(edoReviewLayerDefinitionBo);
    	
    	return builder.build();
    }*/


    /* no usages
    public Map<BigDecimal, EdoReviewLayerDefinitionBo> buildRouteLevelMap(Collection<EdoReviewLayerDefinitionBo> reviewLayerDefinitions) {
        Map<BigDecimal, EdoReviewLayerDefinitionBo> levelMap = new HashMap<BigDecimal, EdoReviewLayerDefinitionBo>();
        if (CollectionUtils.isNotEmpty(reviewLayerDefinitions)) {
            for (EdoReviewLayerDefinitionBo reviewLayerDefinition : reviewLayerDefinitions) {
                levelMap.put(new BigDecimal(reviewLayerDefinition.getRouteLevel()), reviewLayerDefinition);
            }
        }
        return levelMap;
    }*/

    /*public Map<String, String> buildNodeMap(Collection<EdoReviewLayerDefinition> reviewLayerDefinitions) {
        Map<String, String> nodeMap = new HashMap<String, String>();
        if (CollectionUtils.isNotEmpty(reviewLayerDefinitions)) {
            for (EdoReviewLayerDefinition reviewLayerDefinition : reviewLayerDefinitions) {
                nodeMap.put(reviewLayerDefinition.getNodeName(), reviewLayerDefinition.getSuppNodeName());
            }
        }
        return nodeMap;
    }*/
    
    /* public EdoReviewLayerDefinition getReviewLayerDefinitionBySupplementalNode(String suppNodeName) {
    	return this.edoReviewLayerDefinitionDao.getReviewLayerDefinitionBySupplementalNode(suppNodeName);
	}  */


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


    /* public List<String> getValidNodes() {
        List<EdoReviewLayerDefinition> edoReviewLayerDefinitions = this.edoReviewLayerDefinitionDao.getReviewLayerDefinitions();
        List<String> validNodes = new LinkedList<String>();

        for (EdoReviewLayerDefinition edoReviewLayerDefinition : edoReviewLayerDefinitions) {
            validNodes.add(edoReviewLayerDefinition.getNodeName());
        }

        return validNodes;
    }  */

    /*no usages of this method found
    public EdoReviewLayerDefinitionBo getReviewLayerDefinition(BigDecimal routeLevel, List<EdoReviewLayerDefinitionBo> reviewLayerDefinitions) {
        if (CollectionUtils.isNotEmpty(reviewLayerDefinitions) && routeLevel != null) {
            for (EdoReviewLayerDefinitionBo reviewLayerDefinition : reviewLayerDefinitions) {
                if (reviewLayerDefinition.getRouteLevel().equals(routeLevel)) {
                    return reviewLayerDefinition;
                }
            }
        }
        return null;
    }*/

    /*no usages of this method found
    public EdoReviewLayerDefinitionBo getReviewLayerDefinition(String nodeName, List<EdoReviewLayerDefinitionBo> reviewLayerDefinitions) {
        if (CollectionUtils.isNotEmpty(reviewLayerDefinitions) && StringUtils.isNotEmpty(nodeName)) {
            for (EdoReviewLayerDefinitionBo reviewLayerDefinition : reviewLayerDefinitions) {
                if (reviewLayerDefinition.getNodeName().equals(nodeName)) {
                    return reviewLayerDefinition;
                }
            }
        }
        return null;
    }*/


    //public EdoReviewLayerDefinition getReviewLayerDefinition(String nodeName) {
    //    return this.edoReviewLayerDefinitionDao.getReviewLayerDefinition(nodeName);
    //}

}
