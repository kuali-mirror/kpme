package org.kuali.hr.core.util;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class OjbSubQueryUtil {

    public static ReportQueryByCriteria getEffectiveDateSubQuery(Class clazz, Date asOfDate, List<String> equalToField, boolean checkActive) {
        //create base effective date filter
        Criteria effDateFilter = new Criteria();
        effDateFilter.addLessOrEqualThan("effectiveDate", asOfDate);
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
