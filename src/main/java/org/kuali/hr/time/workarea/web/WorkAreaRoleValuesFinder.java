package org.kuali.hr.time.workarea.web;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

public class WorkAreaRoleValuesFinder extends KeyValuesBase {

	static final List<KeyLabelPair> labels = new ArrayList<KeyLabelPair>(TkConstants.ROLE_ASSIGNMENT_FOR_WORK_AREA.size());
	static {
		for (String roleKey : TkConstants.ROLE_ASSIGNMENT_FOR_WORK_AREA) {
			labels.add(new KeyLabelPair(roleKey, TkConstants.ALL_ROLES_MAP.get(roleKey)));
		}
	}

	@Override
	public List<KeyLabelPair> getKeyValues() {
		return labels;
	}

}
