package org.kuali.hr.time.util;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

public class TkHistoryKeyValueFinder extends KeyValuesBase {

	@Override
	public List getKeyValues() {
		List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();
        keyValues.add(new KeyLabelPair("Y", "Yes"));     		
        keyValues.add(new KeyLabelPair("N", "No"));     		
        return keyValues;
	}

}
