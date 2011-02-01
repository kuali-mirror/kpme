package org.kuali.hr.time.timeblock.dao;

import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlock;

public interface TimeBlockDao {

	public void saveOrUpdate(TimeBlock timeBlock);

	public void saveOrUpdate(List<TimeBlock> timeBlockList);

	public void deleteTimeBlock(TimeBlock timeBlock);

	public TimeBlock getTimeBlock(Long timeBlockId);
	
	public List<TimeBlock> getTimeBlocks(Long documentId);

}
