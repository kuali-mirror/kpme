package org.kuali.hr.lm.util;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

// KPME-1268 Kagata
public class RecordLeaveKeyValueFinder extends KeyValuesBase {

	@Override
	public List getKeyValues() {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("LM", "Leave Management"));
        keyValues.add(new ConcreteKeyValue("T", "Timesheet"));
        keyValues.add(new ConcreteKeyValue("NA", "Not Applicable"));
        return keyValues;
	}

}
