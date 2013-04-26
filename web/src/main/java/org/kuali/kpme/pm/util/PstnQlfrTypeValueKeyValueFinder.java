package org.kuali.kpme.pm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.kpme.pm.PMConstants;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

public class PstnQlfrTypeValueKeyValueFinder extends KeyValuesBase {

	private static final long serialVersionUID = 1L;

	@Override
	public List<KeyValue> getKeyValues() {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		for (Map.Entry entry : PMConstants.PSTN_QLFR_TYPE_VALUE_MAP.entrySet()) {
            keyValues.add(new ConcreteKeyValue((String) entry.getKey(), (String) entry.getValue()));
        }           
		return keyValues;
	}

}
