package org.kuali.kpme.edo.dossier.type.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.dossier.type.EdoDossierTypeBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/20/12
 * Time: 9:57 AM
 *
 * This class implements the DAO interface to retrieve dossier types from the database.  It has
 * two methods: one to retrieve a single dossier type record and one to retrieve a list of all
 * dossier type records.
 *
 */
public class EdoDossierTypeDaoImpl extends PlatformAwareDaoBaseOjb implements EdoDossierTypeDao {

    /**
     * Retrieve the dossier type record with the specified database ID
     *
     * @param   dossierTypeID   the ID number of the record desired
     * @return                  an object of EdoDossierType
     */
    public EdoDossierTypeBo getEdoDossierTypeById(String edoDossierTypeID){
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("edoDossierTypeID", edoDossierTypeID);

        Query query = QueryFactory.newQuery(EdoDossierTypeBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            if (c.size() == 1) {
                return (EdoDossierTypeBo)c.toArray()[0];
            }
        }

        return null;
    }

    /**
     * Retrieve a list of all dossier types from the database
     *
     * @return      a List of EdoDossierType objects
     */
    public List<EdoDossierTypeBo> getEdoDossierTypeList() {

        List<EdoDossierTypeBo> dossierTypeList = new LinkedList<EdoDossierTypeBo>();

        Criteria cConditions = new Criteria();

        Query query = QueryFactory.newQuery(EdoDossierTypeBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            if (c.size() > 0) {
                dossierTypeList.addAll(c);
                return dossierTypeList;
            }
        }
        return null;
    }

    public void saveOrUpdate(EdoDossierTypeBo dossierType) {
        this.getPersistenceBrokerTemplate().store(dossierType);
    }

    public EdoDossierTypeBo getEdoDossierTypeByName(String dossierTypeName) {
        Criteria crit = new Criteria();
        crit.addEqualTo("dossierTypeName", dossierTypeName);
        return (EdoDossierTypeBo) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(EdoDossierTypeBo.class, crit));
    }

    public EdoDossierTypeBo getEdoDossierTypeByCode(String dossierTypeCode) {
        Criteria crit = new Criteria();
        crit.addEqualTo("dossierTypeCode", dossierTypeCode);
        return (EdoDossierTypeBo) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(EdoDossierTypeBo.class, crit));
    }
}
