package org.kuali.kpme.edo.reviewlayerdef.dao;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.kpme.edo.reports.EdoPromotionAndTenureReport;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinitionBo;
import org.kuali.kpme.edo.reviewlayerdef.EdoSuppReviewLayerDefinitionBo;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoPropertyConstants;
import org.kuali.kpme.edo.util.EdoPropertyConstants.EdoReviewLayerDefinitionFields;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.kuali.rice.krad.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.*;

public class EdoReviewLayerDefinitionDaoImpl extends PlatformAwareDaoBaseOjb implements EdoReviewLayerDefinitionDao {

    public EdoReviewLayerDefinitionBo getReviewLayerDefinitionById(String reviewLayerDefinitionId) {
        Criteria criteria = new Criteria();
        
        criteria.addEqualTo("edoReviewLayerDefinitionId", reviewLayerDefinitionId);

        Query query = QueryFactory.newQuery(EdoReviewLayerDefinitionBo.class, criteria);
        return (EdoReviewLayerDefinitionBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }


    public EdoReviewLayerDefinitionBo getReviewLayerDefinition(String workflowId, String nodeName) {
        Criteria criteria = new Criteria();

        if (StringUtils.isNotBlank(nodeName)) {
            criteria.addSql("upper(node_name) like '" + nodeName.toUpperCase() + "'");
            //criteria.addLike("nodeName", nodeName);
        	//criteria.addEqualTo("nodeName", nodeName);
        }
        criteria.addEqualTo("workflowId", workflowId);
        Query query = QueryFactory.newQuery(EdoReviewLayerDefinitionBo.class, criteria);
        
        return (EdoReviewLayerDefinitionBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    public List<EdoReviewLayerDefinitionBo> getReviewLayerDefinitions() {
        List<EdoReviewLayerDefinitionBo> results = new LinkedList<EdoReviewLayerDefinitionBo>();
        Criteria criteria = new Criteria();

        Query query = QueryFactory.newQuery(EdoReviewLayerDefinitionBo.class, criteria);
        results.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

    public List<EdoReviewLayerDefinitionBo> getReviewLayerDefinitions(String workflowId) {
        List<EdoReviewLayerDefinitionBo> results = new LinkedList<EdoReviewLayerDefinitionBo>();
        Criteria criteria = new Criteria();
        criteria.addEqualTo("workflowId", workflowId);

        Query query = QueryFactory.newQuery(EdoReviewLayerDefinitionBo.class, criteria);
        results.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

    public List<EdoReviewLayerDefinitionBo> getReviewLayerDefinitions(String nodeName, String voteType, String reviewLetter) {
        List<EdoReviewLayerDefinitionBo> results = new LinkedList<EdoReviewLayerDefinitionBo>();

        Criteria criteria = new Criteria();

        if (StringUtils.isNotBlank(nodeName)) {
            criteria.addLike("nodeName", nodeName);
        }

        if (StringUtils.isNotBlank(voteType)) {
            criteria.addLike("voteType", voteType);
        }

        if (StringUtils.isNotBlank(reviewLetter)) {
            criteria.addEqualTo("reviewLetter", reviewLetter);
        }

        Query query = QueryFactory.newQuery(EdoReviewLayerDefinitionBo.class, criteria);
        results.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

    public List<EdoReviewLayerDefinitionBo> getReviewLayerDefinitions(Set<String> nodeNames) {
        List<EdoReviewLayerDefinitionBo> results = new LinkedList<EdoReviewLayerDefinitionBo>();

        if (CollectionUtils.isNotEmpty(nodeNames)) {
            Criteria criteria = new Criteria();
            criteria.addIn("nodeName", nodeNames);

            Query query = QueryFactory.newQuery(EdoReviewLayerDefinitionBo.class, criteria);
            results.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));
        }

        return results;
    }

    public List<EdoReviewLayerDefinitionBo> getReviewLayerDefinitions(String workflowId, Set<String> nodeNames) {
        List<EdoReviewLayerDefinitionBo> results = new LinkedList<EdoReviewLayerDefinitionBo>();

        if (CollectionUtils.isNotEmpty(nodeNames)) {
            Criteria criteria = new Criteria();
            criteria.addIn("nodeName", nodeNames);
            criteria.addEqualTo("workflowId", workflowId);

            Query query = QueryFactory.newQuery(EdoReviewLayerDefinitionBo.class, criteria);
            results.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));
        }

        return results;
    }

    public void saveOrUpdate(EdoReviewLayerDefinitionBo reviewLayerDefinition) {
        this.getPersistenceBrokerTemplate().store(reviewLayerDefinition);
    }


    public List<EdoReviewLayerDefinitionBo> getReviewLayerDefinitionsToMax(String maxReviewLevel) {
        List<EdoReviewLayerDefinitionBo> results = new LinkedList<EdoReviewLayerDefinitionBo>();

        Criteria criteria = new Criteria();
        criteria.addLessThan("reviewLevel", maxReviewLevel);

        Query query = QueryFactory.newQuery(EdoReviewLayerDefinitionBo.class, criteria);
        
        Collection collection = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        results.addAll(collection); 
        

        return results;
    }

    public List<String> getValidReviewLevelNodeNames() {
        List<String> nodeNames = new LinkedList<String>();
        List<EdoReviewLayerDefinitionBo> results = new LinkedList<EdoReviewLayerDefinitionBo>();

        Criteria criteria = new Criteria();
        //criteria.addColumnNotNull("review_level");

        Query query = QueryFactory.newQuery(EdoReviewLayerDefinitionBo.class, criteria);
        Collection collection = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (CollectionUtils.isNotEmpty(collection)) {
            results.addAll(collection);
            for (EdoReviewLayerDefinitionBo edoReviewLayerDefinition : results) {
                nodeNames.add(edoReviewLayerDefinition.getNodeName());
            }
        }

        return nodeNames;
    }

    public List<String> getValidReviewLevelNodeNames(String workflowId) {
        List<String> nodeNames = new LinkedList<String>();
        List<EdoReviewLayerDefinitionBo> results = new LinkedList<EdoReviewLayerDefinitionBo>();

        Criteria criteria = new Criteria();
        //criteria.addColumnNotNull("review_level");
        criteria.addEqualTo("workflowId", workflowId);

        Query query = QueryFactory.newQuery(EdoReviewLayerDefinitionBo.class, criteria);
        Collection collection = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (CollectionUtils.isNotEmpty(collection)) {
            results.addAll(collection);
            for (EdoReviewLayerDefinitionBo edoReviewLayerDefinition : results) {
                nodeNames.add(edoReviewLayerDefinition.getNodeName());
            }
        }

        return nodeNames;
    }

    // KPME-3711 Moved these to EdoSuppReviewLayerDefinition
    /*
    public List<String> getAuthorizedSupplementalNodes(String edoReviewLayerDefinitionId){
    	List<String> authSuppNodes = new ArrayList<String>();
        List<EdoSuppReviewLayerDefinitionBo> results = new LinkedList<EdoSuppReviewLayerDefinitionBo>();

        Criteria criteria = new Criteria();
        criteria.addEqualTo("edoReviewLayerDefinitionId", edoReviewLayerDefinitionId);

        Query query = QueryFactory.newQuery(EdoSuppReviewLayerDefinitionBo.class, criteria);

        Collection collection = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (CollectionUtils.isNotEmpty(collection)) {
            results.addAll(collection);
            for (EdoSuppReviewLayerDefinitionBo edoSuppReviewLayerDefinition : results) {
                authSuppNodes.add(edoSuppReviewLayerDefinition.getSuppNodeName());
            }
        }

        return authSuppNodes;
    }

    public EdoSuppReviewLayerDefinitionBo getSuppReviewLayerDefinition(String reviewLayerDefinitionId) {
        Criteria criteria = new Criteria();

        criteria.addEqualTo("edoeviewLayerDefinitionId", reviewLayerDefinitionId);

        Query query = QueryFactory.newQuery(EdoSuppReviewLayerDefinitionBo.class, criteria);
        return (EdoSuppReviewLayerDefinitionBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    } 
    
     public String getSuppLevelQualifierByWorkflowId(String workflowId, String nodeName) {
        String qualifier = null;

        Criteria criteria = new Criteria();
        criteria.addEqualTo(EdoPropertyConstants.EdoSuppReviewLayerDefinitionFields.WORKFLOW_ID, workflowId);
        criteria.addEqualTo(EdoPropertyConstants.EdoSuppReviewLayerDefinitionFields.NODE_NAME, nodeName);

        ReportQueryByCriteria query = QueryFactory.newReportQuery(EdoSuppReviewLayerDefinitionBo.class, criteria);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        for (Object obj : c ) {
            EdoSuppReviewLayerDefinitionBo suppReviewLayer = (EdoSuppReviewLayerDefinitionBo)obj;
            qualifier = suppReviewLayer.getWorkflowQualifier();
        }

        return qualifier;
    } */

    public EdoReviewLayerDefinitionBo getReviewLayerDefinitionBySupplementalNode(String suppNodeName) {
        Criteria criteria = new Criteria();

        criteria.addEqualTo("suppNodeName", suppNodeName);

        Query query = QueryFactory.newQuery(EdoSuppReviewLayerDefinitionBo.class, criteria);
        EdoSuppReviewLayerDefinitionBo obj = (EdoSuppReviewLayerDefinitionBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        criteria = new Criteria();
        criteria.addEqualTo("edoeviewLayerDefinitionId", obj.getEdoReviewLayerDefinitionId());
        Query query2 = QueryFactory.newQuery(EdoReviewLayerDefinitionBo.class, criteria);
        return (EdoReviewLayerDefinitionBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query2);
    }

    @Override
    public List<String> getDistinctWorkflowIds() {
        List<String> workflowIds = new ArrayList<String>();
        
        Criteria criteria = new Criteria();
        criteria.addNotEqualTo(EdoReviewLayerDefinitionFields.VOTE_TYPE, EdoConstants.VOTE_TYPE_NONE);

		String[] attributes = new String[] {EdoReviewLayerDefinitionFields.WORKFLOW_ID};				
		ReportQueryByCriteria query = QueryFactory.newReportQuery(EdoPromotionAndTenureReport.class, attributes, criteria, true);
		query.addOrderByAscending(EdoReviewLayerDefinitionFields.WORKFLOW_ID);
		
        Iterator results = getPersistenceBrokerTemplate().getReportQueryIteratorByQuery(query);
        while (results.hasNext()) {
        	Object[] row = (Object[])results.next();
        	workflowIds.add((String)row[0]);
        }        
        
        return workflowIds;
    }

    public List<EdoReviewLayerDefinitionBo> getReviewLayerDefinitionsByWorkflowId(String workflowId) {
        List<EdoReviewLayerDefinitionBo> results = new LinkedList<EdoReviewLayerDefinitionBo>();

        Criteria criteria = new Criteria();
        criteria.addEqualTo(EdoReviewLayerDefinitionFields.WORKFLOW_ID, workflowId);
        criteria.addNotEqualTo(EdoReviewLayerDefinitionFields.VOTE_TYPE, EdoConstants.VOTE_TYPE_NONE);

        ReportQueryByCriteria query = QueryFactory.newReportQuery(EdoReviewLayerDefinitionBo.class, criteria);
        query.addOrderByAscending(EdoReviewLayerDefinitionFields.ROUTE_LEVEL);

        Collection collection = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        results.addAll(collection);

        return results;
    }

    public String getLevelQualifierByWorkflowId(String workflowId, String nodeName) {
        String qualifier = null;

        Criteria criteria = new Criteria();
        criteria.addEqualTo(EdoReviewLayerDefinitionFields.WORKFLOW_ID, workflowId);
        criteria.addEqualTo(EdoReviewLayerDefinitionFields.NODE_NAME, nodeName);
        //criteria.addNotEqualTo(EdoReviewLayerDefinitionFields.VOTE_TYPE, EdoConstants.VOTE_TYPE_NONE);

        ReportQueryByCriteria query = QueryFactory.newReportQuery(EdoReviewLayerDefinitionBo.class, criteria);
        query.addOrderByAscending(EdoReviewLayerDefinitionFields.ROUTE_LEVEL);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        for (Object obj : c ) {
            EdoReviewLayerDefinitionBo reviewLayer = (EdoReviewLayerDefinitionBo)obj;
            qualifier = reviewLayer.getWorkflowQualifier();
        }

        return qualifier;
    }

    public List<EdoReviewLayerDefinitionBo> getRouteLevelsWithReviewLayers() {
    	List<EdoReviewLayerDefinitionBo> results = new ArrayList<EdoReviewLayerDefinitionBo>();
    	
    	Criteria criteria = new Criteria();
    	criteria.addNotNull(EdoReviewLayerDefinitionFields.REVIEW_LEVEL);
    	
    	ReportQueryByCriteria query = QueryFactory.newReportQuery(EdoReviewLayerDefinitionBo.class, criteria);
    	query.addOrderByAscending(EdoReviewLayerDefinitionFields.ROUTE_LEVEL);
    	
    	Collection c = getPersistenceBrokerTemplate().getCollectionByQuery(query);    	
    	for(Object obj : c) {
    		EdoReviewLayerDefinitionBo reviewLayer = (EdoReviewLayerDefinitionBo)obj;
    		results.add(reviewLayer);
    	}
    	
    	return results;
    }
    

    /*
    public EdoReviewLayerDefinitionBo getReviewLayerDefinition(String workflowId, BigDecimal reviewLayerDefinitionID) {
        Criteria criteria = new Criteria();

        criteria.addEqualTo("edoReviewLayerDefinitionId", reviewLayerDefinitionID);
        criteria.addEqualTo("workflowId", workflowId);

        Query query = QueryFactory.newQuery(EdoReviewLayerDefinitionBo.class, criteria);
        return (EdoReviewLayerDefinitionBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }*/

    /*
    public EdoReviewLayerDefinitionBo getReviewLayerDefinition(String workflowId, String nodeName, String routeLevel) {
        Criteria criteria = new Criteria();

        if (StringUtils.isNotBlank(nodeName)) {
            criteria.addLike("nodeName", nodeName);
        }
        if (ObjectUtils.isNotNull(routeLevel)) {
            criteria.addEqualTo("routeLevel", routeLevel);
        }
        if (ObjectUtils.isNotNull(workflowId)) {
            criteria.addEqualTo("workflowId", workflowId);
        }

        return (EdoReviewLayerDefinitionBo) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(EdoReviewLayerDefinitionBo.class, criteria));
    }*/
    

    /* public EdoReviewLayerDefinition getReviewLayerDefinition(String nodeName, BigDecimal routeLevel) {
        Criteria criteria = new Criteria();

        if (StringUtils.isNotBlank(nodeName)) {
            criteria.addLike("nodeName", nodeName);
        }

        if (ObjectUtils.isNotNull(routeLevel)) {
            criteria.addEqualTo("routeLevel", routeLevel);
        }

        return (EdoReviewLayerDefinition) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(EdoReviewLayerDefinition.class, criteria));
    }  */

    //use this

    /* public BigDecimal getMaxRouteLevel() {
        Criteria criteria = new Criteria();
        ReportQueryByCriteria query = new ReportQueryByCriteria(EdoReviewLayerDefinition.class, criteria);
        query.addOrderBy("routeLevel", false);
        query.setFetchSize(1);

        EdoReviewLayerDefinition reviewLayerDefinition = (EdoReviewLayerDefinition) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        if (ObjectUtils.isNotNull(reviewLayerDefinition)) {
            return reviewLayerDefinition.getRouteLevel();
        } else {
            return BigDecimal.ZERO;
        }
    }  */
}
