package org.kuali.hr.lm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.earncodesec.EarnCodeType;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

public class EarnCodeTypeKeyValueFinder extends KeyValuesBase {

	@Override
	public List getKeyValues() {
		List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();
        keyValues.add(new KeyLabelPair(EarnCodeType.TIME.getCode(), "Time"));
        keyValues.add(new KeyLabelPair(EarnCodeType.LEAVE.getCode(), "Leave"));
        keyValues.add(new KeyLabelPair(EarnCodeType.BOTH.getCode(), "Both"));
        return keyValues;
	}

}
