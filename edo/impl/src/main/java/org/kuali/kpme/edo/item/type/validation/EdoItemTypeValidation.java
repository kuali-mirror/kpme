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
package org.kuali.kpme.edo.item.type.validation;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.kpme.edo.item.type.EdoItemTypeBo;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class EdoItemTypeValidation extends MaintenanceDocumentRuleBase {

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {

		boolean isValid = super.processCustomRouteDocumentBusinessRules(document);
		LOG.debug("entering custom validation for edo checklist item");
		
		EdoItemTypeBo edoItemType = (EdoItemTypeBo)this.getNewDataObject();
		
		if (edoItemType != null) {
			isValid &= validateItemTypeName(edoItemType);
		}
		return isValid;
	}
	
	private boolean validateItemTypeName(EdoItemTypeBo edoItemType) {
		
		String itemId = EdoServiceLocator.getEdoItemTypeService().getItemTypeID(edoItemType.getItemTypeName(), edoItemType.getEffectiveLocalDate());
		
		if (!StringUtils.isEmpty(itemId)) {
			
			if (StringUtils.isEmpty(edoItemType.getEdoItemTypeId())) { // New or Copy
				// error.itemtype.exist = Item Type '{0}' already exists.
				this.putFieldError("dataObject.edoItemType", "error.itemtype.exist", edoItemType.getItemTypeName());
				return false;
			} else {
				if (!itemId.equals(edoItemType.getEdoItemTypeId())) {
					// error.itemtype.exist = Item Type '{0}' already exists.
					this.putFieldError("dataObject.edoItemType", "error.itemtype.exist", edoItemType.getItemTypeName());
					return false;
				}
			}
		}
		
		return true;
	}
	
}
