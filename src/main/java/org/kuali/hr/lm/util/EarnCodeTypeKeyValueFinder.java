package org.kuali.hr.lm.util;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

public class EarnCodeTypeKeyValueFinder extends KeyValuesBase {

	@Override
	public List getKeyValues() {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("T", "Time"));
        keyValues.add(new ConcreteKeyValue("L", "Leave"));
        keyValues.add(new ConcreteKeyValue("B", "Both"));
        return keyValues;
	}

}
