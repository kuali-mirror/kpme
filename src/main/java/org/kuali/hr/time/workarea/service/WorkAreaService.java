package org.kuali.hr.time.workarea.service;

import java.sql.Date;

import org.kuali.hr.time.workarea.WorkArea;

public interface WorkAreaService {

    public WorkArea getWorkArea(Long workAreaId, Date asOfDate);
    public void saveOrUpdate(WorkArea workArea);
}
