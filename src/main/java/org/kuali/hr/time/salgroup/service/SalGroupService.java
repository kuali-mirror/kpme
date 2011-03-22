package org.kuali.hr.time.salgroup.service;

import java.sql.Date;

import org.kuali.hr.time.salgroup.SalGroup;

public interface SalGroupService {
	/**
	 * Fetch a SalGroup as of a particular date
	 * @param salGroup
	 * @param asOfDate
	 * @return
	 */
	public SalGroup getSalGroup(String salGroup, Date asOfDate);
}
