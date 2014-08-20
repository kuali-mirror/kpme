package org.kuali.kpme.edo.workflow.dao;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.dossier.type.EdoDossierTypeBo;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.workflow.EdoDossierDocumentInfoBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.util.*;

public class EdoDossierDocumentInfoDaoImpl extends PlatformAwareDaoBaseOjb implements EdoDossierDocumentInfoDao {

    @Override
    public void saveOrUpdate(EdoDossierDocumentInfoBo edoDossierDocumentInfoBo) {
        if (edoDossierDocumentInfoBo != null) {
            this.getPersistenceBrokerTemplate().store(edoDossierDocumentInfoBo);
        }
    }

    @Override
    public EdoDossierDocumentInfoBo getEdoDossierDocumentInfoByDocId(String edoDocumentId) {
    	Criteria crit = new Criteria();
        crit.addEqualTo("edoDocumentId", edoDocumentId);
        return (EdoDossierDocumentInfoBo) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(EdoDossierDocumentInfoBo.class, crit));
    
    }

    @Override
    public EdoDossierDocumentInfoBo getEdoDossierDocumentInfoByDossierId(String edoDossierId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("edoDossierId", edoDossierId);
        crit.addNotLike("documentTypeName", "%Supplemental%");
        crit.addNotLike("documentStatus", "%F%"); // this cannot be added here - have to look into this more
        return (EdoDossierDocumentInfoBo) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(EdoDossierDocumentInfoBo.class, crit));
    }

    @Override
    public List<EdoDossierDocumentInfoBo> getEdoDossierDocumentInfoByDocType(String edoDossierId, String docTypeName) {
    	List<EdoDossierDocumentInfoBo> dossierProcessDocumentHeaders = new ArrayList<EdoDossierDocumentInfoBo>();
        Criteria crit = new Criteria();
        crit.addEqualTo("edoDossierId", edoDossierId);
        crit.addEqualTo("documentTypeName", docTypeName);
        Query query = QueryFactory.newQuery(EdoDossierDocumentInfoBo.class, crit);
        dossierProcessDocumentHeaders.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return dossierProcessDocumentHeaders;
    }

    public boolean hasPendingSupplementalDocument(String edoDossierId) {

        Criteria crit = new Criteria();
        crit.addEqualTo("edoDossierId", edoDossierId);
        crit.addEqualTo("documentTypeName", EdoConstants.SUPPLEMENTAL_DOC_TYPE_TENURE);
        crit.addNotEqualTo("documentStatus", "F");

        Collection c_tenure = this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(EdoDossierDocumentInfoBo.class, crit));
        if (CollectionUtils.isNotEmpty(c_tenure)) {
            return true;
        }
        crit = new Criteria();
        crit.addEqualTo("edoDossierId", edoDossierId);
        crit.addEqualTo("documentTypeName", EdoConstants.SUPPLEMENTAL_DOC_TYPE_PROMOTION);
        crit.addNotEqualTo("documentStatus", "F");

        Collection c_promotion = this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(EdoDossierDocumentInfoBo.class, crit));
        if (CollectionUtils.isNotEmpty(c_promotion)) {
            return true;

        }
        return false;
    }

    public List<EdoDossierDocumentInfoBo> getPendingSupplementalDocuments(String edoDossierId) {
        List<EdoDossierDocumentInfoBo> docHeaders = new LinkedList<EdoDossierDocumentInfoBo>();

        Criteria crit = new Criteria();
        crit.addEqualTo("edoDossierId", edoDossierId);
        crit.addEqualTo("documentTypeName", EdoConstants.SUPPLEMENTAL_DOC_TYPE_TENURE);
        crit.addNotEqualTo("documentStatus", "F");

        Collection c_tenure = this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(EdoDossierDocumentInfoBo.class, crit));
        if (CollectionUtils.isNotEmpty(c_tenure)) {
            docHeaders.addAll(c_tenure);
        }
        crit = new Criteria();
        crit.addEqualTo("edoDossierId", edoDossierId);
        crit.addEqualTo("documentTypeName", EdoConstants.SUPPLEMENTAL_DOC_TYPE_PROMOTION);
        crit.addNotEqualTo("documentStatus", "F");

        Collection c_promotion = this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(EdoDossierDocumentInfoBo.class, crit));
        if (CollectionUtils.isNotEmpty(c_promotion)) {
            docHeaders.addAll(c_promotion);
        }
        return docHeaders;
    }
}
