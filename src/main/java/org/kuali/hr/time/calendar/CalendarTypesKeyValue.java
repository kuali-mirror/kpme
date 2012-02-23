package org.kuali.hr.time.calendar;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;


public class CalendarTypesKeyValue extends KeyValuesBase {

	@SuppressWarnings("unchecked")
	@Override
	public List getKeyValues() {
        List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();
        keyValues.add(new KeyLabelPair("Pay", "Pay"));
        keyValues.add(new KeyLabelPair("Leave", "Leave"));
        return keyValues;
	}

}
