package org.kuali.hr.time.timeblock.dao;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.timeblock.TimeBlock;

import java.util.List;

public interface TimeBlockDao {

	public void saveOrUpdate(TimeBlock timeBlock);

	public void saveOrUpdate(List<TimeBlock> timeBlockList);

	public void deleteTimeBlock(TimeBlock timeBlock);

	public TimeBlock getTimeBlock(Long timeBlockId);
	
	public List<TimeBlock> getTimeBlocks(Long documentId);
	
	public List<TimeBlock> getTimeBlocksForAssignment(Assignment assign);
	
	public void deleteTimeBlocksAssociatedWithDocumentId(String documentId);
	
	/*
	 * Get all the time blocks with the given Clock Log id as the clockLogEndId
	 * @param tkClockLogId
	 * @return List<TimeBlock>	 * 
	 */
	public List<TimeBlock> getTimeBlocksForClockLogEndId(Long tkClockLogId);
	/*
	 * Get all the time blocks with the given Clock Log id as the clockLogBeginId
	 * @param tkClockLogId
	 * @return List<TimeBlock>	 * 
	 */
	public List<TimeBlock> getTimeBlocksForClockLogBeginId(Long tkClockLogId);
	
	public List<TimeBlock> getTimeBlocks();
	public List<TimeBlock> getLatestEndTimestamp();

    List<TimeBlock> getOvernightTimeBlocks(Long clockLogEndId);
}
