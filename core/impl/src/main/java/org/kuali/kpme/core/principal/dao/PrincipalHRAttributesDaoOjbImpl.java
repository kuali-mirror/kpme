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
package org.kuali.kpme.core.principal.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.principal.PrincipalHRAttributesBo;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PrincipalHRAttributesDaoOjbImpl extends PlatformAwareDaoBaseOjb implements PrincipalHRAttributesDao {

	@Override
	public PrincipalHRAttributesBo getPrincipalCalendar(String principalId,
			LocalDate asOfDate) {
		PrincipalHRAttributesBo pc = null;

		Criteria root = new Criteria();
//		KPME-2273/1965 Business Key list used instead
//      ImmutableList<String> fields = new ImmutableList.Builder<String>()
//                .add("principalId")
//                .build();

		root.addEqualTo("principalId", principalId);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PrincipalHRAttributesBo.class, asOfDate, PrincipalHRAttributesBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PrincipalHRAttributesBo.class, PrincipalHRAttributesBo.BUSINESS_KEYS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(PrincipalHRAttributesBo.class, root);
		Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

		if (obj != null) {
			pc = (PrincipalHRAttributesBo) obj;
		}

		return pc;
	}

	@Override
	public void saveOrUpdate(PrincipalHRAttributesBo principalCalendar) {
		this.getPersistenceBrokerTemplate().store(principalCalendar);
		
	}

	@Override
	public void saveOrUpdate(List<PrincipalHRAttributesBo> lstPrincipalCalendar) {
		if(lstPrincipalCalendar != null){
			for(PrincipalHRAttributesBo principalCal : lstPrincipalCalendar){
				this.getPersistenceBrokerTemplate().store(principalCal);
			}
		}
		
	}

    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<PrincipalHRAttributesBo> getActiveEmployeesForPayCalendar(String payCalendarName, LocalDate asOfDate) {
        List<PrincipalHRAttributesBo> principalHRAttributes = new ArrayList<PrincipalHRAttributesBo>();
        Criteria root = new Criteria();
        
        root.addEqualTo("payCalendar", payCalendarName);
//		KPME-2273/1965 Business Key list used instead
//        ImmutableList<String> fields = new ImmutableList.Builder<String>()
//                .add("payCalendar")
//                .add("principalId")
//                .build();

        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PrincipalHRAttributesBo.class, asOfDate, PrincipalHRAttributesBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PrincipalHRAttributesBo.class, PrincipalHRAttributesBo.BUSINESS_KEYS, false));

        Query query = QueryFactory.newQuery(PrincipalHRAttributesBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
        	principalHRAttributes.addAll(c);
        }

        return principalHRAttributes;
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<PrincipalHRAttributesBo> getActiveEmployeesForLeaveCalendar(String leaveCalendarName, LocalDate asOfDate) {
        List<PrincipalHRAttributesBo> principalHRAttributes = new ArrayList<PrincipalHRAttributesBo>();
        Criteria root = new Criteria();


        root.addEqualTo("leaveCalendar", leaveCalendarName);
//		KPME-2273/1965 Business Key list used instead
//        ImmutableList<String> fields = new ImmutableList.Builder<String>()
//                .add("leaveCalendar")
//                .add("principalId")
//                .build();
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PrincipalHRAttributesBo.class, asOfDate, PrincipalHRAttributesBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PrincipalHRAttributesBo.class, PrincipalHRAttributesBo.BUSINESS_KEYS, false));

        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PrincipalHRAttributesBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
        	principalHRAttributes.addAll(c);
        }

        return principalHRAttributes;
    }
    
    public List<String> getActiveEmployeesIdForLeaveCalendarAndIdList(String leaveCalendarName, List<String> pidList, LocalDate asOfDate) {
    	List<PrincipalHRAttributesBo> principalHRAttributes = new ArrayList<PrincipalHRAttributesBo>();
        Criteria root = new Criteria();
        
        root.addEqualTo("leaveCalendar", leaveCalendarName);
        root.addIn("principalId", pidList);
//		KPME-2273/1965 Business Key list used instead
//        ImmutableList<String> fields = new ImmutableList.Builder<String>()
//                .add("leaveCalendar")
//                .add("principalId")
//                .build();
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PrincipalHRAttributesBo.class,asOfDate, PrincipalHRAttributesBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PrincipalHRAttributesBo.class, PrincipalHRAttributesBo.BUSINESS_KEYS, false));

        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PrincipalHRAttributesBo.class, root);
        Collection c = getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
        	principalHRAttributes.addAll(c);
        }
        Set<String> pids = new HashSet<String>();
        for(PrincipalHRAttributesBo phra : principalHRAttributes) {
	       	if(phra != null) {
	       	pids.add(phra.getPrincipalId());
	       	}
        }
        List<String> ids = new ArrayList<String>();
        ids.addAll(pids);
        
        return ids;
    }
    
    public List<String> getActiveEmployeesIdForTimeCalendarAndIdList(String timeCalendarName, List<String> pidList, LocalDate asOfDate) {
    	List<PrincipalHRAttributesBo> principalHRAttributes = new ArrayList<PrincipalHRAttributesBo>();
        Criteria root = new Criteria();
        
        root.addEqualTo("payCalendar", timeCalendarName);
        root.addIn("principalId", pidList);
//		KPME-2273/1965 Business Key list used instead
//        ImmutableList<String> fields = new ImmutableList.Builder<String>()
//                .add("payCalendar")
//                .add("principalId")
//                .build();
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PrincipalHRAttributesBo.class, asOfDate, PrincipalHRAttributesBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PrincipalHRAttributesBo.class, PrincipalHRAttributesBo.BUSINESS_KEYS, false));

        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PrincipalHRAttributesBo.class, root);
        Collection c = getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
        	principalHRAttributes.addAll(c);
        }
        Set<String> pids = new HashSet<String>();
        for(PrincipalHRAttributesBo phra : principalHRAttributes) {
	       	if(phra != null) {
	       	pids.add(phra.getPrincipalId());
	       	}
        }
        List<String> ids = new ArrayList<String>();
        ids.addAll(pids);
        
        return ids;
    }
	
    // KPME-1250 Kagata
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<PrincipalHRAttributesBo> getActiveEmployeesForLeavePlan(String leavePlan, LocalDate asOfDate) {

        List<PrincipalHRAttributesBo> principals = new ArrayList<PrincipalHRAttributesBo>();
        Criteria root = new Criteria();
//		KPME-2273/1965 Business Key list used instead
//        ImmutableList<String> fields = new ImmutableList.Builder<String>()
//                .add("leavePlan")
//                .add("principalId")
//                .build();
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PrincipalHRAttributesBo.class, asOfDate, PrincipalHRAttributesBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PrincipalHRAttributesBo.class, PrincipalHRAttributesBo.BUSINESS_KEYS, false));

        root.addEqualTo("leavePlan", leavePlan);
        root.addEqualTo("active", true);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PrincipalHRAttributesBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
        	principals.addAll(c);
        }

        return principals;
    }
    
	@Override
	@SuppressWarnings("rawtypes")
    public List<String> getUniquePayCalendars(List<String> principalIds) {
        List<String> payCalendars = new ArrayList<String>();
        
        Criteria root = new Criteria();
        root.addIn("principalId", principalIds);
        root.addEqualTo("active", true);
        
        ReportQueryByCriteria query = QueryFactory.newReportQuery(PrincipalHRAttributesBo.class, root, true);
        query.setDistinct(true);
        query.setAttributes(new String[] {"payCalendar"});
        Iterator iterator = getPersistenceBrokerTemplate().getReportQueryIteratorByQuery(query);
        while (iterator.hasNext()) {
            Object[] values = (Object[]) iterator.next();
            String leaveCalendar = (String) values[0];
            if (StringUtils.isNotBlank(leaveCalendar)) {
            	payCalendars.add(leaveCalendar);
            }
        }
        
        return payCalendars;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public List<String> getUniqueLeaveCalendars(List<String> principalIds) {
        List<String> leaveCalendars = new ArrayList<String>();
        
        Criteria root = new Criteria();
        root.addIn("principalId", principalIds);
        root.addEqualTo("active", true);
        
        ReportQueryByCriteria query = QueryFactory.newReportQuery(PrincipalHRAttributesBo.class, root, true);
        query.setDistinct(true);
        query.setAttributes(new String[] {"leaveCalendar"});
        Iterator iterator = this.getPersistenceBrokerTemplate().getReportQueryIteratorByQuery(query);
        while (iterator.hasNext()) {
            Object[] values = (Object[]) iterator.next();
            String leaveCalendar = (String) values[0];
            if (StringUtils.isNotBlank(leaveCalendar)) {
                leaveCalendars.add(leaveCalendar);
            }
        }
        
        return leaveCalendars;
    }
    
    @Override
    public PrincipalHRAttributesBo getInactivePrincipalHRAttributes(String principalId, LocalDate asOfDate) {
    	PrincipalHRAttributesBo pc = null;

		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
        effdt.addGreaterOrEqualThan("effectiveDate", asOfDate.toDate());
//		KPME-2273/1965 Business Key list used instead
//        ImmutableList<String> fields = new ImmutableList.Builder<String>()
//                .add("leavePlan")
//                .add("principalId")
//                .build();
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(PrincipalHRAttributesBo.class, effdt, PrincipalHRAttributesBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PrincipalHRAttributesBo.class, PrincipalHRAttributesBo.BUSINESS_KEYS, false));

		root.addEqualTo("principalId", principalId);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", false);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(PrincipalHRAttributesBo.class, root);
		Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

		if (obj != null) {
			pc = (PrincipalHRAttributesBo) obj;
		}

		return pc;
    }
    
    @Override
    public PrincipalHRAttributesBo getPrincipalHRAttributes(String hrPrincipalAttributeId) {
    	Criteria crit = new Criteria();
		crit.addEqualTo("hrPrincipalAttributeId", hrPrincipalAttributeId);
		
		Query query = QueryFactory.newQuery(PrincipalHRAttributesBo.class, crit);
		return (PrincipalHRAttributesBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }
    
    @Override
    public List<PrincipalHRAttributesBo> getAllActivePrincipalHrAttributesForPrincipalId(String principalId, LocalDate asOfDate) {
    	
    	List<PrincipalHRAttributesBo> phaList = new ArrayList<PrincipalHRAttributesBo>();
    	Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addLessOrEqualThan("effectiveDate", asOfDate.toDate());
        root.addEqualTo("active", true);
        Query query = QueryFactory.newQuery(PrincipalHRAttributesBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
            phaList.addAll(c);
        }
        return phaList;        
    }
    
    @Override
    public List<PrincipalHRAttributesBo> getAllInActivePrincipalHrAttributesForPrincipalId(String principalId, LocalDate asOfDate) {
    	List<PrincipalHRAttributesBo> phaList = new ArrayList<PrincipalHRAttributesBo>();
    	Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addLessOrEqualThan("effectiveDate", asOfDate.toDate());
        root.addEqualTo("active", false);

        Query query = QueryFactory.newQuery(PrincipalHRAttributesBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
            phaList.addAll(c);
        }
        return phaList;  
    }
    @Override
    public PrincipalHRAttributesBo getMaxTimeStampPrincipalHRAttributes(String principalId) {
    	Criteria root = new Criteria();
        Criteria crit = new Criteria();
        
        crit.addEqualTo("principalId", principalId);
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(PrincipalHRAttributesBo.class, crit);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("principalId", principalId);
        root.addEqualTo("timestamp", timestampSubQuery);

        Query query = QueryFactory.newQuery(PrincipalHRAttributesBo.class, root);
        return (PrincipalHRAttributesBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }
    
    @Override
    public List<PrincipalHRAttributesBo> getActivePrincipalHrAttributesForRange(String principalId, LocalDate startDate, LocalDate endDate) {
    	List<PrincipalHRAttributesBo> activeList = new ArrayList<PrincipalHRAttributesBo>();
    	Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addGreaterOrEqualThan("effectiveDate", startDate.toDate());
        root.addLessOrEqualThan("effectiveDate", endDate.toDate());
        root.addEqualTo("active", true);
        Query query = QueryFactory.newQuery(PrincipalHRAttributesBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
        	activeList.addAll(c);
        }
        List<PrincipalHRAttributesBo> aList = new ArrayList<PrincipalHRAttributesBo>();
        aList.addAll(activeList);
        for(PrincipalHRAttributesBo aPha : aList) {
        	List<PrincipalHRAttributesBo> inactivePhas = this.getInactivePrincipalHRAttributesForRange(principalId, aPha.getEffectiveLocalDate(), endDate);
        	if(CollectionUtils.isNotEmpty(inactivePhas)) {
        		for(PrincipalHRAttributesBo inactive : inactivePhas) {
        			if(inactive.getTimestamp().after(aPha.getTimestamp())) {
        				activeList.remove(aPha);
        			}
        		}
        	}
        }
        
        return activeList;   
    }
    
    @Override
    public List<PrincipalHRAttributesBo> getInactivePrincipalHRAttributesForRange(String principalId, LocalDate startDate, LocalDate endDate) {
    	List<PrincipalHRAttributesBo> inactiveList = new ArrayList<PrincipalHRAttributesBo>();
    	Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addGreaterOrEqualThan("effectiveDate", startDate.toDate());
        root.addLessOrEqualThan("effectiveDate", endDate.toDate());
        root.addEqualTo("active", false);
        Query query = QueryFactory.newQuery(PrincipalHRAttributesBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
        	inactiveList.addAll(c);
        }
        return inactiveList;
    }
   
	@Override
    @SuppressWarnings("unchecked")
    public List<PrincipalHRAttributesBo> getPrincipalHrAtributes(String principalId, String leavePlan, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
    	List<PrincipalHRAttributesBo> results = new ArrayList<PrincipalHRAttributesBo>();
        
    	Criteria root = new Criteria();
    	
        if (StringUtils.isNotBlank(principalId)) {
            root.addLike("UPPER(principalId)", principalId.toUpperCase()); // KPME-2695 in case principal id is not a number
        }

        if (StringUtils.isNotBlank(leavePlan)) {
            root.addLike("UPPER(leavePlan)", leavePlan.toUpperCase()); // KPME-2695
        }

        Criteria effectiveDateFilter = new Criteria();
        if (fromEffdt != null) {
            effectiveDateFilter.addGreaterOrEqualThan("effectiveDate", fromEffdt.toDate());
        }
        if (toEffdt != null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", toEffdt.toDate());
        }
        if (fromEffdt == null && toEffdt == null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", LocalDate.now().toDate());
        }
        root.addAndCriteria(effectiveDateFilter);

        if (StringUtils.isNotBlank(active)) {
        	Criteria activeFilter = new Criteria();
            if (StringUtils.equals(active, "Y")) {
                activeFilter.addEqualTo("active", true);
            } else if (StringUtils.equals(active, "N")) {
                activeFilter.addEqualTo("active", false);
            }
            root.addAndCriteria(activeFilter);
        }
        
        if (StringUtils.equals(showHistory, "N")) {
//    		KPME-2273/1965 Business Key list used instead
//            ImmutableList<String> fields = new ImmutableList.Builder<String>()
//                    .add("principalId")
//                    .add("leavePlan")
//                    .build();
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(PrincipalHRAttributesBo.class, effectiveDateFilter, PrincipalHRAttributesBo.BUSINESS_KEYS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PrincipalHRAttributesBo.class, PrincipalHRAttributesBo.BUSINESS_KEYS, false));
       }
        
       Query query = QueryFactory.newQuery(PrincipalHRAttributesBo.class, root);
       results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
       
       return results;
    }

}