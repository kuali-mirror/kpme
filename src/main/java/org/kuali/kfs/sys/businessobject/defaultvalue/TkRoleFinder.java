package org.kuali.kfs.sys.businessobject.defaultvalue;

import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.lookup.valueFinder.ValueFinder;

public class TkRoleFinder implements ValueFinder {

	@Override
	public String getValue() {
		return TkConstants.ROLE_TK_APPROVER;
	}

}
