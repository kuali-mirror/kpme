package org.kuali.kpme.core.bo.paystep.valuesfinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.kpme.core.util.HrConstants;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;

public class ServiceUnitKeyValueFinder extends UifKeyValuesFinderBase {

	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		List<KeyValue> values = new ArrayList<KeyValue>();
		for(Map.Entry entry : HrConstants.SERVICE_UNIT_OF_TIME.entrySet()) {
			values.add(new ConcreteKeyValue((String)entry.getKey(), (String)entry.getValue()));
		}
		return values;
	}

}
