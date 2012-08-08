package org.kuali.hr.time.salgroup.service;

import org.kuali.hr.time.salgroup.SalGroup;
import org.springframework.cache.annotation.Cacheable;

import java.sql.Date;

public interface SalGroupService {
	/**
	 * Fetch a SalGroup as of a particular date
	 * @param salGroup
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= SalGroup.CACHE_NAME, key="'salGroup=' + #p0 + '|' + 'asOfDate=' + #p1")
	public SalGroup getSalGroup(String salGroup, Date asOfDate);

    @Cacheable(value= SalGroup.CACHE_NAME, key="'hrSalGroupId=' + #p0")
	public SalGroup getSalGroup(String hrSalGroupId);
	
	public int getSalGroupCount(String salGroup);
}
