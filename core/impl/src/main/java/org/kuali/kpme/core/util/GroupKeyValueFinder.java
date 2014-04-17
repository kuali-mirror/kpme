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
package org.kuali.kpme.core.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

public class GroupKeyValueFinder extends UifKeyValuesFinderBase {

	private static final long serialVersionUID = 1L;

	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		MaintenanceDocumentForm docForm = (MaintenanceDocumentForm) model;
		HrKeyedBusinessObject anHrKeyObject = (HrKeyedBusinessObject) docForm.getDocument().getNewMaintainableObject().getDataObject();
		LocalDate aDate = anHrKeyObject.getEffectiveLocalDate() != null ? anHrKeyObject.getEffectiveLocalDate() : null;

		if (aDate != null) {
			List<? extends HrGroupKey> groupKeyList = HrServiceLocator.getHrGroupKeyService().getAllActiveHrGroupKeys(aDate);
			if(CollectionUtils.isNotEmpty(groupKeyList)) {
				for(HrGroupKey groupKey : groupKeyList) {
					keyValues.add(new ConcreteKeyValue((String)groupKey.getGroupKeyCode(), (String)groupKey.getGroupKeyCode()));
				}	
			}			
		}
		
		return keyValues;
	}
}
