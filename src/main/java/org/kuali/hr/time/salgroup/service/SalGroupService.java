package org.kuali.hr.time.salgroup.service;

import org.kuali.hr.time.salgroup.SalGroup;

import java.sql.Date;

public interface SalGroupService {
	/**
	 * Fetch a SalGroup as of a particular date
	 * @param salGroup
	 * @param asOfDate
	 * @return
	 */
	public SalGroup getSalGroup(String salGroup, Date asOfDate);
	
	public SalGroup getSalGroup(String hrSalGroupId);
	
	public int getSalGroupCount(String salGroup);
}
