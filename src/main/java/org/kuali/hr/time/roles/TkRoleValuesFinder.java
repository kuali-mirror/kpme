package org.kuali.hr.time.roles;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

public class TkRoleValuesFinder extends KeyValuesBase {

	public List<KeyLabelPair> getKeyValues() {
		List<KeyLabelPair> chartKeyLabels = new ArrayList<KeyLabelPair>();
		chartKeyLabels.add(new KeyLabelPair("", ""));
		
		chartKeyLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_APPROVER, "Approver"));
		chartKeyLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_SYS_ADMIN, "System Admin"));
		chartKeyLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_ORG_ADMIN, "Org Admin"));
		
		return chartKeyLabels;
	}
}
