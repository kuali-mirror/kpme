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
package org.kuali.hr.time.batch.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.batch.BatchJobEntry;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class BatchJobEntryDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements BatchJobEntryDao {

    @Override
    public void saveOrUpdate(BatchJobEntry batchJobEntry) {
        this.getPersistenceBrokerTemplate().store(batchJobEntry);
    }

    @Override
    public BatchJobEntry getBatchJobEntry(Long batchJobEntryId) {
        Criteria currentRecordCriteria = new Criteria();
        currentRecordCriteria.addEqualTo("tkBatchJobEntryId", batchJobEntryId);

        return (BatchJobEntry) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(BatchJobEntry.class, currentRecordCriteria));
    }

    @Override
    public List<BatchJobEntry> getBatchJobEntries(Long batchJobEntryId) {
        Criteria root = new Criteria();
        root.addEqualTo("tkBatchJobId", batchJobEntryId);
        Query query = QueryFactory.newQuery(BatchJobEntry.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<BatchJobEntry> entries = new ArrayList<BatchJobEntry>();
        entries.addAll(c);

        return entries;
    }

    @Override
    public List<BatchJobEntry> getBatchJobEntries(String ip, String status) {
        Criteria root = new Criteria();
        root.addEqualTo("ipAddress", ip);
        root.addEqualTo("batchJobEntryStatus", status);
        Query query = QueryFactory.newQuery(BatchJobEntry.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<BatchJobEntry> entries = new ArrayList<BatchJobEntry>();
        entries.addAll(c);

        return entries;
    }
    
    @Override
    public List<BatchJobEntry> getBatchJobEntries(Map<String, Object> criteria) {
        Criteria root = new Criteria();
        for (Map.Entry<String, Object> crit : criteria.entrySet()) {
            if(crit.getValue() != null){
                if(StringUtils.equals("ipAddress", crit.getKey()) || StringUtils.equals("batchJobName", crit.getKey())) {
                    root.addLike(crit.getKey(), "%" + crit.getValue() + "%");
                }
                else {
                    root.addEqualTo(crit.getKey(), crit.getValue());
                }
            }
        }

        Query query = QueryFactory.newQuery(BatchJobEntry.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<BatchJobEntry> entries = new ArrayList<BatchJobEntry>();
        entries.addAll(c);

        return entries;
    }
}
