package org.kuali.hr.time.salgroup.service;

import java.sql.Date;

import org.kuali.hr.time.salgroup.SalGroup;

public interface SalGroupService {

	public SalGroup getSalGroup(String salGroup, Date asOfDate);
}
