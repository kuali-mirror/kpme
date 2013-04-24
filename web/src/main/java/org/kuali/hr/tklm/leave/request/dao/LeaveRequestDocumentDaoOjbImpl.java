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
package org.kuali.hr.tklm.leave.request.dao;


import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.tklm.leave.workflow.LeaveRequestDocument;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.util.ArrayList;
import java.util.List;

public class LeaveRequestDocumentDaoOjbImpl extends PlatformAwareDaoBaseOjb implements LeaveRequestDocumentDao{
    @Override
    public LeaveRequestDocument getLeaveRequestDocument(String documentNumber) {
        LeaveRequestDocument lrd = null;

        Criteria root = new Criteria();
        root.addEqualTo("documentNumber", documentNumber);
        Query query = QueryFactory.newQuery(LeaveRequestDocument.class, root);
        lrd = (LeaveRequestDocument)this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        return lrd;
    }

    @Override
    public List<LeaveRequestDocument> getLeaveRequestDocumentsByLeaveBlockId(String leaveBlockId) {
        List<LeaveRequestDocument> lrd = null;

        Criteria root = new Criteria();
        root.addEqualTo("lmLeaveBlockId", leaveBlockId);
        Query query = QueryFactory.newQuery(LeaveRequestDocument.class, root);
        lrd = new ArrayList<LeaveRequestDocument>(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return lrd;
    }
}
