package org.kuali.hr.time.salgroup.dao;

import java.sql.Date;

import org.kuali.hr.time.salgroup.SalGroup;

public interface SalGroupDao {
	public void saveOrUpdate(SalGroup salGroup);
	public SalGroup getSalGroup(String salGroup, Date asOfDate);

}
