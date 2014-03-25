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
import org.kuali.kpme.pm.api.positionflag.PositionFlagContract;
import org.kuali.kpme.pm.classification.Classification;
import org.kuali.kpme.pm.classification.flag.ClassificationFlag;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.kpme.pm.position.PstnFlagBo;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

public class FlagCategoryKeyValueFinder extends UifKeyValuesFinderBase {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public List<KeyValue> getKeyValues() {		
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		List<String> categories = PmServiceLocator.getPositionFlagService().getAllActiveFlagCategories();
		keyValues.add(new ConcreteKeyValue("", "Select category to see flags"));
		if(CollectionUtils.isNotEmpty(categories)) {
			for(String aCategory : categories) {
				keyValues.add(new ConcreteKeyValue(aCategory, aCategory));
			}
		}         
		return keyValues;
	}
	
	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		List<KeyValue> options = new ArrayList<KeyValue>();
		MaintenanceDocumentForm docForm = (MaintenanceDocumentForm) model; 
		HrBusinessObject anHrObject = (HrBusinessObject) docForm.getDocument().getNewMaintainableObject().getDataObject();
		if(anHrObject.getEffectiveDate() != null) {
			List<? extends PositionFlagContract> flagList = PmServiceLocator.getPositionFlagService().getAllActivePositionFlags(null, null, anHrObject.getEffectiveLocalDate());
			options.add(new ConcreteKeyValue("", "Select category to see flags"));
			if(CollectionUtils.isNotEmpty(flagList)) {
				for(PositionFlagContract aFlag : flagList) {
					if (!options.contains(new ConcreteKeyValue((String) aFlag.getCategory(), (String) aFlag.getCategory()))) {
						options.add(new ConcreteKeyValue((String) aFlag.getCategory(), (String) aFlag.getCategory()));						
					}
				}
			}         
		} else {
			options = this.getKeyValues();
		}
		
        return options;
    }
	
	// KPME-3135 - only show unselected options in flag categeory dropdown list
	@Override
    public List<KeyValue> getKeyValues(ViewModel model, InputField field){
		
		MaintenanceDocumentForm docForm = (MaintenanceDocumentForm) model;
		HrBusinessObject anHrObject = (HrBusinessObject) docForm.getDocument().getNewMaintainableObject().getDataObject();
		LocalDate aDate = anHrObject.getEffectiveLocalDate() != null ? anHrObject.getEffectiveLocalDate() : null;
		List<KeyValue> options = new ArrayList<KeyValue>();
		
		
		if (field.getId().contains("add")) {

			// Get available categories and add them to options list
			if(aDate != null) { // Edit
				List<? extends PositionFlagContract> availableFlagList = PmServiceLocator.getPositionFlagService().getAllActivePositionFlags(null, null, anHrObject.getEffectiveLocalDate());
				options.add(new ConcreteKeyValue("", "Select category to see flags"));
				if (CollectionUtils.isNotEmpty(availableFlagList)) {
					for(PositionFlagContract aFlag : availableFlagList) {
						if (!options.contains(new ConcreteKeyValue((String) aFlag.getCategory(), (String) aFlag.getCategory()))) {
							options.add(new ConcreteKeyValue((String) aFlag.getCategory(), (String) aFlag.getCategory()));						
						}
					}
				}
			} else{ // Create New
				List<String> categories = PmServiceLocator.getPositionFlagService().getAllActiveFlagCategories();
				options.add(new ConcreteKeyValue("", "Select category to see flags"));
				if(CollectionUtils.isNotEmpty(categories)) {
					for(String aCategory : categories) {
						options.add(new ConcreteKeyValue(aCategory, aCategory));
					}
				}  
			}

			// Only show available categories by removing the existing ones from options list
			if (anHrObject instanceof Classification) {
				Classification aClass = (Classification)anHrObject;
				List<ClassificationFlag> existingFlagList = aClass.getFlagList();
				if (CollectionUtils.isNotEmpty(existingFlagList)) {
					for (ClassificationFlag aFlag : existingFlagList) {
						KeyValue aFlagKeyVale = new ConcreteKeyValue((String)aFlag.getCategory(), (String)aFlag.getCategory());
						if (options.contains(aFlagKeyVale)) {
							options.remove(aFlagKeyVale);
						}
					}
				}	
				
			} else {
				PositionBo aClass = (PositionBo)anHrObject;		
				List<PstnFlagBo> existingFlagList = aClass.getFlagList(); // holds a list of flags that exist on the document 		
				if (CollectionUtils.isNotEmpty(existingFlagList)) {
					for (PstnFlagBo aFlag : existingFlagList) {
						KeyValue aFlagKeyVale = new ConcreteKeyValue((String)aFlag.getCategory(), (String)aFlag.getCategory());
						if (options.contains(aFlagKeyVale)) {
							options.remove(aFlagKeyVale);
						}
					}
				}			
			}

		} else {
			options = this.getKeyValues(model);	
		}
		
		return options;
	}

}
