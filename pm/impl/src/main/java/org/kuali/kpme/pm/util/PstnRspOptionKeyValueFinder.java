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
import org.kuali.kpme.pm.api.positionresponsibilityoption.PositionResponsibilityOptionContract;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.kpme.pm.positionresponsibility.PositionResponsibilityBo;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

public class PstnRspOptionKeyValueFinder extends UifKeyValuesFinderBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6768131853656090086L;

	@Override
	public List<KeyValue> getKeyValues() {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		List<? extends PositionResponsibilityOptionContract> typeList = PmServiceLocator.getPositionResponsibilityOptionService().getAllActivePstnRspOptions();
		keyValues.add(new ConcreteKeyValue("", ""));
		if(CollectionUtils.isNotEmpty(typeList)) {
			for(PositionResponsibilityOptionContract aType : typeList) {
				keyValues.add(new ConcreteKeyValue((String) aType.getPrOptionId(), (String) aType.getPrOptionName()));
			}
		}         
		return keyValues;
	}
	
	// KPME-2360
	@Override
    public List<KeyValue> getKeyValues(ViewModel model, InputField field){
		 
		MaintenanceDocumentForm docForm = (MaintenanceDocumentForm) model;
		HrBusinessObject anHrObject = (HrBusinessObject) docForm.getDocument().getNewMaintainableObject().getDataObject();
		List<KeyValue> options = new ArrayList<KeyValue>();
		
		if (field.getId().contains("add")) {
			// For "add" line, just delegate to getKeyValues() method as it is working correctly
			options = getKeyValues();	
		} else {
			// Strip index off field id
			String fieldId = field.getId();
			int line_index = fieldId.indexOf("line");
			int index = Integer.parseInt(fieldId.substring(line_index+4));

			PositionBo aClass = (PositionBo)anHrObject;
			List<PositionResponsibilityBo> posRresponsibilityList = aClass.getPositionResponsibilityList(); // holds "added" lines
			PositionResponsibilityBo posResponsibility = (PositionResponsibilityBo)posRresponsibilityList.get(index);
			
			List<? extends PositionResponsibilityOptionContract> typeList = PmServiceLocator.getPositionResponsibilityOptionService().getAllActivePstnRspOptions();
			options.add(new ConcreteKeyValue("", ""));
			if(CollectionUtils.isNotEmpty(typeList)) {
				for(PositionResponsibilityOptionContract aType : typeList) {
					options.add(new ConcreteKeyValue((String) aType.getPrOptionId(), (String) aType.getPrOptionName()));
				}
			}      
		} 

		return options;
		
	}

}
