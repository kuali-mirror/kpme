package org.kuali.hr.time.workarea.web;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

public class WorkAreaOvertimePreferenceValuesFinder extends KeyValuesBase {

	static final List<KeyLabelPair> labels = new ArrayList<KeyLabelPair>(2);
	static {
		labels.add(new KeyLabelPair("OT1", "Arbitrary IU OT 1"));
		labels.add(new KeyLabelPair("OT2", "Arbitrary IU OT 2"));
	}

	@Override
	public List<KeyLabelPair> getKeyValues() {
		return labels;
	}
}
