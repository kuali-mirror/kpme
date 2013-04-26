package org.kuali.hr.tklm.leave.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.hr.tklm.common.TkConstants;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;

public class ServiceUnitKeyValueFinder extends UifKeyValuesFinderBase {

	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		List<KeyValue> values = new ArrayList<KeyValue>();
		for(Map.Entry entry : TkConstants.SERVICE_UNIT_OF_TIME.entrySet()) {
			values.add(new ConcreteKeyValue((String)entry.getKey(), (String)entry.getValue()));
		}
		return values;
	}

}
