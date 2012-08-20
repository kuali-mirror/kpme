package org.kuali.hr.time.calendar;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;


public class CalendarTypesKeyValue extends KeyValuesBase {

	@SuppressWarnings("unchecked")
	@Override
	public List getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("Pay", "Pay"));
        keyValues.add(new ConcreteKeyValue("Leave", "Leave"));
        return keyValues;
	}

}
