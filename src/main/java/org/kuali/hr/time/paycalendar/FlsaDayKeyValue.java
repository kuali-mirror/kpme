package org.kuali.hr.time.paycalendar;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.util.KeyLabelPair;


public class FlsaDayKeyValue extends org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase {

	@SuppressWarnings("unchecked")
	@Override
	public List getKeyValues() {
        List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();
        keyValues.add(new KeyLabelPair("Sun", "Sun"));
        keyValues.add(new KeyLabelPair("Mon", "Mon"));
        keyValues.add(new KeyLabelPair("Tues", "Tues"));
        keyValues.add(new KeyLabelPair("Wed", "Wed"));
        keyValues.add(new KeyLabelPair("Thur", "Thur"));
        keyValues.add(new KeyLabelPair("Fri", "Fri"));
        keyValues.add(new KeyLabelPair("Sat", "Sat"));
        return keyValues;
	}

}
