package org.kuali.hr.time.workarea.web;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

public class WorkAreaOvertimePreferenceValuesFinder extends KeyValuesBase {

	static final List<KeyValue> labels = new ArrayList<KeyValue>(2);
	static {
		labels.add(new ConcreteKeyValue("OT1", "Arbitrary IU OT 1"));
		labels.add(new ConcreteKeyValue("OT2", "Arbitrary IU OT 2"));
	}

	@Override
	public List<KeyValue> getKeyValues() {
		return labels;
	}
}
