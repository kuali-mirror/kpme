/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.pm.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.kpme.core.api.departmentaffiliation.DepartmentAffiliationContract;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

public class DepartmentAffiliationKeyValueFinder extends KeyValuesBase {

	private static final long serialVersionUID = 1L;

	@Override
	public List<KeyValue> getKeyValues() {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		List<? extends DepartmentAffiliationContract> affilList = HrServiceLocator.getDepartmentAffiliationService().getAllActiveAffiliations();
		keyValues.add(new ConcreteKeyValue("", ""));
		if(CollectionUtils.isNotEmpty(affilList)) {
			for(DepartmentAffiliationContract anAffil : affilList) {
                if (!anAffil.isPrimaryIndicator()) {
				    keyValues.add(new ConcreteKeyValue((String) anAffil.getDeptAfflType(), (String) anAffil.getDeptAfflType()));
                }
			}
		}         
		return keyValues;
	}
}
