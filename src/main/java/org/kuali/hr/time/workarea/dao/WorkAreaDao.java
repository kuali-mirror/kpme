package org.kuali.hr.time.workarea.dao;

import java.sql.Date;

import org.kuali.hr.time.workarea.WorkArea;

public interface WorkAreaDao {

    public WorkArea getWorkArea(Long workArea, Date asOfDate);
    public void saveOrUpdate(WorkArea workArea);
}
