package org.kuali.kpme.edo.item.count.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.item.count.EdoItemCountV;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 6/21/13
 * Time: 10:46 AM
 */
public class EdoItemCountVDaoImpl extends PlatformAwareDaoBaseOjb implements EdoItemCountVDao {

    public List<EdoItemCountV> getItemCount(BigDecimal dossierId, BigDecimal checklistSectionId) {
        List<EdoItemCountV> itemList = new LinkedList<EdoItemCountV>();

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("checklist_section_id", checklistSectionId);
        cConditions.addEqualTo("dossier_id", dossierId);

        Query query = QueryFactory.newQuery(EdoItemCountV.class, cConditions);
        itemList.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return itemList;
    }
}
