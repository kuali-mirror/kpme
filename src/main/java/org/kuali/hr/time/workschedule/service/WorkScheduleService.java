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
package org.kuali.hr.time.workschedule.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.workschedule.WorkSchedule;
import org.kuali.hr.time.workschedule.WorkScheduleEntry;

public interface WorkScheduleService {

	public void saveOrUpdate(WorkSchedule workSchedule);

	public void saveOrUpdate(List<WorkSchedule> workSchedules);

    public WorkSchedule getWorkSchedule(Long workScheduleId, Date asOfDate);

    /**
     * Takes a work schedule and flattens it out based on effdt and offset passed in from beginDateTime, endDateTime
     *
     */
	public List<WorkScheduleEntry> getWorkSchedEntries(WorkSchedule workSchedule, java.util.Date beginDateTime, java.util.Date endDateTime);

}
