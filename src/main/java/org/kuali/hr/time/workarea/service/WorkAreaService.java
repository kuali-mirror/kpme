package org.kuali.hr.time.workarea.service;

import org.kuali.hr.time.workarea.WorkArea;

public interface WorkAreaService {

    public WorkArea getWorkArea(Long workAreaId);

    public void saveOrUpdate(WorkArea workArea);
}
