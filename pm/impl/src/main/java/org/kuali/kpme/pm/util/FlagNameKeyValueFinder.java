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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.pm.flag.Flag;
import org.kuali.kpme.pm.positionflag.PositionFlag;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

public class FlagNameKeyValueFinder extends UifKeyValuesFinderBase {
	private static final long serialVersionUID = 1L;

	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		MaintenanceDocumentForm docForm = (MaintenanceDocumentForm) model;
		List<KeyValue> options = new ArrayList<KeyValue>();
		HrBusinessObject anHrObject = (HrBusinessObject) docForm.getDocument().getNewMaintainableObject().getDataObject();
		LocalDate aDate = anHrObject.getEffectiveLocalDate() != null ? anHrObject.getEffectiveLocalDate() : null;

		Flag aFlagObj = (Flag) docForm.getNewCollectionLines().get("document.newMaintainableObject.dataObject.flagList");
		String category = null;
		if(aFlagObj != null && StringUtils.isNotEmpty(aFlagObj.getCategory())) {
			category = aFlagObj.getCategory();
		}
		List<PositionFlag> flagList = PmServiceLocator.getPositionFlagService().getAllActivePositionFlagsWithCategory(category, aDate);
		if(CollectionUtils.isNotEmpty(flagList)) {
			for(PositionFlag aFlag : flagList) {
				options.add(new ConcreteKeyValue((String) aFlag.getPositionFlagName(), (String) aFlag.getPositionFlagName()));
			}
		}
        return options;
	}

}
