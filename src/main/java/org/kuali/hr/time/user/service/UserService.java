package org.kuali.hr.time.user.service;

import java.sql.Date;

import org.kuali.hr.time.util.TKUser;

public interface UserService {
	public TKUser buildTkUser(String principalId, Date asOfDate);
}
