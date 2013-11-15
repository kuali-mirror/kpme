package org.kuali.kpme.pm.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.kpme.pm.api.positiondepartmentaffiliation.PositionDepartmentAffiliationContract;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

public class DepartmentAffiliationKeyValueFinder extends KeyValuesBase {

	private static final long serialVersionUID = 1L;

	@Override
	public List<KeyValue> getKeyValues() {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		List<? extends PositionDepartmentAffiliationContract> affilList = PmServiceLocator.getPositionDepartmentAffiliationService().getAllActiveAffiliations();
		keyValues.add(new ConcreteKeyValue("", ""));
		if(CollectionUtils.isNotEmpty(affilList)) {
			for(PositionDepartmentAffiliationContract anAffil : affilList) {
				keyValues.add(new ConcreteKeyValue((String) anAffil.getPositionDeptAfflType(), (String) anAffil.getPositionDeptAfflType()));
			}
		}         
		return keyValues;
	}
}
