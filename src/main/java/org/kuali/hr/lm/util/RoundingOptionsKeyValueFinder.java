package org.kuali.hr.lm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.hr.lm.LMConstants;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

public class RoundingOptionsKeyValueFinder extends KeyValuesBase {

	@SuppressWarnings("unchecked")
	@Override
	public List getKeyValues() {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		for (Map.Entry entry : LMConstants.ROUND_OPTION_MAP.entrySet()) {
			keyValues.add(new ConcreteKeyValue((String) entry.getKey(), (String) entry.getValue()));
        } 
        return keyValues;
	}

}
