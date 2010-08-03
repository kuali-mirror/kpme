package org.kuali.hr.time.timeblock.dao;

import java.util.List;

import org.apache.log4j.Logger;
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
}
