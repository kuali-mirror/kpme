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
  package org.kuali.hr.tklm.time.rules.lunch.department.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.HrBusinessObject;
import org.kuali.hr.tklm.time.rules.lunch.department.DeptLunchRule;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;

public class DeptLunchRuleMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void processAfterPost(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		DeptLunchRule deptLunchRule = (DeptLunchRule) document
				.getDocumentBusinessObject();
		deptLunchRule.setUserPrincipalId(GlobalVariables.getUserSession()
				.getPrincipalId());
		super.processAfterPost(document, parameters);
	}

	@Override
	public void processAfterEdit(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		DeptLunchRule deptLunchRule = (DeptLunchRule) document
				.getDocumentBusinessObject();
		deptLunchRule.setUserPrincipalId(GlobalVariables.getUserSession()
				.getPrincipalId());
		super.processAfterEdit(document, parameters);
	}

	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		if (fieldValues.containsKey("workArea")
				&& StringUtils.equals(fieldValues.get("workArea"), "%")) {
			fieldValues.put("workArea", "-1");
		}
		if (fieldValues.containsKey("jobNumber")
				&& StringUtils.equals(fieldValues.get("jobNumber"), "%")) {
			fieldValues.put("jobNumber", "-1");
		}
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall);
	}

	@Override
	public HrBusinessObject getObjectById(String id) {
		return TkServiceLocator.getDepartmentLunchRuleService().getDepartmentLunchRule(id);
	}
}
