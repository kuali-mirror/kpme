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
package org.kuali.kpme.tklm.time.timeblock.dao;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockBo;

import java.util.List;

public interface TimeBlockDao {

	public void saveOrUpdate(TimeBlockBo timeBlock);

	public void saveOrUpdate(List<TimeBlockBo> timeBlockList);

	public void deleteTimeBlock(TimeBlockBo timeBlock);

	public TimeBlockBo getTimeBlock(String timeBlockId);
	
	public List<TimeBlockBo> getTimeBlocks(String documentId);
	
	public List<TimeBlockBo> getTimeBlocksForAssignment(Assignment assign);
	
	public void deleteTimeBlocksAssociatedWithDocumentId(String documentId);
	
	/*
	 * Get all the time blocks with the given Clock Log id as the clockLogEndId
	 * @param tkClockLogId
	 * @return List<TimeBlock>	 * 
	 */
	public List<TimeBlockBo> getTimeBlocksForClockLogEndId(String tkClockLogId);
	/*
	 * Get all the time blocks with the given Clock Log id as the clockLogBeginId
	 * @param tkClockLogId
	 * @return List<TimeBlock>	 * 
	 */
	public List<TimeBlockBo> getTimeBlocksForClockLogBeginId(String tkClockLogId);
	
	public List<TimeBlockBo> getLatestEndTimestampForEarnCode(String earnCode);

    List<TimeBlockBo> getOvernightTimeBlocks(String clockLogEndId);
    
    public List<TimeBlockBo> getTimeBlocksWithEarnCode(String earnCode, DateTime effDate);

	public List<TimeBlockBo> getTimeBlocksForLookup(String documentId,
			String principalId, String userPrincipalId, LocalDate fromDate,
			LocalDate toDate);

    public List<TimeBlockBo> getIntersectingTimeBlocks(String principalId, Interval interval);
}
