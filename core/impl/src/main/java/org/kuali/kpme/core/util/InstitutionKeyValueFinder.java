/**
 * Copyright 2004-2013 The Kuali Foundation
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
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.institution.InstitutionContract;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

public class InstitutionKeyValueFinder extends UifKeyValuesFinderBase {

	private static final long serialVersionUID = 1L;

	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {

		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		
		if (!StringUtils.contains(model.getFormPostUrl(), "inquiry")) {
			
			MaintenanceDocumentForm docForm = (MaintenanceDocumentForm) model;
			HrBusinessObject anHrObject = (HrBusinessObject) docForm.getDocument().getNewMaintainableObject().getDataObject();
			if (anHrObject.getEffectiveDate() != null) {
				List<? extends InstitutionContract> intList = HrServiceLocator.getInstitutionService().getActiveInstitutionsAsOf(anHrObject.getEffectiveLocalDate());

				if (CollectionUtils.isNotEmpty(intList)) {
					for (InstitutionContract anInstitution : intList) {
						keyValues.add(new ConcreteKeyValue((String) anInstitution.getInstitutionCode(), (String) anInstitution.getInstitutionCode()));
					}
				}
			}
		}
		
		return keyValues;
	}
}
