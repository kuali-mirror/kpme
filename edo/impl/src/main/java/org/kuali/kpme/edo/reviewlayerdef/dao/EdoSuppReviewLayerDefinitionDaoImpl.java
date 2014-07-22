package org.kuali.kpme.edo.reviewlayerdef.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.kpme.edo.reviewlayerdef.EdoSuppReviewLayerDefinitionBo;
import org.kuali.kpme.edo.util.EdoPropertyConstants;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class EdoSuppReviewLayerDefinitionDaoImpl extends PlatformAwareDaoBaseOjb implements EdoSuppReviewLayerDefinitionDao {
 
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

    public List<EdoSuppReviewLayerDefinitionBo> getSuppReviewLayerDefinitions(String edoReviewLayerDefinitionId) {
    	List<EdoSuppReviewLayerDefinitionBo> results = new LinkedList<EdoSuppReviewLayerDefinitionBo>();
        Criteria criteria = new Criteria();

        criteria.addEqualTo("edoReviewLayerDefinitionId", edoReviewLayerDefinitionId);

        Query query = QueryFactory.newQuery(EdoSuppReviewLayerDefinitionBo.class, criteria);
        results.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
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
    }
}
