package org.kuali.hr.time.clocklog;


import java.util.ArrayList;
import java.util.List;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.hr.time.util.TkConstants;


public class FlsaClocklogKeyValue extends org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase{
	@SuppressWarnings("unchecked")
	@Override
	public List getKeyValues(){
        List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();
        for(int i=0; i< TkConstants.ClOCK_ACTIONS.size(); i++)
        {
        	keyValues.add(new KeyLabelPair(TkConstants.ClOCK_ACTIONS.get(i),TkConstants.ClOCK_ACTIONS.get(i)));
        }        		
        return keyValues;
	}
}
