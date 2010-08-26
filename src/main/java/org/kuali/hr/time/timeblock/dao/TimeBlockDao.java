package org.kuali.hr.time.timeblock.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlock;

public interface TimeBlockDao {

	public void saveOrUpdate(TimeBlock timeBlock);

	public void saveOrUpdate(List<TimeBlock> timeBlockList);

	public List<TimeBlock> getTimeBlocksByPeriod(String principalId, Date beginDate, Date endDate);

	public void deleteTimeBlock(TimeBlock timeBlock);

	public TimeBlock getTimeBlock(String timeBlockId);

}
