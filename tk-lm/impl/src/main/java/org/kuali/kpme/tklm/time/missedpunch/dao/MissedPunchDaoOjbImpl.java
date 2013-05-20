/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.missedpunch.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunch;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunchDocument;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class MissedPunchDaoOjbImpl extends PlatformAwareDaoBaseOjb implements MissedPunchDao {
    
	@Override
	public MissedPunchDocument getMissedPunchDocument(String tkMissedPunchId) {
        Criteria root = new Criteria();
        root.addEqualTo("tkMissedPunchId", tkMissedPunchId);
        Query query = QueryFactory.newQuery(MissedPunchDocument.class, root);
        return (MissedPunchDocument) getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	@SuppressWarnings("unchecked")
    public List<MissedPunch> getMissedPunchesByTimesheetDocumentId(String timesheetDocumentId) {
        List<MissedPunch> missedPunches = new ArrayList<MissedPunch>();
        
		Criteria root = new Criteria();
        root.addEqualTo("timesheetDocumentId", timesheetDocumentId);
        Query query = QueryFactory.newQuery(MissedPunch.class, root);
        missedPunches.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return missedPunches;
	}
	
	@Override
    public MissedPunch getMissedPunchByClockLogId(String tkClockLogId) {
        Criteria root = new Criteria();
        root.addEqualTo("tkClockLogId", tkClockLogId);
        Query query = QueryFactory.newQuery(MissedPunch.class, root);
        return (MissedPunch) getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
    
}
