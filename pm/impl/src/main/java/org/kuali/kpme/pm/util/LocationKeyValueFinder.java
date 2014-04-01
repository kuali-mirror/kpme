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

import org.apache.cxf.common.util.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.pm.classification.ClassificationBo;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.kpme.pm.positiondepartment.PositionDepartmentBo;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

public class LocationKeyValueFinder extends UifKeyValuesFinderBase {

	private static final long serialVersionUID = 1L;

	@Override
	public List<KeyValue> getKeyValues(ViewModel model, InputField field) {
		
		List<KeyValue> options = new ArrayList<KeyValue>();
		
		MaintenanceDocumentForm docForm = (MaintenanceDocumentForm) model;
		HrBusinessObject anHrObject = (HrBusinessObject) docForm.getDocument().getNewMaintainableObject().getDataObject();
		LocalDate aDate = anHrObject.getEffectiveLocalDate() != null ? anHrObject.getEffectiveLocalDate() : null;
		
		if (aDate != null) {
			String institution = null;
			
			if (anHrObject instanceof ClassificationBo) {
				ClassificationBo aClass = (ClassificationBo)anHrObject;
				institution = aClass.getInstitution();
			} else {
				
				PositionBo aClass = (PositionBo)anHrObject;		
				if (field.getId().contains("add") || field.getId().contains("line")) {  // gets called on Additinal Departments Page
					
					if (field.getId().contains("add")) {
						PositionDepartmentBo dept = (PositionDepartmentBo) docForm.getNewCollectionLines().get("document.newMaintainableObject.dataObject.departmentList");
						if (StringUtils.isEmpty(dept.getInstitution())) {
							institution = aClass.getInstitution();
						} else {
							institution = dept.getInstitution();						
						}
					} else {
						String fieldId = field.getId();
						int line_index = fieldId.indexOf("line");
						int index = Integer.parseInt(fieldId.substring(line_index+4));
						List<PositionDepartmentBo> deptList = aClass.getDepartmentList(); // holds "added" lines
						PositionDepartmentBo aDepartment = (PositionDepartmentBo)deptList.get(index);
						institution = aDepartment.getInstitution();
					}
					
				} else { // gets called on Position Overview Page
					institution = aClass.getInstitution();
				}
			}

			
			List<String> locations = HrServiceLocator.getDepartmentService().getLocationsValuesWithInstitution(institution, aDate);
			if(locations != null && locations.size() > 0) {
				for(String location : locations) {
					options.add(new ConcreteKeyValue(location, location));						
				}
			}
		}

		return options;
	}
}
