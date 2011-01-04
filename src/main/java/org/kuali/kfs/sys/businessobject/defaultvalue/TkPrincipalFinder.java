package org.kuali.kfs.sys.businessobject.defaultvalue;

import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.lookup.valueFinder.ValueFinder;

public class TkPrincipalFinder implements ValueFinder {

	@Override
	public String getValue() {
		return TKContext.getPrincipalId();
	}

}
