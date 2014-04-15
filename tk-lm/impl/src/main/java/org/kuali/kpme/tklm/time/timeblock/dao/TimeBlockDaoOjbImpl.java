/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.timeblock.dao;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class TimeBlockDaoOjbImpl extends PlatformAwareDaoBaseOjb implements TimeBlockDao {

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TimeBlockDaoOjbImpl.class);

    public void saveOrUpdate(TimeBlockBo timeBlock) {
        this.getPersistenceBrokerTemplate().store(timeBlock);
    }

    public void saveOrUpdate(List<TimeBlockBo> timeBlockList) {
        if (timeBlockList != null) {
            for (TimeBlockBo timeBlock : timeBlockList) {
                this.getPersistenceBrokerTemplate().store(timeBlock);
            }
        }
    }

    public TimeBlockBo getTimeBlock(String tkTimeBlockId) {
        Criteria currentRecordCriteria = new Criteria();
        currentRecordCriteria.addEqualTo("tkTimeBlockId", tkTimeBlockId);

        return (TimeBlockBo) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TimeBlockBo.class, currentRecordCriteria));
    }

    @SuppressWarnings("unchecked")
    public List<TimeBlockBo> getTimeBlocks(String documentId) {
        Criteria currentRecordCriteria = new Criteria();
        currentRecordCriteria.addEqualTo("documentId", documentId);
        Query query = QueryFactory.newQuery(TimeBlockBo.class, currentRecordCriteria);
        List<TimeBlockBo> timeBlocks = (List<TimeBlockBo>) this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        return timeBlocks == null || timeBlocks.size() == 0 ? new LinkedList<TimeBlockBo>() : timeBlocks;
    }

    @SuppressWarnings("unchecked")
    public List<TimeBlockBo> getTimeBlocksForAssignment(Assignment assign) {
        Criteria rootCriteria = new Criteria();
        rootCriteria.addEqualTo("principalId", assign.getPrincipalId());
        rootCriteria.addEqualTo("jobNumber", assign.getJobNumber());
        rootCriteria.addEqualTo("task", assign.getTask());
        rootCriteria.addEqualTo("workArea", assign.getWorkArea());
        Query query = QueryFactory.newQuery(TimeBlockBo.class, rootCriteria);
        List<TimeBlockBo> timeBlocks = (List<TimeBlockBo>) this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        return timeBlocks == null || timeBlocks.isEmpty() ? new ArrayList<TimeBlockBo>() : timeBlocks;
    }

    public void deleteTimeBlock(TimeBlockBo timeBlock) {
        this.getPersistenceBrokerTemplate().delete(timeBlock);
    }


    public void deleteTimeBlocksAssociatedWithDocumentId(String documentId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("documentId", documentId);
        this.getPersistenceBrokerTemplate().deleteByQuery(QueryFactory.newQuery(TimeBlockBo.class, crit));

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TimeBlockBo> getTimeBlocksForClockLogEndId(String tkClockLogId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("clockLogEndId", tkClockLogId);
        return (List<TimeBlockBo>) this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(TimeBlockBo.class, crit));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TimeBlockBo> getTimeBlocksForClockLogBeginId(String tkClockLogId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("clockLogBeginId", tkClockLogId);
        return (List<TimeBlockBo>) this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(TimeBlockBo.class, crit));
    }

    @Override
    public List<TimeBlockBo> getLatestEndTimestampForEarnCode(String earnCode) { //KPME937
        List<TimeBlockBo> timeBlocks = new ArrayList<TimeBlockBo>();
        Criteria root = new Criteria();
        Criteria crit = new Criteria();
        crit.addEqualTo("earnCode", earnCode);
        ReportQueryByCriteria endTimestampSubQuery = QueryFactory.newReportQuery(TimeBlockBo.class, crit);
        endTimestampSubQuery.setAttributes(new String[]{"max(endTimestamp)"});

        root.addEqualTo("endTimestamp", endTimestampSubQuery);
        root.addEqualTo("earnCode", earnCode);
        Query query = QueryFactory.newQuery(TimeBlockBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            timeBlocks.addAll(c);
        }

        return timeBlocks;
    }

    @Override
    public List<TimeBlockBo> getOvernightTimeBlocks(String clockLogEndId) {
        List<TimeBlockBo> timeBlocks = new ArrayList<TimeBlockBo>();
        Criteria root = new Criteria();

        root.addEqualTo("clockLogEndId", clockLogEndId);

        Query query = QueryFactory.newQuery(TimeBlockBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            timeBlocks.addAll(c);
        }

        return timeBlocks;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<TimeBlockBo> getTimeBlocksWithEarnCode(String earnCode, DateTime effDate) {
    	 Criteria root = new Criteria();
         root.addEqualTo("earnCode", earnCode);
         root.addGreaterOrEqualThan("beginTimestamp", effDate.toDate());
         return (List<TimeBlockBo>) this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(TimeBlockBo.class, root));
    }

	@Override
	public List<TimeBlockBo> getTimeBlocksForLookup(String documentId,
			String principalId, String userPrincipalId, LocalDate fromDate,
			LocalDate toDate) {
        Criteria criteria = new Criteria();
        if(StringUtils.isNotBlank(documentId)) {
        	criteria.addEqualTo("documentId", documentId);
        }
        if(fromDate != null) {
        	criteria.addGreaterOrEqualThan("beginTimestamp", fromDate.toDate());
        }
        if(toDate != null) {
        	criteria.addLessOrEqualThan("endTimestamp",toDate.toDate());
        }
        if(StringUtils.isNotBlank(principalId)) {
        	criteria.addEqualTo("principalId", principalId);
        }
        if(StringUtils.isNotBlank(userPrincipalId)) {
        	criteria.addEqualTo("userPrincipalId", userPrincipalId);
        }
        Query query = QueryFactory.newQuery(TimeBlockBo.class, criteria);
        List<TimeBlockBo> timeBlocks = (List<TimeBlockBo>) this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        return timeBlocks == null || timeBlocks.size() == 0 ? new LinkedList<TimeBlockBo>() : timeBlocks;
	}

    @Override
    public List<TimeBlockBo> getIntersectingTimeBlocks(String principalId, Interval interval) {
        Criteria rootCriteria = new Criteria();
        rootCriteria.addEqualTo("principalId", principalId);

        Criteria orCriteria = new Criteria();
        Criteria beginCriteria = new Criteria();
        Criteria endCriteria = new Criteria();
        Timestamp intervalStart = new Timestamp(interval.getStartMillis());
        Timestamp intervalEnd = new Timestamp(interval.getEndMillis());
        beginCriteria.addBetween("beginTimestamp", intervalStart, intervalEnd);
        endCriteria.addBetween("endTimestamp", intervalStart, intervalEnd);
        orCriteria.addOrCriteria(beginCriteria);
        orCriteria.addOrCriteria(endCriteria);
        rootCriteria.addAndCriteria(orCriteria);


        Query query = QueryFactory.newQuery(TimeBlockBo.class, rootCriteria);
        List<TimeBlockBo> timeBlocks = (List<TimeBlockBo>) this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        return timeBlocks == null || timeBlocks.isEmpty() ? new ArrayList<TimeBlockBo>() : timeBlocks;
    }

}
