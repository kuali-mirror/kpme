/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.timeblock.dao;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.timeblock.TimeBlock;

import java.sql.Date;
import java.util.List;

public interface TimeBlockDao {

	public void saveOrUpdate(TimeBlock timeBlock);

	public void saveOrUpdate(List<TimeBlock> timeBlockList);

	public void deleteTimeBlock(TimeBlock timeBlock);

	public TimeBlock getTimeBlock(String timeBlockId);
	
	public List<TimeBlock> getTimeBlocks(String documentId);
	
	public List<TimeBlock> getTimeBlocksForAssignment(Assignment assign);
	
	public void deleteTimeBlocksAssociatedWithDocumentId(String documentId);
	
	/*
	 * Get all the time blocks with the given Clock Log id as the clockLogEndId
	 * @param tkClockLogId
	 * @return List<TimeBlock>	 * 
	 */
	public List<TimeBlock> getTimeBlocksForClockLogEndId(String tkClockLogId);
	/*
	 * Get all the time blocks with the given Clock Log id as the clockLogBeginId
	 * @param tkClockLogId
	 * @return List<TimeBlock>	 * 
	 */
	public List<TimeBlock> getTimeBlocksForClockLogBeginId(String tkClockLogId);
	
	public List<TimeBlock> getTimeBlocks();
	public List<TimeBlock> getLatestEndTimestamp();

    List<TimeBlock> getOvernightTimeBlocks(String clockLogEndId);
    
    public List<TimeBlock> getTimeBlocksWithEarnCode(String earnCode, Date effDate);
}
