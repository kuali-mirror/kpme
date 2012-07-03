package org.kuali.hr.time.position.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.position.PositionNumber;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements PositionDao {

    @Override
    public Position getPosition(String hrPositionId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("hrPositionId", hrPositionId);

        Query query = QueryFactory.newQuery(Position.class, crit);
        return (Position) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }


    @Override
    public Position getPositionByPositionNumber(String hrPositionNbr) {
        Criteria crit = new Criteria();
        crit.addEqualTo("position_nbr", hrPositionNbr);

        Query query = QueryFactory.newQuery(Position.class, crit);
        return (Position) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public PositionNumber getNextUniquePositionNumber() {
        Criteria crit = new Criteria();
        ReportQueryByCriteria query = QueryFactory.newReportQuery(PositionNumber.class, crit);
        query.setAttributes(new String[]{"max(id)"});
        return (PositionNumber) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public void saveOrUpdate(PositionNumber positionNumber) {
        this.getPersistenceBrokerTemplate().store(positionNumber);
    }

}
