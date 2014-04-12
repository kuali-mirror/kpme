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
package org.kuali.kpme.core.groupkey.dao;


import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class HrGroupKeyDaoOjbImpl extends PlatformAwareDaoBaseOjb implements HrGroupKeyDao {
    @Override
    public HrGroupKeyBo getHrGroupKey(String groupKeyCode, LocalDate asOfDate) {
        Criteria root = new Criteria();

        root.addEqualTo("groupKeyCode", groupKeyCode);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(HrGroupKeyBo.class, asOfDate, HrGroupKeyBo.BUSINESS_KEYS, true));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(HrGroupKeyBo.class, HrGroupKeyBo.BUSINESS_KEYS, true));

        Query query = QueryFactory.newQuery(HrGroupKeyBo.class, root);

        HrGroupKeyBo d = (HrGroupKeyBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        return d;
    }
}
