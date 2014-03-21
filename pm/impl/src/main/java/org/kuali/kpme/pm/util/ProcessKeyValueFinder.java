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
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.pm.PMConstants;
import org.kuali.kpme.pm.api.positionflag.PositionFlagContract;
import org.kuali.kpme.pm.api.pstnqlfrtype.PstnQlfrTypeContract;
import org.kuali.kpme.pm.classification.qual.ClassificationQualification;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

public class ProcessKeyValueFinder extends UifKeyValuesFinderBase {

	private static final long serialVersionUID = 1L;

	@Override
	public List<KeyValue> getKeyValues() {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("",""));
		for(String aString : PMConstants.PSTN_PROCESS_LIST) { 
			if(!aString.equals(PMConstants.PSTN_PROCESS_NEW)){
				keyValues.add(new ConcreteKeyValue(aString, aString));
			}	
		}
		return keyValues;
	}
	
	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		List<KeyValue> options = new ArrayList<KeyValue>();
		MaintenanceDocumentForm docForm = (MaintenanceDocumentForm) model; 
		PositionBo aPosition = (PositionBo) docForm.getDocument().getNewMaintainableObject().getDataObject();

		if(aPosition.getPositionNumber() != null) {
			options = this.getKeyValues();
		} else {
			options.add(new ConcreteKeyValue(PMConstants.PSTN_PROCESS_NEW, PMConstants.PSTN_PROCESS_NEW));
		}

        return options;
    }
}
