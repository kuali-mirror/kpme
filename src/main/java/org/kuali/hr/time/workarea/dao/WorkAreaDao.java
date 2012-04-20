package org.kuali.hr.time.workarea.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.workarea.WorkArea;

public interface WorkAreaDao {

    public WorkArea getWorkArea(Long workArea, Date asOfDate);
    public List<WorkArea> getWorkArea(String department, Date asOfDate);
    public void saveOrUpdate(WorkArea workArea);
    public WorkArea getWorkArea(String tkWorkAreaId);
    public Long getNextWorkAreaKey();
    public List<WorkArea> getWorkAreas(String dept, String workArea, String workAreaDescr, Date fromEffdt, Date toEffdt,
    									String active, String showHistory);
    public int getWorkAreaCount(String dept, Long workArea);
}
