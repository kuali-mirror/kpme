package org.kuali.hr.time.workarea.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.role.assign.TkRoleAssign;
import org.kuali.hr.time.workarea.WorkArea;

public interface WorkAreaService {

    public WorkArea getWorkArea(Long workAreaId, Date asOfDate);

    public void saveOrUpdate(WorkArea workArea);

    /**
     * Returns the List<TkRoleAssign> for the currently active "Work Area" 
     * based on the provided work area name.  If you want to retrieve a 
     * specific List<TkRoleAssign> from a given workAreaId(Row ID), this is not
     * the correct call!
     * 
     * @param workAreaId
     * @return
     */
    public List<TkRoleAssign> getWorkAreaRoles(Long workArea);
    public void saveWorkAreaRoles(Long workAreaId, List<TkRoleAssign> roleList);
}
