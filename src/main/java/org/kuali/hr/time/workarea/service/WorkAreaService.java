package org.kuali.hr.time.workarea.service;

import org.kuali.hr.time.workarea.WorkArea;

import java.sql.Date;

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

    /**
     * A helper method to populate the roles for the given WorkArea. This
     * method will be called automatically when calls to getWorkArea() are
     * made. Functionality is exposed here to allow the Kuali Lookup / Maint
     * pages to completely populate WorkArea objects.
     *
     * @param workArea The WorkArea for which we need roles populated.
     */
    public void populateWorkAreaRoles(WorkArea workArea);
}
