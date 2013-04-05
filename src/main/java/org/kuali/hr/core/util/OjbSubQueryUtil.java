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
package org.kuali.hr.core.util;


import org.apache.commons.collections.CollectionUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;

import java.sql.Date;
import java.util.List;

public class OjbSubQueryUtil {

    public static ReportQueryByCriteria getEffectiveDateSubQuery(Class clazz, Date asOfDate, List<String> equalToField, boolean checkActive) {
        //create base effective date filter
        Criteria effDateFilter = new Criteria();
        effDateFilter.addLessOrEqualThan("effectiveDate", asOfDate);
        Criteria orNull = new Criteria();
        orNull.addIsNull("effectiveDate");
        effDateFilter.addOrCriteria(orNull);
        
        return OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(clazz, effDateFilter, equalToField, checkActive);
    }

    public static ReportQueryByCriteria getEffectiveDateSubQueryWithoutFilter(Class clazz, List<String> equalToField, boolean checkActive) {
        return OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(clazz, null, equalToField, checkActive);
    }

    public static ReportQueryByCriteria getEffectiveDateSubQueryWithFilter(Class clazz, Criteria effectiveDateFilter, List<String> equalToField, boolean checkActive) {
        Criteria effdt = new Criteria();
        if (effectiveDateFilter != null) {
            effdt.addAndCriteria(effectiveDateFilter);
        }
        if (CollectionUtils.isNotEmpty(equalToField)) {
            for (String field : equalToField) {
                Criteria keyField = new Criteria();
                keyField.addEqualToField(field, Criteria.PARENT_QUERY_PREFIX+field);
                Criteria orNull = new Criteria();
                orNull.addIsNull(field);
                orNull.addIsNull(Criteria.PARENT_QUERY_PREFIX+field);
                keyField.addOrCriteria(orNull);
                effdt.addAndCriteria(keyField);
            }
        }
        if (checkActive) {
            effdt.addEqualTo("active", true);
        }
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(clazz, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        return effdtSubQuery;
    }

    public static ReportQueryByCriteria getTimestampSubQuery(Class clazz, List<String> equalToField, boolean checkActive) {
        Criteria timestamp = new Criteria();
        //need to check the effective date, always
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");

        if (CollectionUtils.isNotEmpty(equalToField)) {
            for (String field : equalToField) {
                Criteria keyField = new Criteria();
                keyField.addEqualToField(field, Criteria.PARENT_QUERY_PREFIX+field);
                Criteria orNull = new Criteria();
                orNull.addIsNull(field);
                orNull.addIsNull(Criteria.PARENT_QUERY_PREFIX+field);
                keyField.addOrCriteria(orNull);
                timestamp.addAndCriteria(keyField);
            }
        }
        if (checkActive) {
            timestamp.addEqualTo("active", true);
        }
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(clazz, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        return timestampSubQuery;
    }
}
