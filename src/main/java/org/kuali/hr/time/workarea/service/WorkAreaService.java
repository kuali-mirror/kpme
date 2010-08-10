package org.kuali.hr.time.workarea.service;

import java.util.List;

import org.kuali.hr.time.role.assign.TkRoleAssign;
import org.kuali.hr.time.workarea.WorkArea;

public interface WorkAreaService {

    public WorkArea getWorkArea(Long workAreaId);

    public void saveOrUpdate(WorkArea workArea);
    
    public List<TkRoleAssign> getWorkAreaRoles(Long workAreaId);
    public void saveWorkAreaRoles(Long workAreaId, List<TkRoleAssign> roleList);
}
