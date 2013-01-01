/**
 * Copyright 2004-2013 The Kuali Foundation
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

import org.kuali.hr.time.workschedule.WorkScheduleAssignment;
import org.kuali.hr.time.workschedule.dao.WorkScheduleAssignmentDao;

public class WorkScheduleAssignmentServiceImpl implements WorkScheduleAssignmentService {

    private WorkScheduleAssignmentDao workScheduleAssignmentDao;

    @Override
    public void saveOrUpdate(WorkScheduleAssignment wsa) {
        workScheduleAssignmentDao.saveOrUpdate(wsa);
    }

    @Override
    public List<WorkScheduleAssignment> getWorkScheduleAssignments(String principalId, String department, Long workArea, Date asOfDate) {
        List<WorkScheduleAssignment> list = null;
        // wild-cards, 2^3 again ...

        // principal, dept, workArea
        list = workScheduleAssignmentDao.getWorkScheduleAssignments(principalId, department, workArea, asOfDate);

        // principal, dept, -1
        if (list.isEmpty())
            list = workScheduleAssignmentDao.getWorkScheduleAssignments(principalId, department, -1L, asOfDate);

        // principal, *, workArea
        if (list.isEmpty())
            list = workScheduleAssignmentDao.getWorkScheduleAssignments(principalId, "%", workArea, asOfDate);

        // principal, *, -1
        if (list.isEmpty())
            list = workScheduleAssignmentDao.getWorkScheduleAssignments(principalId, "%", -1L, asOfDate);

        // *, dept, workArea
        if (list.isEmpty())
            list = workScheduleAssignmentDao.getWorkScheduleAssignments("%", department, workArea, asOfDate);

        // *, dept, -1
        if (list.isEmpty())
            list = workScheduleAssignmentDao.getWorkScheduleAssignments("%", department, -1L, asOfDate);

        // *, *, workArea
        if (list.isEmpty())
            list = workScheduleAssignmentDao.getWorkScheduleAssignments("%", "%", workArea, asOfDate);

        // *, *, -1
        if (list.isEmpty())
            list = workScheduleAssignmentDao.getWorkScheduleAssignments("%", "%", -1L, asOfDate);

        return list;
    }

    public void setWorkScheduleAssignmentDao(WorkScheduleAssignmentDao workScheduleAssignmentDao) {
        this.workScheduleAssignmentDao = workScheduleAssignmentDao;
    }
}
