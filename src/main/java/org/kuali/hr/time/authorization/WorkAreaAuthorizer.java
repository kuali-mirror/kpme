package org.kuali.hr.time.authorization;

import org.kuali.rice.kns.bo.BusinessObject;

public class WorkAreaAuthorizer extends DepartmentalRuleAuthorizer {
	
    @Override
    public boolean rolesIndicateWriteAccess(BusinessObject bo) {
    	return rolesIndicateGeneralWriteAccess();
    }

}
