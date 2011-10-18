package org.kuali.hr.lm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

public class UnitOfTimeKeyValueFinder extends KeyValuesBase {

	@Override
	public List getKeyValues() {
		List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();
		for (Map.Entry entry : TkConstants.UNIT_OF_TIME.entrySet()) {
            keyValues.add(new KeyLabelPair(entry.getKey(), (String)entry.getValue()));
        }     		
        return keyValues;
	}

}
