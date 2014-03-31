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
package org.kuali.kpme.core.assignment.dao;

import java.sql.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.assignment.AssignmentBo;
import org.kuali.kpme.core.assignment.AssignmentBo;

public interface AssignmentDao {

	/**
	 * Returns all assignments for the provided principalId that are valid as of
	 * the specified payPeriodEndDate.
	 *
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
	public List<AssignmentBo> findAssignments(String principalId, LocalDate asOfDate);
	/**
	 * Save or update the given assignment
	 * @param assignment
	 */
	public void saveOrUpdate(AssignmentBo assignment);
	/**
	 * Save of update the given list of assignments
	 * @param assignments
	 */
	public void saveOrUpdate(List<AssignmentBo> assignments);

	/**
	 * Delete an assignment
	 * @param assignment
	 */
	public void delete(AssignmentBo assignment);

	/**
	 * Get list of active assignments in a given work area as of a particular date
	 * @param workArea
	 * @param asOfDate
	 * @return
	 */
	public List<AssignmentBo> getActiveAssignmentsInWorkArea(Long workArea, LocalDate asOfDate);

    public List<AssignmentBo> getActiveAssignmentsInWorkAreas(List<Long> workAreas, LocalDate asOfDate);

	public AssignmentBo getAssignment(String tkAssignmentId);

    public AssignmentBo getAssignmentForTargetPrincipal(Long job, Long workArea, Long task, LocalDate asOfDate);

	public List<AssignmentBo> getActiveAssignments(LocalDate asOfDate);
	
	public AssignmentBo getAssignment(String principalId, Long jobNumber, Long workArea, Long task, LocalDate asOfDate);
	
	/**
	 * KPME-1129
	 * Get a list of active assignments based on principalId and jobNumber as of a particular date 
	 * @param principalId
	 * @param jobNumber
	 * @param asOfDate
	 * @return
	 */
	public List<AssignmentBo> getActiveAssignmentsForJob(String principalId, Long jobNumber, LocalDate asOfDate);

    List<AssignmentBo> findAssignmentsWithinPeriod(String principalId, LocalDate startDate, LocalDate endDate);

    List<AssignmentBo> searchAssignments(LocalDate fromEffdt, LocalDate toEffdt, String principalId, String jobNumber,
                                    String dept, String workArea, String active, String showHistory);
    
    public AssignmentBo getMaxTimestampAssignment(String principalId);
    
    public List<String> getPrincipalIds(List<String> workAreaList, LocalDate effdt, LocalDate startDate, LocalDate endDate);
    
    public List<AssignmentBo> getAssignments(List<String> workAreaList, LocalDate effdt, LocalDate startDate, LocalDate endDate);

    public List<AssignmentBo> findAssignmentsHistoryForPeriod(String principalId, LocalDate startDate, LocalDate endDate);

    public List<AssignmentBo> findAssignmentsWithinPeriod(String principalId, LocalDate startDate, LocalDate endDate, boolean requireActive);
}
