package org.kuali.hr.time.util;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

public class TkFlsaStatusKeyValueFinder extends KeyValuesBase {

	@Override
	public List getKeyValues() {
		List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();
        keyValues.add(new KeyLabelPair(TkConstants.FLSA_STATUS_NON_EXEMPT, "Non-Exempt"));     		
        keyValues.add(new KeyLabelPair(TkConstants.FLSA_STATUS_EXEMPT, "Exempt"));     		
        return keyValues;
	}

}
