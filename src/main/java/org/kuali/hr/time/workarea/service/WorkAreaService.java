package org.kuali.hr.time.workarea.service;

import java.sql.Date;

import org.kuali.hr.time.workarea.WorkArea;

public interface WorkAreaService {
	/**
	 * Fetch WorkArea as of a particular date
	 * @param workArea
	 * @param asOfDate
	 * @return
	 */
    public WorkArea getWorkArea(Long workArea, Date asOfDate);
    /**
     * Save or Update given work area
     * @param workArea
     */
    public void saveOrUpdate(WorkArea workArea);
}
