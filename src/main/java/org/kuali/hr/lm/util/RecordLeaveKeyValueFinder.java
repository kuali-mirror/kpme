package org.kuali.hr.lm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

// KPME-1268 Kagata
public class RecordLeaveKeyValueFinder extends KeyValuesBase {

	@Override
	public List getKeyValues() {
		List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();
        keyValues.add(new KeyLabelPair("LM", "Leave Management"));
        keyValues.add(new KeyLabelPair("T", "Timesheet"));
        keyValues.add(new KeyLabelPair("NA", "Not Applicable"));
        return keyValues;
	}

}
