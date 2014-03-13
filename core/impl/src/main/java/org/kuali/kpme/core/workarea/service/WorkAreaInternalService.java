package org.kuali.kpme.core.workarea.service;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.workarea.WorkAreaBo;

import java.util.List;


public interface WorkAreaInternalService {
    WorkAreaBo getWorkArea(String tkWorkAreaId);
    List<WorkAreaBo> getWorkAreas(String userPrincipalId, String dept, String workArea, String workAreaDescr, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory);

    /**
     * Fetch WorkArea as of a particular date with populated tasks and kim role memberships
     * @param workArea
     * @param asOfDate
     * @return
     */
    WorkAreaBo getWorkArea(Long workArea, LocalDate asOfDate);
}
