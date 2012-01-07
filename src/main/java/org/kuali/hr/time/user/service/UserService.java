package org.kuali.hr.time.user.service;

import java.sql.Date;

import org.kuali.hr.time.util.TKUser;
import org.kuali.rice.kim.bo.Person;

public interface UserService {
    public TKUser buildTkUser(String actualPrincipalId, Date asOfDate);
    public TKUser buildTkUser(Person actual, Person backdoor, Person target, Date asOfDate);
}
