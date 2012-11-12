/**
 * Copyright 2004-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.time.position.dao;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public List<Position> getPositions(String positionNum, String workArea,
                                       String positionDescr, Date fromEffdt, Date toEffdt, String active, String showHistory) {
        Criteria crit = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        List<Position> results = new ArrayList<Position>();
        if(StringUtils.isNotBlank(workArea)){
            crit.addLike("workArea", workArea);
        }
        if(StringUtils.isNotBlank(positionNum) && StringUtils.isNotEmpty(positionNum)){
            crit.addLike("positionNumber", positionNum);
        }
        if(StringUtils.isNotBlank(positionDescr)){
            crit.addLike("description", positionDescr);
        }
        if(fromEffdt != null){
            crit.addGreaterOrEqualThan("effectiveDate", fromEffdt);
        }
        if(toEffdt != null){
            crit.addLessOrEqualThan("effectiveDate", toEffdt);
        } else {
            crit.addLessOrEqualThan("effectiveDate", TKUtils.getCurrentDate());
        }

        if(StringUtils.isEmpty(active) && StringUtils.equals(showHistory,"Y")){
            Query query = QueryFactory.newQuery(Position.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }
        else if(StringUtils.isEmpty(active) && StringUtils.equals(showHistory, "N")){
            effdt.addEqualToField("positionNumber", Criteria.PARENT_QUERY_PREFIX + "positionNumber");
            if(toEffdt != null){
                effdt.addLessOrEqualThan("effectiveDate", toEffdt);
            }
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Position.class, effdt);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

            timestamp.addEqualToField("positionNumber", Criteria.PARENT_QUERY_PREFIX + "positionNumber");
            timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Position.class, timestamp);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
//            if(StringUtils.isNotBlank(positionNum)){
//                crit.addEqualTo("positionNumber", positionNum);
//            }
            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Query query = QueryFactory.newQuery(Position.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }

        else if(StringUtils.equals(active, "Y") && StringUtils.equals("N", showHistory)){
            effdt.addEqualToField("positionNumber", Criteria.PARENT_QUERY_PREFIX + "positionNumber");
            if(toEffdt != null){
                effdt.addLessOrEqualThan("effectiveDate", toEffdt);
            }
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Position.class, effdt);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

            timestamp.addEqualToField("positionNumber", Criteria.PARENT_QUERY_PREFIX + "positionNumber");
            timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Position.class, timestamp);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
//            if(StringUtils.isNotBlank(positionNum)){
//                crit.addEqualTo("positionNumber", positionNum);
//            }
            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", true);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(Position.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        } //return all active records from the database
        else if(StringUtils.equals(active, "Y") && StringUtils.equals("Y", showHistory)){
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", true);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(Position.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }
        //return all inactive records in the database
        else if(StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "Y")){
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", false);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(Position.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }

        //return the most effective inactive rows if there are no active rows <= the curr date
        else if(StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "N")){
            effdt.addEqualToField("positionNumber", Criteria.PARENT_QUERY_PREFIX + "positionNumber");
            if(toEffdt != null){
                effdt.addLessOrEqualThan("effectiveDate", toEffdt);
            }
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Position.class, effdt);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

            timestamp.addEqualToField("positionNumber", Criteria.PARENT_QUERY_PREFIX + "positionNumber");
            timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Position.class, timestamp);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
//            if(StringUtils.isNotBlank(positionNum)){
//                crit.addEqualTo("positionNumber", positionNum);
//            }
            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", false);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(Position.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);

        }
        return results;
    }

    /*@Override
    public PositionNumber getNextUniquePositionNumber() {
        Criteria crit = new Criteria();
        ReportQueryByCriteria query = QueryFactory.newReportQuery(PositionNumber.class, crit);
        query.setAttributes(new String[]{"max(id)"});
        return (PositionNumber) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public void saveOrUpdate(PositionNumber positionNumber) {
        this.getPersistenceBrokerTemplate().store(positionNumber);
    }*/

}
