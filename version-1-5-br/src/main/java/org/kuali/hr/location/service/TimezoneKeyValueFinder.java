package org.kuali.hr.location.service;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

public class TimezoneKeyValueFinder extends KeyValuesBase{

	@Override
	public List getKeyValues() {
		List<KeyLabelPair> timezones = new ArrayList<KeyLabelPair>();
		for(String timeZone : TkConstants.TIME_ZONES){
			timezones.add(new KeyLabelPair(timeZone,timeZone));
		}
		return timezones;
	}

}
