package org.kuali.hr.time.timeblock.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class TimeBlockDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements TimeBlockDao {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(TimeBlockDaoSpringOjbImpl.class);

	public void saveOrUpdate(TimeBlock timeBlock) {
		this.getPersistenceBrokerTemplate().store(timeBlock);
	}

	public void saveOrUpdate(List<TimeBlock> timeBlockList) {
		if (timeBlockList != null) {
			for (TimeBlock timeBlock : timeBlockList) {
				this.getPersistenceBrokerTemplate().store(timeBlock);
			}
		}
	}

	public TimeBlock getTimeBlock(Long tkTimeBlockId) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("tkTimeBlockId", tkTimeBlockId);

		return (TimeBlock)this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TimeBlock.class, currentRecordCriteria));
	}

	@SuppressWarnings("unchecked")
	public List<TimeBlock> getTimeBlocks(Long documentId){
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("documentId", documentId);
		Query query = QueryFactory.newQuery(TimeBlock.class, currentRecordCriteria);
		List<TimeBlock> timeBlocks = (List<TimeBlock>)this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		return timeBlocks == null || timeBlocks.size() == 0 ? new LinkedList<TimeBlock>() : timeBlocks;
	}

	@SuppressWarnings("unchecked")
	public List<TimeBlock> getTimeBlocksForAssignment(Assignment assign) {
    	Criteria rootCriteria = new Criteria();
    	rootCriteria.addEqualTo("jobNumber", assign.getJobNumber());
    	rootCriteria.addEqualTo("task", assign.getTask());
    	rootCriteria.addEqualTo("workArea", assign.getWorkArea());
    	Query query = QueryFactory.newQuery(TimeBlock.class, rootCriteria);
    	List<TimeBlock> timeBlocks = (List<TimeBlock>)this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
    	return timeBlocks == null || timeBlocks.isEmpty() ? new ArrayList<TimeBlock>() : timeBlocks;
    }
	
	public void deleteTimeBlock(TimeBlock timeBlock) {
		this.getPersistenceBrokerTemplate().delete(timeBlock);
	}

	
	public void deleteTimeBlocksAssociatedWithDocumentId(String documentId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("documentId", documentId);
		this.getPersistenceBrokerTemplate().deleteByQuery(QueryFactory.newQuery(TimeBlock.class, crit));
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TimeBlock> getTimeBlocksForClockLogId(Long tkClockLogId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("tkClockLogId", tkClockLogId);
		return (List<TimeBlock>)this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(TimeBlock.class, crit));
	}
	
	public List<TimeBlock> getTimeBlocks() { //KPME937
		List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
		Criteria crit = new Criteria();
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(TimeBlock.class, crit));
	
		if (c != null) {
			timeBlocks.addAll(c);
        }

        return timeBlocks;
	}
	
	@Override
	public List<TimeBlock> getLatestEndTimestamp() { //KPME937
		List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
		Criteria root = new Criteria();
		Criteria crit = new Criteria();
		
		ReportQueryByCriteria endTimestampSubQuery = QueryFactory.newReportQuery(TimeBlock.class, crit); 
		endTimestampSubQuery.setAttributes(new String[]{"max(endTimestamp)"});
	
		root.addEqualTo("endTimestamp", endTimestampSubQuery);
		
		Query query = QueryFactory.newQuery(TimeBlock.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (c != null) {
			timeBlocks.addAll(c);
        }

        return timeBlocks;
	}

}
