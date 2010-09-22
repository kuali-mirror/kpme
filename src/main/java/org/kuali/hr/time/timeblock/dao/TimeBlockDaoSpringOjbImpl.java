package org.kuali.hr.time.timeblock.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class TimeBlockDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements TimeBlockDao {

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

	public TimeBlock getTimeBlock(String timeBlockId) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("timeBlockId", timeBlockId);

		return (TimeBlock)this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TimeBlock.class, currentRecordCriteria));
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<TimeBlock> getTimeBlocksByPeriod(String principalId, Date beginDate, Date endDate) {

		Timestamp begin = new Timestamp(beginDate.getTime());
		Timestamp end = new Timestamp(endDate.getTime());
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("user_principal_id", principalId);
		currentRecordCriteria.addGreaterOrEqualThan("begin_ts", begin);
		currentRecordCriteria.addLessOrEqualThan("end_ts", end);
		currentRecordCriteria.addOrderByDescending("begin_ts");
		List<TimeBlock> timeBlocks = new LinkedList<TimeBlock>();
		Collection<TimeBlock> c = this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(TimeBlock.class, currentRecordCriteria));
		timeBlocks.addAll(c);

		return timeBlocks;
	}

	public void deleteTimeBlock(TimeBlock timeBlock) {
		this.getPersistenceBrokerTemplate().delete(timeBlock);
	}
}
