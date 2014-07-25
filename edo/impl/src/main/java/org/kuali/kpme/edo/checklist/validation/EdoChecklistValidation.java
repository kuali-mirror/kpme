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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.kpme.core.bo.validation.HrKeyedBusinessObjectValidation;
import org.kuali.kpme.edo.api.checklist.EdoChecklistItem;
import org.kuali.kpme.edo.api.checklist.EdoChecklistSection;
import org.kuali.kpme.edo.api.dossier.type.EdoDossierType;
import org.kuali.kpme.edo.checklist.EdoChecklistBo;
import org.kuali.kpme.edo.checklist.EdoChecklistItemBo;
import org.kuali.kpme.edo.checklist.EdoChecklistSectionBo;
import org.kuali.kpme.edo.checklist.service.EdoChecklistSectionService;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;

public class EdoChecklistValidation extends HrKeyedBusinessObjectValidation {

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		
		boolean isValid = super.processCustomRouteDocumentBusinessRules(document);
		LOG.debug("entering custom validation for edo checklist");
		
		EdoChecklistBo checklist = (EdoChecklistBo) this.getNewDataObject();
		
		if (checklist != null) {
			isValid &= validateDossierTypeCode(checklist);
			isValid &= validateSectionOrdinal(checklist);
			//isValid &= validateChecklistItemOrdinal(checklist);
			//isValid &= validateChecklistItemName(checklist);
		}
		return isValid;
	}

	private boolean validateDossierTypeCode(EdoChecklistBo checklist) {
		
		EdoDossierType edoDossierType = EdoServiceLocator.getEdoDossierTypeService().getEdoDossierTypeByCode(checklist.getDossierTypeCode());
		String errorMes = "Dossier Type Code '"+ checklist.getDossierTypeCode() + "'";
		if(edoDossierType == null) {
			this.putFieldError("dataObject.edoDossierType", "error.existence", errorMes);
			return false;
		} 
		
		return true;
	}
	
	private boolean validateSectionOrdinal(EdoChecklistBo checklist) {
		
		List<EdoChecklistSectionBo> sections = checklist.getChecklistSections();
		List<Integer> ordinals = new ArrayList<Integer>();
		Set<Integer> sortedOrginals = new HashSet();
		
		for (EdoChecklistSectionBo section : sections) {
			ordinals.add(section.getChecklistSectionOrdinal());
		}
		for (Integer ordinal : ordinals) {
			if (!sortedOrginals.add(ordinal)) {
				String[] params = new String[2];
				params[0] = "Checklist Section Ordenal";
				params[1] = ordinal+"";
				this.putFieldError("dataObject.edoChecklistSection", "error.checklist.exist", params);
				return false;		
			}
		}

		return true; 
		
	}
	
	/*
	private boolean validateChecklistItemOrdinal(EdoChecklistBo checklist) {

		List<EdoChecklistItem> listItems = EdoServiceLocator.getChecklistItemService().getChecklistItemsBySectionId(checklistItem.getEdoChecklistSectionId());
		for (EdoChecklistItem listItem : listItems) {
			if (listItem.getChecklistItemOrdinal() == checklistItem.getChecklistItemOrdinal()) {
				// error.checklist.exist ={0} '{1}' is already in use.
				String[] params = new String[2];
				params[0] = "Checklist Item Ordenal";
				params[1] = checklistItem.getChecklistItemOrdinal()+"";
				this.putFieldError("dataObject.edoChecklistItem", "error.checklist.exist", params);
				return false;
			}
		}
		
		return true;
		
	}
	
	private boolean validateChecklistItemName(EdoChecklistBo checklist) {

		EdoChecklistSectionService checklistSectionService = EdoServiceLocator.getChecklistSectionService();
		
		String edoChecklistSectionId = checklistItem.getEdoChecklistSectionId();
		String edoChecklistId = checklistSectionService.getChecklistSectionById(edoChecklistSectionId).getEdoChecklistId();
		
		List<EdoChecklistSection> sections = checklistSectionService.getChecklistSectionsByChecklistId(edoChecklistId);
		
		for (EdoChecklistSection section : sections) {
			
			List<EdoChecklistItem> listItems = EdoServiceLocator.getChecklistItemService().getChecklistItemsBySectionId(section.getEdoChecklistSectionId());
			for (EdoChecklistItem listItem : listItems) {
				if (listItem.getChecklistItemName().equals(checklistItem.getChecklistItemName())) {
					// error.checklist.exist ={0} '{1}' is already in use.
					String[] params = new String[2];
					params[0] = "Checklist Item Name";
					params[1] = checklistItem.getChecklistItemName();
					this.putFieldError("dataObject.edoChecklistItem", "error.checklist.exist", params);
					return false;
				}
			}
			
		}

		return true;
		
	}*/

}
