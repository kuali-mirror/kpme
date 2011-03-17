package org.kuali.hr.time.workschedule.service;

import org.kuali.hr.time.workschedule.WorkScheduleAssignment;
import org.kuali.hr.time.workschedule.dao.WorkScheduleAssignmentDao;

import java.sql.Date;
import java.util.List;

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
