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
package org.kuali.kpme.core.position.validation;

import org.kuali.kpme.core.position.PositionBaseBo;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class PositionBaseValidation extends MaintenanceDocumentRuleBase {

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position");
		
		PositionBaseBo position = (PositionBaseBo)this.getNewDataObject();

		if (position != null) {
			valid = true;
		}
		
		if (valid && document.isNew()) {
			String positionNumber = KRADServiceLocator.getSequenceAccessorService().getNextAvailableSequenceNumber("hr_position_s", PositionBaseBo.class).toString();
	    	position.setPositionNumber(positionNumber);		
		}
		
		return valid;
	}
}