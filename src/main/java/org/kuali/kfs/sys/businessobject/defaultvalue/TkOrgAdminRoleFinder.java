package org.kuali.kfs.sys.businessobject.defaultvalue;

import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.krad.valuefinder.ValueFinder;

public class TkOrgAdminRoleFinder implements ValueFinder {

	@Override
	public String getValue() {
		return TkConstants.ROLE_TK_DEPT_ADMIN;
	}

}
