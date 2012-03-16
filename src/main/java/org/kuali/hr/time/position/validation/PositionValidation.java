package org.kuali.hr.time.position.validation;

import java.sql.Date;

import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

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
		PersistableBusinessObject pbo = this.getNewBo();

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