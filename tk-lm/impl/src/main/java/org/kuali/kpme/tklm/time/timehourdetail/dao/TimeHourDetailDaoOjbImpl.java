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
package org.kuali.kpme.tklm.time.timehourdetail.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetailBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class TimeHourDetailDaoOjbImpl extends PlatformAwareDaoBaseOjb implements TimeHourDetailDao {

    private static final Logger LOG = Logger.getLogger(TimeHourDetailDaoOjbImpl.class);

    @Override
    public void saveOrUpdate(TimeHourDetailBo timeHourDetail) {
        this.getPersistenceBrokerTemplate().store(timeHourDetail);
    }

    @Override
    public void saveOrUpdate(List<TimeHourDetailBo> timeHourDetails) {
        if (timeHourDetails != null) {
            for (TimeHourDetailBo timeHourDetail : timeHourDetails) {
                this.getPersistenceBrokerTemplate().store(timeHourDetail);
            }
        }
    }

    @Override
    public TimeHourDetailBo getTimeHourDetail(String timeHourDetailId) {
        Criteria currentRecordCriteria = new Criteria();
        currentRecordCriteria.addEqualTo("tkTimeHourDetailId", timeHourDetailId);

        return (TimeHourDetailBo) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TimeHourDetailBo.class, currentRecordCriteria));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TimeHourDetailBo> getTimeHourDetailsForTimeBlock(String timeBlockId) {
        Criteria currentRecordCriteria = new Criteria();
        currentRecordCriteria.addEqualTo("tkTimeBlockId", timeBlockId);
        Query query = QueryFactory.newQuery(TimeHourDetailBo.class, currentRecordCriteria);
        return (List<TimeHourDetailBo>) this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
    }

    public void remove(String timeBlockId) {
        Criteria removalCriteria = new Criteria();
        removalCriteria.addEqualTo("tkTimeBlockId", timeBlockId);

        this.getPersistenceBrokerTemplate().deleteByQuery(QueryFactory.newQuery(TimeHourDetailBo.class, removalCriteria));
    }

    @Override
    public void removeById(String timeHourDetailId) {
        Criteria removalCriteria = new Criteria();
        removalCriteria.addEqualTo("tkTimeHourDetailId", timeHourDetailId);

        this.getPersistenceBrokerTemplate().deleteByQuery(QueryFactory.newQuery(TimeHourDetailBo.class, removalCriteria));
    }
}
