package org.kuali.hr.lm.util;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

public class FractionOptionsKeyValueFinder extends KeyValuesBase {

	@Override
	public List<KeyValue> getKeyValues() {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("99", "99"));
        keyValues.add(new ConcreteKeyValue("99.9", "99.9"));
        keyValues.add(new ConcreteKeyValue("99.99", "99.99"));
        return keyValues;
	}

}
