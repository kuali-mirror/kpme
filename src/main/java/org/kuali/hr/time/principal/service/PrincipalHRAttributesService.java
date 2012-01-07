package org.kuali.hr.time.principal.service;

import java.util.Date;

import org.kuali.hr.time.principal.PrincipalHRAttributes;

public interface PrincipalHRAttributesService {
	/**
	 * Fetch PrincipalCalendar object at a particular date
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
	public PrincipalHRAttributes getPrincipalCalendar(String principalId, Date asOfDate);
}
