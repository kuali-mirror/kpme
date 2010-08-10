package org.kuali.hr.time.workarea.dao;

import org.kuali.hr.time.workarea.WorkArea;

public interface WorkAreaDao {

    public WorkArea getWorkArea(Long workAreaId);
    public void saveOrUpdate(WorkArea workArea);
}
