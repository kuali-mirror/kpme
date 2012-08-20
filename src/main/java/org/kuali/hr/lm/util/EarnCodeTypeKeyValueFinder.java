package org.kuali.hr.lm.util;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.lm.earncodesec.EarnCodeType;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

public class EarnCodeTypeKeyValueFinder extends KeyValuesBase {

	@Override
	public List getKeyValues() {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue(EarnCodeType.TIME.getCode(), "Time"));
        keyValues.add(new ConcreteKeyValue(EarnCodeType.LEAVE.getCode(), "Leave"));
        keyValues.add(new ConcreteKeyValue(EarnCodeType.BOTH.getCode(), "Both"));
        return keyValues;
	}

}
