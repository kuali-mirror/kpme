package org.kuali.hr.time.timezone.service;


import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.util.KeyLabelPair;


public class TimeZoneKeyValue extends org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase{
	@SuppressWarnings("unchecked")
	@Override
	public List getKeyValues(){
        List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();
        for(int i=0; i< TkConstants.TIME_ZONES.size(); i++)
        {
        	keyValues.add(new KeyLabelPair(TkConstants.TIME_ZONES.get(i),TkConstants.TIME_ZONES.get(i)));
        }        		
        return keyValues;
	}
}
