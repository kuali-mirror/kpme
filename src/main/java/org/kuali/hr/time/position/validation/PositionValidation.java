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
package org.kuali.hr.time.position.validation;

import java.sql.Date;

import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class PositionValidation extends MaintenanceDocumentRuleBase {

	protected boolean validatePositionWorkarea(Position position) {
		
		boolean workAreaValidity = true;
		
		if (position.getWorkArea() != null) {
			Long WANumbr = position.getWorkArea();
			Date effDate = position.getEffectiveDate();

			if (TkServiceLocator.getWorkAreaService().getWorkArea(WANumbr,
					effDate) != null) {
				return workAreaValidity;
			} else {
				this.putFieldError("workArea", "pos.workArea.invalid",
						"work area '" + position.getWorkArea() + "'");
				workAreaValidity = false;
			}
		}
		return workAreaValidity;
	}

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();

		if (pbo instanceof Position) {
			Position position = (Position) pbo;
			if (position != null) {
				valid = true;
				valid &= this.validatePositionWorkarea(position);
			}
		}
		return valid;
	}
}