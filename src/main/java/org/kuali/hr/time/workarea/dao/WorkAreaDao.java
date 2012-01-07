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
}
