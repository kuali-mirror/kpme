/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.workschedule.validation;

import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class WorkScheduleRule extends MaintenanceDocumentRuleBase {

//	protected boolean validateWorkArea(WorkSchedule workSchedule ) {
//		boolean valid = false;
//		LOG.debug("Validating workarea: " + workSchedule.getWorkArea());
//		WorkArea workArea = KNSServiceLocator.getBusinessObjectService()
//				.findBySinglePrimaryKey(WorkArea.class, workSchedule.getWorkArea());
//		if (workArea != null) {
//
//			valid = true;
//			LOG.debug("found workarea.");
//		} else {
//			this.putFieldError("workArea", "error.existence", "workarea '"
//					+ workSchedule.getWorkArea()+ "'");
//		}
//		return valid;
//	}
//
//	protected boolean validateDepartment(WorkSchedule workSchedule) {
//		boolean valid = false;
//
//		if (workSchedule.getDept().equals(TkConstants.WILDCARD_CHARACTER)) {
//			valid = true;
//		} else {
//			LOG.debug("Validating dept: " + workSchedule.getDept());
//			// TODO: We may need a full DAO that handles bo lookups at some point,
//			// but we can use the provided one:
//			Department dept = KNSServiceLocator.getBusinessObjectService()
//					.findBySinglePrimaryKey(Department.class, workSchedule.getDept());
//			if (dept != null) {
//				valid = true;
//				LOG.debug("found department.");
//			} else {
//				this.putFieldError("deptId", "error.existence", "department '"
//						+ workSchedule.getDept() + "'");
//			}
//		}
//		return valid;
//	}
//
//	/**
//	 * It looks like the method that calls this class doesn't actually care
//	 * about the return type.
//	 */
//	@Override
//	protected boolean processCustomRouteDocumentBusinessRules(
//			MaintenanceDocument document) {
//		boolean valid = false;
//
//		LOG.debug("entering custom validation for WorkSchedule");
//		PersistableBusinessObject pbo = this.getNewBo();
//		if (pbo instanceof WorkSchedule) {
//			WorkSchedule workSchedule = (WorkSchedule) pbo;
//
//			if (workSchedule != null) {
//				valid = true;
//				valid &= this.validateWorkArea(workSchedule);
//				valid &= this.validateDepartment(workSchedule);
//
//			}
//		}
//		return valid;
//	}
//
//	@Override
//	protected boolean processCustomApproveDocumentBusinessRules(
//			MaintenanceDocument document) {
//		return super.processCustomApproveDocumentBusinessRules(document);
//	}
//
//	@Override
//	protected boolean processCustomRouteDocumentBusinessRules(
//			MaintenanceDocument document) {
//		return super.processCustomRouteDocumentBusinessRules(document);
//	}
}
