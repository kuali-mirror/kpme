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
package org.kuali.hr.time.roles;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kns.web.struts.form.KualiMaintenanceForm;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

public class TkRoleValuesFinder extends KeyValuesBase {

	private KualiMaintenanceForm kualiForm = null;
	
	public KualiMaintenanceForm getKualiForm() {
		return kualiForm;
	}

	public void setKualiForm(KualiMaintenanceForm kualiForm) {
		this.kualiForm = kualiForm;
	}

	@Override
	public List<KeyValue> getKeyValues() {
		//Filter this list based on your roles for this user
		List<KeyValue> filteredLabels = new ArrayList<KeyValue>();
		KualiMaintenanceForm kualiForm = null;
        if (TKContext.getHttpServletRequest() != null) {
            if(TKContext.getHttpServletRequest().getAttribute("KualiForm") instanceof KualiMaintenanceForm){
                kualiForm = (KualiMaintenanceForm)TKContext.getHttpServletRequest().getAttribute("KualiForm");
                setKualiForm(kualiForm);
            }
        }
        
		if(kualiForm == null || kualiForm.getDocTypeName().equals("RoleGroupMaintenanceDocumentType")){
			if(TKContext.getUser().isSystemAdmin()){
				filteredLabels.add(new ConcreteKeyValue(TkConstants.ROLE_TK_SYS_ADMIN, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_SYS_ADMIN)));
				filteredLabels.add(new ConcreteKeyValue(TkConstants.ROLE_TK_GLOBAL_VO, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_GLOBAL_VO)));
			} 
			
			if(TKContext.getUser().isSystemAdmin() || TKContext.getUser().isLocationAdmin()){
				filteredLabels.add(new ConcreteKeyValue(TkConstants.ROLE_TK_LOCATION_ADMIN, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_LOCATION_ADMIN)));
				filteredLabels.add(new ConcreteKeyValue(TkConstants.ROLE_TK_LOCATION_VO, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_LOCATION_VO)));
			}
		}

		if(kualiForm == null || kualiForm.getDocTypeName().equals("DepartmentMaintenanceDocumentType")){
			if(TKContext.getUser().isSystemAdmin() || TKContext.getUser().isLocationAdmin()){
				filteredLabels.add(new ConcreteKeyValue(TkConstants.ROLE_TK_DEPT_ADMIN, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_DEPT_ADMIN)));
				filteredLabels.add(new ConcreteKeyValue(TkConstants.ROLE_TK_DEPT_VO, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_DEPT_VO)));
				
				filteredLabels.add(new ConcreteKeyValue(TkConstants.ROLE_LV_DEPT_ADMIN, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_LV_DEPT_ADMIN)));
				filteredLabels.add(new ConcreteKeyValue(TkConstants.ROLE_LV_DEPT_VO, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_LV_DEPT_VO)));
			}
		}
		
		if(kualiForm == null || kualiForm.getDocTypeName().equals("WorkAreaMaintenanceDocumentType")){
			if(TKContext.getUser().isSystemAdmin() || TKContext.getUser().isLocationAdmin() || TKContext.getUser().isDepartmentAdmin()){
				filteredLabels.add(new ConcreteKeyValue(TkConstants.ROLE_TK_APPROVER, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_APPROVER)));
				filteredLabels.add(new ConcreteKeyValue(TkConstants.ROLE_TK_APPROVER_DELEGATE, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_APPROVER_DELEGATE)));
				filteredLabels.add(new ConcreteKeyValue(TkConstants.ROLE_TK_REVIEWER, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_REVIEWER)));
			}
		}
		
	

		return filteredLabels;
	}
}
