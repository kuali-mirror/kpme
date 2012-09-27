package org.kuali.hr.lm.leaveblock.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class LeaveBlockDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements LeaveBlockDao {

    private static final Logger LOG = Logger.getLogger(LeaveBlockDaoSpringOjbImpl.class);

    @Override
    public LeaveBlock getLeaveBlock(Long leaveBlockId) {
        Criteria root = new Criteria();
        root.addEqualTo("lmLeaveBlockId", leaveBlockId);
//        root.addEqualTo("active", true);

        Query query = QueryFactory.newQuery(LeaveBlock.class, root);

        return (LeaveBlock) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public void saveOrUpdate(LeaveBlock leaveBlock) {
        this.getPersistenceBrokerTemplate().store(leaveBlock);
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
    public List<LeaveBlock> getLeaveBlocks(String principalId, Date beginDate, Date endDate) {
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addGreaterOrEqualThan("leaveDate", beginDate);
        root.addLessOrEqualThan("leaveDate", endDate);
//        root.addEqualTo("active", true);

        Query query = QueryFactory.newQuery(LeaveBlock.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
        	leaveBlocks.addAll(c);
        }

        return leaveBlocks;
    }

	@Override
	public List<LeaveBlock> getLeaveBlocks(String principalId, String requestStatus, Date currentDate) {
	    List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
	    Criteria root = new Criteria();
	    root.addEqualTo("requestStatus", requestStatus);
	    root.addEqualTo("principalId", principalId);
	    if(currentDate != null) {
	    	root.addGreaterThan("leaveDate", currentDate);
	    }
	    Query query = QueryFactory.newQuery(LeaveBlock.class, root);
	    Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	    if(c!= null) {
	    	leaveBlocks.addAll(c);
	    }
		return leaveBlocks;
	}
	
	@Override
	public List<LeaveBlock> getLeaveBlocksForDate(String principalId, Date leaveDate) {
	    List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
	    Criteria root = new Criteria();
	    root.addEqualTo("principalId", principalId);
	    root.addEqualTo("leaveDate", leaveDate);
	    Query query = QueryFactory.newQuery(LeaveBlock.class, root);
	    Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	    if(c!= null) {
	    	leaveBlocks.addAll(c);
	    }
		return leaveBlocks;
	}

	@Override
	public List<LeaveBlock> getLeaveBlocks(Date leaveDate, String accrualCategoryId, String principalId) {
		Criteria root = new Criteria();
		root.addLessOrEqualThan("timestamp", leaveDate);
		root.addEqualTo("accrualCategoryId", accrualCategoryId);
		root.addEqualTo("principalId", principalId);
		Query query = QueryFactory.newQuery(LeaveBlock.class, root);
        List<LeaveBlock> leaveBlocks = (List<LeaveBlock>) this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		return leaveBlocks;
	}

    @Override
    public List<LeaveBlock> getLeaveBlocks(String principalId, String accrualCategoryId, Date beginDate, Date endDate) {
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addGreaterOrEqualThan("leaveDate", beginDate);
        root.addLessOrEqualThan("leaveDate", endDate);
        root.addEqualTo("accrualCategoryId", accrualCategoryId);
//        root.addEqualTo("active", true);

        Query query = QueryFactory.newQuery(LeaveBlock.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            leaveBlocks.addAll(c);
        }

        return leaveBlocks;
    }

    @Override
    public List<LeaveBlock> getFLMALeaveBlocks(String principalId, String accrualCategoryId, Date beginDate, Date endDate) {
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addGreaterOrEqualThan("leaveDate", beginDate);
        root.addLessOrEqualThan("leaveDate", endDate);
        root.addEqualTo("accrualCategoryId", accrualCategoryId);
        root.addEqualTo("earnCodeObj.fmla", "Y");
        //Criteria earnCodeSub = new Criteria();
        //earnCodeSub.addEqualTo("fmla", "Y");
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
	public List<LeaveBlock> getNotAccrualGeneratedLeaveBlocksForDate(String principalId, Date leaveDate) {
		List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
	    Criteria root = new Criteria();
	    root.addEqualTo("principalId", principalId);
	    root.addEqualTo("leaveDate", leaveDate);
	    root.addEqualTo("accrualGenerated", "N");
	    Query query = QueryFactory.newQuery(LeaveBlock.class, root);
	    Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	    if(c!= null) {
	    	leaveBlocks.addAll(c);
	    }
		return leaveBlocks;
	}
	@Override
	public List<LeaveBlock> getLeaveBlocksForTimesheet(String principalId, Date beginDate, Date endDate) {
		List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
		
        Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addGreaterOrEqualThan("leaveDate", beginDate);
        root.addLessOrEqualThan("leaveDate", endDate);
        List<String> typeValues = new ArrayList<String>();
        typeValues.add(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR);
        typeValues.add(LMConstants.LEAVE_BLOCK_TYPE.TIME_CALENDAR);
        root.addIn("leaveBlockType", typeValues);

        Query query = QueryFactory.newQuery(LeaveBlock.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
        	leaveBlocks.addAll(c);
        }

        return leaveBlocks;
	}

    public void deleteLeaveBlocksForDocumentId(String documentId){
        Criteria crit = new Criteria();
        crit.addEqualTo("documentId",documentId);
        this.getPersistenceBrokerTemplate().deleteByQuery(QueryFactory.newQuery(LeaveBlock.class, crit));

    }

}
