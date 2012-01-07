package org.kuali.hr.lm.util;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

public class RoundingOptionsKeyValueFinder extends KeyValuesBase {

	@Override
	public List getKeyValues() {
		List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();
        keyValues.add(new KeyLabelPair("T", "Traditional"));
        keyValues.add(new KeyLabelPair("R", "Truncate"));
        return keyValues;
	}

}
