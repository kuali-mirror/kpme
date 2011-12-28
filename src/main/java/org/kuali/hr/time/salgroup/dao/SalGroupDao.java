package org.kuali.hr.time.salgroup.dao;

import org.kuali.hr.time.salgroup.SalGroup;

import java.sql.Date;

public interface SalGroupDao {
	public void saveOrUpdate(SalGroup salGroup);
	public SalGroup getSalGroup(String salGroup, Date asOfDate);
	public SalGroup getSalGroup(String hrSalGroupId);

}
