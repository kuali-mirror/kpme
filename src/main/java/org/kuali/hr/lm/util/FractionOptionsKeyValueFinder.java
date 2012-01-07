package org.kuali.hr.lm.util;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

public class FractionOptionsKeyValueFinder extends KeyValuesBase {

	@Override
	public List getKeyValues() {
		List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();
        keyValues.add(new KeyLabelPair("99", "99"));
        keyValues.add(new KeyLabelPair("99.9", "99.9"));
        keyValues.add(new KeyLabelPair("99.99", "99.99"));
        return keyValues;
	}

}
