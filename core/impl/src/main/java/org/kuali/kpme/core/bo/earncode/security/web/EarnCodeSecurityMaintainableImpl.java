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
package org.kuali.kpme.core.bo.earncode.security.web;

import java.util.Map;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.bo.earncode.security.EarnCodeSecurity;
import org.kuali.kpme.core.bo.utils.ValidationUtils;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

public class EarnCodeSecurityMaintainableImpl extends HrBusinessObjectMaintainableImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
    public void processAfterEdit( MaintenanceDocument document, Map<String,String[]> parameters ) {
		EarnCodeSecurity departmentEarnCode = (EarnCodeSecurity) this.getBusinessObject();
		int count = HrServiceLocator.getEarnCodeSecurityService().getNewerEarnCodeSecurityCount(departmentEarnCode.getEarnCode(), departmentEarnCode.getEffectiveLocalDate());
		if(count > 0) {
			GlobalVariables.getMessageMap().putWarningWithoutFullErrorPath(
					KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + "effectiveDate", 
					"deptEarncode.effectiveDate.newer.exists");
		}
		
		if(ValidationUtils.duplicateDeptEarnCodeExists(departmentEarnCode)) {
			GlobalVariables.getMessageMap().putWarningWithoutFullErrorPath(
					KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + "dept", 
					"deptEarncode.duplicate.exists");
		}
    }

	@Override
	public HrBusinessObject getObjectById(String id) {
		return HrServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurity(id);
	}
}
