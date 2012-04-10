package org.kuali.hr.lm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

public class AccrualEarnIntervalKeyValueFinder extends KeyValuesBase {

	@Override
	public List getKeyValues() {
		List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();
		// KPME-1347 Kagata Use LMConstant instead of TKConstant
		//for (Map.Entry entry : TkConstants.ACCRUAL_EARN_INTERVAL.entrySet()) {
		for (Map.Entry entry : LMConstants.ACCRUAL_EARN_INTERVAL_MAP.entrySet()) {
			keyValues.add(new KeyLabelPair(entry.getKey(), (String)entry.getValue()));
        }     		
        return keyValues;
	}

}
