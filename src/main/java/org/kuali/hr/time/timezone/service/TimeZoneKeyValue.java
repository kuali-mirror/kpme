package org.kuali.hr.time.timezone.service;


import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;


public class TimeZoneKeyValue extends KeyValuesBase{
	@SuppressWarnings("unchecked")
	@Override
	public List getKeyValues(){
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        for(int i=0; i< TkConstants.TIME_ZONES.size(); i++)
        {
        	keyValues.add(new ConcreteKeyValue(TkConstants.TIME_ZONES.get(i),TkConstants.TIME_ZONES.get(i)));
        }        		
        return keyValues;
	}
}
