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
package org.kuali.kpme.tklm.leave.block.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.earncode.EarnCode;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class LeaveBlockDaoOjbImpl extends PlatformAwareDaoBaseOjb implements LeaveBlockDao {

    private static final Logger LOG = Logger.getLogger(LeaveBlockDaoOjbImpl.class);

    @Override
    public LeaveBlock getLeaveBlock(String leaveBlockId) {
        Criteria root = new Criteria();
        root.addEqualTo("lmLeaveBlockId", leaveBlockId);
//        root.addEqualTo("active", true);

        Query query = QueryFactory.newQuery(LeaveBlock.class, root);

        return (LeaveBlock) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public List<LeaveBlock> getLeaveBlocksForDocumentId(String documentId) {
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        Criteria root = new Criteria();
        root.addEqualTo("documentId", documentId);
//        root.addEqualTo("active", true);

        Query query = QueryFactory.newQuery(LeaveBlock.class, root);
        @SuppressWarnings("rawtypes")
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            leaveBlocks.addAll(c);
        }
        return leaveBlocks;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<LeaveBlock> getLeaveBlocks(String principalId, LocalDate beginDate, LocalDate endDate) {
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addGreaterOrEqualThan("leaveDate", beginDate.toDate());
        root.addLessOrEqualThan("leaveDate", endDate.toDate());
//        root.addEqualTo("active", true);

        Query query = QueryFactory.newQuery(LeaveBlock.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
        	leaveBlocks.addAll(c);
        }

        return leaveBlocks;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<LeaveBlock> getLeaveBlocksWithType(String principalId, LocalDate beginDate, LocalDate endDate, String leaveBlockType) {
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addGreaterOrEqualThan("leaveDate", beginDate.toDate());
        root.addLessOrEqualThan("leaveDate", endDate.toDate());
        root.addEqualTo("leaveBlockType", leaveBlockType);
//        root.addEqualTo("active", true);

        Query query = QueryFactory.newQuery(LeaveBlock.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            leaveBlocks.addAll(c);
        }

        return leaveBlocks;
    }

    @Override
    public List<LeaveBlock> getLeaveBlocksWithAccrualCategory(String principalId, LocalDate beginDate, LocalDate endDate, String accrualCategory) {
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addGreaterOrEqualThan("leaveDate", beginDate.toDate());
        root.addLessOrEqualThan("leaveDate", endDate.toDate());
        root.addEqualTo("accrualCategory", accrualCategory);
//        root.addEqualTo("active", true);

        Query query = QueryFactory.newQuery(LeaveBlock.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            leaveBlocks.addAll(c);
        }

        return leaveBlocks;
    }

    public List<LeaveBlock> getLeaveBlocksSinceCarryOver(String principalId, Map<String, LeaveBlock> carryOverDates, LocalDate endDate, boolean includeAllAccrualCategories) {
        Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        if (endDate != null) {
            root.addLessOrEqualThan("leaveDate", endDate.toDate());
        }

        Criteria orCriteria = new Criteria();
        for (Map.Entry<String, LeaveBlock> entry : carryOverDates.entrySet()) {
            Criteria crit = new Criteria();
            crit.addEqualTo("accrualCategory", entry.getKey());
            crit.addGreaterThan("leaveDate", entry.getValue().getLeaveDate());
            orCriteria.addOrCriteria(crit);
        }
        if (!orCriteria.isEmpty()) {
            if (CollectionUtils.isNotEmpty(carryOverDates.keySet()) && includeAllAccrualCategories) {
                Criteria crit = new Criteria();
                crit.addNotIn("accrualCategory", carryOverDates.keySet());
                orCriteria.addOrCriteria(crit);
            }
            root.addAndCriteria(orCriteria);
        }


        Query query = QueryFactory.newQuery(LeaveBlock.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        if (c != null) {
            leaveBlocks.addAll(c);
        }

        return leaveBlocks;
    }

    public Map<String, LeaveBlock> getLastCarryOverBlocks(String principalId, String leaveBlockType, LocalDate asOfDate) {
        Map<String, LeaveBlock> carryOver = new HashMap<String, LeaveBlock>();
        Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addEqualTo("leaveBlockType", leaveBlockType);

        Criteria dateSubquery = new Criteria();
        dateSubquery.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
        dateSubquery.addEqualToField("leaveBlockType", Criteria.PARENT_QUERY_PREFIX + "leaveBlockType");
        dateSubquery.addEqualToField("accrualCategory", Criteria.PARENT_QUERY_PREFIX + "accrualCategory");
        if (asOfDate != null) {
            dateSubquery.addLessThan("leaveDate", asOfDate.toDate());
        }

        ReportQueryByCriteria subQuery = QueryFactory.newReportQuery(LeaveBlock.class, dateSubquery);
        String[] attributes = new String[] { "max(leaveDate)" };
        subQuery.setAttributes(attributes);

        root.addEqualTo("leaveDate", subQuery);

        Query query = QueryFactory.newQuery(LeaveBlock.class, root);
        Collection<LeaveBlock> c = (Collection<LeaveBlock>)this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        //Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        for (LeaveBlock lb : c) {
            carryOver.put(lb.getAccrualCategory(), lb);
        }

        return carryOver;

    }

    @Override
    public List<LeaveBlock> getLeaveBlocks(String principalId, String leaveBlockType, String requestStatus, LocalDate beginDate, LocalDate endDate) {
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addEqualTo("leaveBlockType", leaveBlockType);
        root.addEqualTo("requestStatus", requestStatus);
        if(beginDate != null) {
            root.addGreaterOrEqualThan("leaveDate", beginDate.toDate());
        }
        if (endDate != null){
            root.addLessOrEqualThan("leaveDate", endDate.toDate());
        }
        Query query = QueryFactory.newQuery(LeaveBlock.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if(c!= null) {
            leaveBlocks.addAll(c);
        }
        return leaveBlocks;
    }

	@Override
	public List<LeaveBlock> getLeaveBlocks(String principalId, String leaveBlockType, String requestStatus, LocalDate currentDate) {
	    List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
	    Criteria root = new Criteria();
	    root.addEqualTo("principalId", principalId);
	    root.addEqualTo("leaveBlockType", leaveBlockType);
	    root.addEqualTo("requestStatus", requestStatus);
	    if(currentDate != null) {
	    	root.addGreaterThan("leaveDate", currentDate.toDate());
	    }
	    Query query = QueryFactory.newQuery(LeaveBlock.class, root);
	    Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	    if(c!= null) {
	    	leaveBlocks.addAll(c);
	    }
		return leaveBlocks;
	}
	
	@Override
	public List<LeaveBlock> getLeaveBlocksForDate(String principalId, LocalDate leaveDate) {
	    List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
	    Criteria root = new Criteria();
	    root.addEqualTo("principalId", principalId);
	    root.addEqualTo("leaveDate", leaveDate.toDate());
	    Query query = QueryFactory.newQuery(LeaveBlock.class, root);
	    Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	    if(c!= null) {
	    	leaveBlocks.addAll(c);
	    }
		return leaveBlocks;
	}

	@Override
	public List<LeaveBlock> getLeaveBlocks(LocalDate leaveDate, String accrualCategory, String principalId) {
		Criteria root = new Criteria();
		root.addLessOrEqualThan("leaveDate", leaveDate.toDate());
		root.addEqualTo("accrualCategory", accrualCategory);
		root.addEqualTo("principalId", principalId);
		Query query = QueryFactory.newQuery(LeaveBlock.class, root);
        List<LeaveBlock> leaveBlocks = (List<LeaveBlock>) this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		return leaveBlocks;
	}

    @Override
    public List<LeaveBlock> getLeaveBlocks(String principalId, String accrualCategory, LocalDate beginDate, LocalDate endDate) {
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addGreaterOrEqualThan("leaveDate", beginDate.toDate());
        root.addLessOrEqualThan("leaveDate", endDate.toDate());
        root.addEqualTo("accrualCategory", accrualCategory);
//        root.addEqualTo("active", true);

        Query query = QueryFactory.newQuery(LeaveBlock.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            leaveBlocks.addAll(c);
        }

        return leaveBlocks;
    }

    @Override
    public List<LeaveBlock> getFMLALeaveBlocks(String principalId, String accrualCategory, LocalDate beginDate, LocalDate endDate) {
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addGreaterOrEqualThan("leaveDate", beginDate.toDate());
        root.addLessOrEqualThan("leaveDate", endDate.toDate());
        root.addEqualTo("accrualCategory", accrualCategory);
        
        Criteria earnCode = new Criteria();
        earnCode.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
        earnCode.addEqualTo("fmla", "Y");
        ReportQueryByCriteria earnCodeSubQuery = QueryFactory.newReportQuery(EarnCode.class, earnCode);
        root.addEqualTo("earnCode", earnCodeSubQuery);
        
        //root.add
//        root.addEqualTo("active", true);

        Query query = QueryFactory.newQuery(LeaveBlock.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            leaveBlocks.addAll(c);
        }

        return leaveBlocks;
    }
	
	@Override
	public List<LeaveBlock> getNotAccrualGeneratedLeaveBlocksForDate(String principalId, LocalDate leaveDate) {
		List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
	    Criteria root = new Criteria();
	    root.addEqualTo("principalId", principalId);
	    root.addEqualTo("leaveDate", leaveDate.toDate());
	    root.addEqualTo("accrualGenerated", "N");
	    Query query = QueryFactory.newQuery(LeaveBlock.class, root);
	    Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	    if(c!= null) {
	    	leaveBlocks.addAll(c);
	    }
		return leaveBlocks;
	}
	
	public List<LeaveBlock> getCalendarLeaveBlocks(String principalId, LocalDate beginDate, LocalDate endDate) {
		List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
		
        Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addGreaterOrEqualThan("leaveDate", beginDate.toDate());
        root.addLessOrEqualThan("leaveDate", endDate.toDate());
        List<String> typeValues = new ArrayList<String>();
        typeValues.add(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR);
        typeValues.add(LMConstants.LEAVE_BLOCK_TYPE.TIME_CALENDAR);
        typeValues.add(LMConstants.LEAVE_BLOCK_TYPE.ACCRUAL_SERVICE);
        root.addIn("leaveBlockType", typeValues);

        Query query = QueryFactory.newQuery(LeaveBlock.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
        	leaveBlocks.addAll(c);
        }
        return leaveBlocks;

	}
	
    public void deleteLeaveBlock(String leaveBlockId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("lmLeaveBlockId",leaveBlockId);
        this.getPersistenceBrokerTemplate().deleteByQuery(QueryFactory.newQuery(LeaveBlock.class, crit));
    }
	
    public void deleteLeaveBlocksForDocumentId(String documentId){
        Criteria crit = new Criteria();
        crit.addEqualTo("documentId",documentId);
        this.getPersistenceBrokerTemplate().deleteByQuery(QueryFactory.newQuery(LeaveBlock.class, crit));

    }
    
      
    @Override
    public List<LeaveBlock> getAccrualGeneratedLeaveBlocks(String principalId, LocalDate beginDate, LocalDate endDate) {
    	List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        Criteria root = new Criteria();
          
        root.addEqualTo("principalId", principalId);
        root.addGreaterOrEqualThan("leaveDate", beginDate.toDate());
        root.addLessOrEqualThan("leaveDate", endDate.toDate());
        root.addEqualTo("accrualGenerated", "Y");

        Query query = QueryFactory.newQuery(LeaveBlock.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
        	leaveBlocks.addAll(c);
        }
        return leaveBlocks;
    }
    
    @Override
    public List<LeaveBlock> getSSTOLeaveBlocks(String principalId, String sstoId, LocalDate accruledDate) {
    	List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        Criteria root = new Criteria();
          
        root.addEqualTo("principalId", principalId);
        root.addEqualTo("leaveDate", accruledDate.toDate());
        root.addEqualTo("scheduleTimeOffId", sstoId);

        Query query = QueryFactory.newQuery(LeaveBlock.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
        	leaveBlocks.addAll(c);
        }
        return leaveBlocks;
    }

    @Override
    public List<LeaveBlock> getABELeaveBlocksSinceTime(String principalId, DateTime lastRanDateTime) {
    	List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
    	Criteria root = new Criteria();
         
		root.addEqualTo("principalId", principalId);
		root.addGreaterThan("timestamp", lastRanDateTime.toDate());
		Query query = QueryFactory.newQuery(LeaveBlock.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		List<LeaveBlock> tempList = new ArrayList<LeaveBlock>();
		if (c != null) {
			tempList.addAll(c);
		}
		for(LeaveBlock lb : tempList) {
			if(lb != null && StringUtils.isNotEmpty(lb.getEarnCode())) {
				EarnCode ec = HrServiceLocator.getEarnCodeService().getEarnCode(lb.getEarnCode(), lb.getLeaveLocalDate());
				if(ec != null && ec.getEligibleForAccrual().equals("N")) {
					leaveBlocks.add(lb);
				}
			}
		}
    	return leaveBlocks;
    }

}
