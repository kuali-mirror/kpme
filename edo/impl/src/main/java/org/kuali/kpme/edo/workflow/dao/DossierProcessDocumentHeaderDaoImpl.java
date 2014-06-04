package org.kuali.kpme.edo.workflow.dao;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.workflow.DossierProcessDocumentHeader;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.util.*;

public class DossierProcessDocumentHeaderDaoImpl extends PlatformAwareDaoBaseOjb implements DossierProcessDocumentHeaderDao {

    @Override
    public void saveOrUpdate(DossierProcessDocumentHeader documentHeader) {
        if (documentHeader != null) {
            this.getPersistenceBrokerTemplate().store(documentHeader);
        }
    }

    @Override
    public DossierProcessDocumentHeader getDossierProcessDocumentHeader(String documentId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("documentId", documentId);
        return (DossierProcessDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(DossierProcessDocumentHeader.class, crit));
    }

    @Override
    public DossierProcessDocumentHeader getDossierProcessDocumentHeader(Integer dossierId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("dossierId", dossierId);
        crit.addNotLike("documentTypeName", "%Supplemental%");
        crit.addNotLike("documentStatus", "%F%"); // this cannot be added here - have to look into this more
        return (DossierProcessDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(DossierProcessDocumentHeader.class, crit));
    }

    @Override
    public List<DossierProcessDocumentHeader> getDossierProcessDocumentHeaderByDocType(Integer dossierId, String docTypeName) {
    	List<DossierProcessDocumentHeader> dossierProcessDocumentHeaders = new ArrayList<DossierProcessDocumentHeader>();
        Criteria crit = new Criteria();
        crit.addEqualTo("dossierId", dossierId);
        crit.addEqualTo("documentTypeName", docTypeName);
        Query query = QueryFactory.newQuery(DossierProcessDocumentHeader.class, crit);
        dossierProcessDocumentHeaders.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return dossierProcessDocumentHeaders;
    }

    public boolean hasPendingSupplementalDocument(Integer dossierId) {

        Criteria crit = new Criteria();
        crit.addEqualTo("dossierId", dossierId);
        crit.addEqualTo("documentTypeName", EdoConstants.SUPPLEMENTAL_DOC_TYPE_TENURE);
        crit.addNotEqualTo("documentStatus", "F");

        Collection c_tenure = this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(DossierProcessDocumentHeader.class, crit));
        if (CollectionUtils.isNotEmpty(c_tenure)) {
            return true;
        }
        crit = new Criteria();
        crit.addEqualTo("dossierId", dossierId);
        crit.addEqualTo("documentTypeName", EdoConstants.SUPPLEMENTAL_DOC_TYPE_PROMOTION);
        crit.addNotEqualTo("documentStatus", "F");

        Collection c_promotion = this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(DossierProcessDocumentHeader.class, crit));
        if (CollectionUtils.isNotEmpty(c_promotion)) {
            return true;

        }
        return false;
    }

    public List<DossierProcessDocumentHeader> getPendingSupplementalDocuments(Integer dossierId) {
        List<DossierProcessDocumentHeader> docHeaders = new LinkedList<DossierProcessDocumentHeader>();

        Criteria crit = new Criteria();
        crit.addEqualTo("dossierId", dossierId);
        crit.addEqualTo("documentTypeName", EdoConstants.SUPPLEMENTAL_DOC_TYPE_TENURE);
        crit.addNotEqualTo("documentStatus", "F");

        Collection c_tenure = this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(DossierProcessDocumentHeader.class, crit));
        if (CollectionUtils.isNotEmpty(c_tenure)) {
            docHeaders.addAll(c_tenure);
        }
        crit = new Criteria();
        crit.addEqualTo("dossierId", dossierId);
        crit.addEqualTo("documentTypeName", EdoConstants.SUPPLEMENTAL_DOC_TYPE_PROMOTION);
        crit.addNotEqualTo("documentStatus", "F");

        Collection c_promotion = this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(DossierProcessDocumentHeader.class, crit));
        if (CollectionUtils.isNotEmpty(c_promotion)) {
            docHeaders.addAll(c_promotion);
        }
        return docHeaders;
    }
}
