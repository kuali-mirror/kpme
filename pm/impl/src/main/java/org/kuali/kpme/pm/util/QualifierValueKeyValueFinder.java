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

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.pm.PMConstants;
import org.kuali.kpme.pm.api.pstnqlfrtype.PstnQlfrTypeContract;
import org.kuali.kpme.pm.classification.Classification;
import org.kuali.kpme.pm.classification.qual.ClassificationQualification;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

public class QualifierValueKeyValueFinder extends UifKeyValuesFinderBase {

	private static final long serialVersionUID = 1L;

	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		MaintenanceDocumentForm docForm = (MaintenanceDocumentForm) model;

		List<KeyValue> options = new ArrayList<KeyValue>();

		ClassificationQualification aQualification = (ClassificationQualification) docForm.getNewCollectionLines().get("document.newMaintainableObject.dataObject.qualificationList");
		if(aQualification != null) {
			String aTypeId = aQualification.getQualificationType();
			PstnQlfrTypeContract aTypeObj = PmServiceLocator.getPstnQlfrTypeService().getPstnQlfrTypeById(aTypeId);
			if(aTypeObj != null) {
				if(aTypeObj.getTypeValue().equals(PMConstants.PSTN_QLFR_SELECT)){
					String[] aCol = aTypeObj.getSelectValues().split(",");
					for(String aString : aCol){
						aString = StringUtils.strip(aString);
						options.add(new ConcreteKeyValue(aString, aString));
					}
				} else{
					return new ArrayList<KeyValue>();
				}
			}
		}
        return options;
	}
	
	// KPME-2360
	@Override
    public List<KeyValue> getKeyValues(ViewModel model, InputField field){
		 
		MaintenanceDocumentForm docForm = (MaintenanceDocumentForm) model;
		HrBusinessObject anHrObject = (HrBusinessObject) docForm.getDocument().getNewMaintainableObject().getDataObject();
		List<KeyValue> options = new ArrayList<KeyValue>();
		
		if (field.getId().contains("add")) {
			// For "add" line, just delegate to getKeyValues(model) method as it is working correctly
			options = getKeyValues(model);	
		} else {
			// Strip index off field id
			String fieldId = field.getId();
			int line_index = fieldId.indexOf("line");
			int index = Integer.parseInt(fieldId.substring(line_index+4));

			Classification aClass = (Classification)anHrObject;
			List<ClassificationQualification> qualificationList = aClass.getQualificationList(); // holds "added" lines
			ClassificationQualification aQualification = (ClassificationQualification)qualificationList.get(index);
			
			if(aQualification != null) {
				String aTypeId = aQualification.getQualificationType();
				PstnQlfrTypeContract aTypeObj = PmServiceLocator.getPstnQlfrTypeService().getPstnQlfrTypeById(aTypeId);;
				if(aTypeObj != null) {
					if(aTypeObj.getTypeValue().equals(PMConstants.PSTN_QLFR_SELECT)){
						String[] aCol = aTypeObj.getSelectValues().split(",");
						for(String aString : aCol){
							aString = StringUtils.strip(aString);
							options.add(new ConcreteKeyValue(aString, aString));
						}
					} else{
						return new ArrayList<KeyValue>();
					}
				}
			}
		} 

		return options;
		
	}

}
