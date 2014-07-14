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
package org.kuali.kpme.edo.checklist.validation;

import java.util.List;

import org.kuali.kpme.edo.api.checklist.EdoChecklist;
import org.kuali.kpme.edo.api.checklist.EdoChecklistSection;
import org.kuali.kpme.edo.checklist.EdoChecklistSectionBo;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class EdoChecklistSectionValidation extends MaintenanceDocumentRuleBase {

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		
		boolean isValid = super.processCustomRouteDocumentBusinessRules(document);
		LOG.debug("entering custom validation for edo checklist section");
		
		EdoChecklistSectionBo checklistSection = (EdoChecklistSectionBo) this.getNewDataObject();
		
		if (checklistSection != null) {
			isValid &= validateChecklistId(checklistSection);
			isValid &= validateSectionOrdinal(checklistSection);
		}
		return isValid;
	}
	
	private boolean validateChecklistId(EdoChecklistSectionBo checklistSection) {

		System.out.println("checklist id "+checklistSection.getEdoChecklistId());
		EdoChecklist aSection = EdoServiceLocator.getChecklistService().getChecklistByID(checklistSection.getEdoChecklistId()) ;
		String errorMes = "Checklist '"+ checklistSection.getEdoChecklistId() + "'";
		if(aSection == null) {
			System.out.println("check list doesn't exist");
			this.putFieldError("dataObject.edoChecklist", "error.existence", errorMes);
			return false;
		} else {
			System.out.println("check list exists "+aSection);
		}
		
		return true;
	}
	
	private boolean validateSectionOrdinal(EdoChecklistSectionBo checklistSection) {

		List<EdoChecklistSection> sections = EdoServiceLocator.getChecklistSectionService().getChecklistSectionsByChecklistID(checklistSection.getEdoChecklistId(), checklistSection.getEffectiveLocalDate());
		for (EdoChecklistSection section : sections) {
			if (checklistSection.getChecklistSectionOrdinal() == section.getChecklistSectionOrdinal()) {
				// error.checklist.ordinal.exist ={0} '{1}' is already in use.
				String[] params = new String[2];
				params[0] = "Checklist Section Ordenal";
				params[1] = checklistSection.getChecklistSectionOrdinal()+"";
				this.putFieldError("dataObject.edoChecklistSection", "error.checklist.ordinal.exist", params);
				return false;
			}
		}
		
		return true;
		
	}
}
