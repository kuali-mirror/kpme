package org.kuali.hr.location.service;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

public class TimezoneKeyValueFinder extends KeyValuesBase{

	@Override
	public List getKeyValues() {
		List<KeyValue> timezones = new ArrayList<KeyValue>();
		for(String timeZone : TkConstants.TIME_ZONES){
			timezones.add(new ConcreteKeyValue(timeZone,timeZone));
		}
		return timezones;
	}

}
