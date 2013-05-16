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
package org.kuali.kpme.pm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.kpme.pm.PMConstants;
import org.kuali.kpme.pm.classification.ClassificationQualification;
import org.kuali.kpme.pm.pstnqlfrtype.PstnQlfrType;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

public class PositionClassQualifierKeyValueFinder extends UifKeyValuesFinderBase{
	private static final long serialVersionUID = 1L;

	@Override
	public List<KeyValue> getKeyValues() {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		for (Map.Entry entry : PMConstants.PSTN_CLSS_QLFR_VALUE_MAP.entrySet()) {
            keyValues.add(new ConcreteKeyValue((String) entry.getKey(), (String) entry.getValue()));
        }            
		return keyValues;
	}

	@Override
    public List<KeyValue> getKeyValues(ViewModel model) {
		MaintenanceDocumentForm docForm = (MaintenanceDocumentForm) model;
		List<KeyValue> options = new ArrayList<KeyValue>();

		ClassificationQualification aQualification = (ClassificationQualification) docForm.getNewCollectionLines().get("document.newMaintainableObject.dataObject.qualificationList");
		if(aQualification != null) {
			String aTypeId = aQualification.getQualificationType();
			PstnQlfrType aTypeObj = PmServiceLocator.getPstnQlfrTypeService().getPstnQlfrTypeById(aTypeId);
			if(aTypeObj != null) {
				if(aTypeObj.getTypeValue().equals(PMConstants.PSTN_QLFR_TYPE_VALUE.TEXT)
						|| aTypeObj.getTypeValue().equals(PMConstants.PSTN_QLFR_TYPE_VALUE.SELECT)) {
					 options.add(new ConcreteKeyValue(PMConstants.PSTN_CLSS_QLFR_VALUE.EQUAL, PMConstants.PSTN_CLSS_QLFR_VALUE_MAP.get(PMConstants.PSTN_CLSS_QLFR_VALUE.EQUAL)));
				} else if(aTypeObj.getTypeValue().equals(PMConstants.PSTN_QLFR_TYPE_VALUE.NUMBER)){
					options = this.getKeyValues();
				}
			}
			
		}
		
        return options;
    }
}
