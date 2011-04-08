package org.kuali.hr.time.workarea.dao;

import org.kuali.hr.time.workarea.WorkArea;

import java.sql.Date;
import java.util.List;

public interface WorkAreaDao {

    public WorkArea getWorkArea(Long workArea, Date asOfDate);
    public List<WorkArea> getWorkArea(String department, Date asOfDate);
    public void saveOrUpdate(WorkArea workArea);
}
