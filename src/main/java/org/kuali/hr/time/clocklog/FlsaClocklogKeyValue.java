package org.kuali.hr.time.clocklog;


import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;


public class FlsaClocklogKeyValue extends KeyValuesBase{
	@SuppressWarnings("unchecked")
	@Override
	public List getKeyValues(){
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        for(int i=0; i< TkConstants.ClOCK_ACTIONS.size(); i++)
        {
        	keyValues.add(new ConcreteKeyValue(TkConstants.ClOCK_ACTIONS.get(i),TkConstants.ClOCK_ACTIONS.get(i)));
        }        		
        return keyValues;
	}
}
