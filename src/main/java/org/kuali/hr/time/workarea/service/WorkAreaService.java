package org.kuali.hr.time.workarea.service;

import org.kuali.hr.time.workarea.WorkArea;

import java.sql.Date;
import java.util.List;

public interface WorkAreaService {
	/**
	 * Fetch WorkArea as of a particular date
	 * @param workArea
	 * @param asOfDate
	 * @return
	 */
    public WorkArea getWorkArea(Long workArea, Date asOfDate);

    /**
     * Fetch a List of WorkArea objects for a given department as of the
     * indicated date.
     *
     * @param department The department we want to use.
     * @param asOfDate An effective date.
     * @return A List<WorkArea> that matches the provided params.
     */
    public List<WorkArea> getWorkAreas(String department, Date asOfDate);

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
    
    public WorkArea getWorkArea(String tkWorkAreaId);
    
    public Long getNextWorkAreaKey();
    
    public List<WorkArea> getWorkAreas(String dept, String workArea, String workAreaDescr, Date fromEffdt, Date toEffdt,
			String active, String showHistory);
}
