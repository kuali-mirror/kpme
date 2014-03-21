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
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.pm.api.positionflag.PositionFlagContract;
import org.kuali.kpme.pm.classification.Classification;
import org.kuali.kpme.pm.classification.flag.ClassificationFlag;
import org.kuali.kpme.pm.flag.Flag;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.kpme.pm.position.PstnFlag;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.field.InputField;
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
		List<? extends PositionFlagContract> flagList = PmServiceLocator.getPositionFlagService().getAllActivePositionFlagsWithCategory(category, aDate);
		if(CollectionUtils.isNotEmpty(flagList)) {
			for(PositionFlagContract aFlag : flagList) {
				options.add(new ConcreteKeyValue((String) aFlag.getPositionFlagName(), (String) aFlag.getPositionFlagName()));
			}
		}
        return options;
	}
	
	// KPME-2360/2958
	@Override
    public List<KeyValue> getKeyValues(ViewModel model, InputField field){
		 
		MaintenanceDocumentForm docForm = (MaintenanceDocumentForm) model;
		HrBusinessObject anHrObject = (HrBusinessObject) docForm.getDocument().getNewMaintainableObject().getDataObject();
		LocalDate aDate = anHrObject.getEffectiveLocalDate() != null ? anHrObject.getEffectiveLocalDate() : null;
		List<KeyValue> options = new ArrayList<KeyValue>();
		
		// * field.id comes in as u416_add or u424_line0 or u424_line1 where u416/u424 is specific to each input field.
		//   - when the id ends with _add, it's for a blank "add" line
		//   - when the id ends with line0 or _line1, it's for "added" lines
		// * When user adds new lines, they are stored in docForm.addedCollectionItems, but they are also appended to anHrObject.flagList,
		//   which seems to contain all the lines (previously added lines loaded from the table and the lines user just added).  We can
		//   use the line number in the id as "index" in anHrObject.flagList since the list stores lines in the order they appear on the screen. 
		//   - Note that "index" and the line number on the screen are off by 1 (xxx_line0 for line 1, xxx_line1 for line2, xxx_line2 for line 3, etc...)
		// * Example
		//   When user loads a document that has two flags already set, the flag section of the page looks like this:
		//	       category    names
		//   -----------------------------------------------------------------
		//   add                                                     add
		//	 1    Category 1   checkbox a, checkbox b                delete
		// 	 2    Category 2   checkbox c, checkbox d, checkbox e    delete
		//
		//   When user adds a new line Category 3, this method gets called in the following order:
		//   1) adding a blank line - field.id is xxx_add
		//   2) processing line 1 (Category 1) - field.id is xxx_line0 - anHrObject.flagList[0]
		//   3) processing line 2 (Category 2) - field.id is xxx_line1 - anHrObject.flagList[1]
		//   4) processing the line user just added (Category 3) - field.id is xxx_line2 - anHrObject.flagList[2]
		
		if (field.getId().contains("add")) {
			// For "add" line, just delegate to getKeyValues(model) method as it is working correctly
			options = getKeyValues(model);	
		} else {
			// Strip index off field id
			String fieldId = field.getId();
			int line_index = fieldId.indexOf("line");
			int index = Integer.parseInt(fieldId.substring(line_index+4));
			
			String category = null;
			if (anHrObject instanceof Classification) {
				Classification aClass = (Classification)anHrObject;
				List<ClassificationFlag> flagList = aClass.getFlagList(); // holds "added" lines
				ClassificationFlag classFlag = (ClassificationFlag)flagList.get(index);
				category = classFlag.getCategory();
			} else {
				PositionBo aPosition = (PositionBo)anHrObject;
				List<PstnFlag> flagList = aPosition.getFlagList(); // holds "added" lines
				PstnFlag posFlag = (PstnFlag)flagList.get(index);
				category = posFlag.getCategory();
			}
			
			List<? extends PositionFlagContract> allFlagList = PmServiceLocator.getPositionFlagService().getAllActivePositionFlagsWithCategory(category, aDate);
			if(CollectionUtils.isNotEmpty(allFlagList)) {
				for(PositionFlagContract aFlag : allFlagList) {
					options.add(new ConcreteKeyValue((String)aFlag.getPositionFlagName(), (String)aFlag.getPositionFlagName()));
				}
			}		
		} 

		return options;
    }

}
