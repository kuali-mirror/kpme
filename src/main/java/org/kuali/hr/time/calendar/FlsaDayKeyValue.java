package org.kuali.hr.time.calendar;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;


public class FlsaDayKeyValue extends KeyValuesBase {

	@SuppressWarnings("unchecked")
	@Override
	public List getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("Sun", "Sun"));
        keyValues.add(new ConcreteKeyValue("Mon", "Mon"));
        keyValues.add(new ConcreteKeyValue("Tues", "Tues"));
        keyValues.add(new ConcreteKeyValue("Wed", "Wed"));
        keyValues.add(new ConcreteKeyValue("Thur", "Thur"));
        keyValues.add(new ConcreteKeyValue("Fri", "Fri"));
        keyValues.add(new ConcreteKeyValue("Sat", "Sat"));
        return keyValues;
	}

}
