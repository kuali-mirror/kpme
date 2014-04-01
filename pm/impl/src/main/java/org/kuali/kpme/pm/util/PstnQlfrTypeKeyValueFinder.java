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
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.pm.api.pstnqlfrtype.PstnQlfrTypeContract;
import org.kuali.kpme.pm.classification.ClassificationBo;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

public class PstnQlfrTypeKeyValueFinder extends UifKeyValuesFinderBase{

	private static final long serialVersionUID = 1L;

	@Override
	public List<KeyValue> getKeyValues() {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		List<? extends PstnQlfrTypeContract> typeList = PmServiceLocator.getPstnQlfrTypeService().getAllActivePstnQlfrTypes(LocalDate.now());
		keyValues.add(new ConcreteKeyValue("", ""));
		if(CollectionUtils.isNotEmpty(typeList)) {
			for(PstnQlfrTypeContract aType : typeList) {
				keyValues.add(new ConcreteKeyValue((String) aType.getPmPstnQlfrTypeId(), (String) aType.getType()));
			}
		}         
		return keyValues;
	}

	
	@Override
    public List<KeyValue> getKeyValues(ViewModel model) {
		MaintenanceDocumentForm docForm = (MaintenanceDocumentForm) model;
		HrBusinessObject anHrObject = (HrBusinessObject) docForm.getDocument().getNewMaintainableObject().getDataObject();
		LocalDate asOfDate = LocalDate.now();
		if(anHrObject.getEffectiveLocalDate() != null) {
			asOfDate = anHrObject.getEffectiveLocalDate();
		}
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		List<? extends PstnQlfrTypeContract> typeList = PmServiceLocator.getPstnQlfrTypeService().getAllActivePstnQlfrTypes(asOfDate);
		keyValues.add(new ConcreteKeyValue("", ""));
		if(CollectionUtils.isNotEmpty(typeList)) {
			for(PstnQlfrTypeContract aType : typeList) {
				keyValues.add(new ConcreteKeyValue((String) aType.getPmPstnQlfrTypeId(), (String) aType.getType()));
			}
		}         
		return keyValues;
	}
	
}
