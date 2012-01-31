package org.kuali.hr.time.roles;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.web.struts.form.KualiMaintenanceForm;

public class TkRoleValuesFinder extends KeyValuesBase {

	private KualiMaintenanceForm kualiForm = null;
	
	public KualiMaintenanceForm getKualiForm() {
		return kualiForm;
	}

	public void setKualiForm(KualiMaintenanceForm kualiForm) {
		this.kualiForm = kualiForm;
	}

	@Override
	public List<KeyLabelPair> getKeyValues() {
		//Filter this list based on your roles for this user
		List<KeyLabelPair> filteredLabels = new ArrayList<KeyLabelPair>();
		KualiMaintenanceForm kualiForm = null;
		if(TKContext.getHttpServletRequest().getAttribute("KualiForm") instanceof KualiMaintenanceForm){
			kualiForm = (KualiMaintenanceForm)TKContext.getHttpServletRequest().getAttribute("KualiForm");
			setKualiForm(kualiForm);
		}
        
		if(kualiForm == null || kualiForm.getDocTypeName().equals("RoleGroupMaintenanceDocumentType")){
			if(TKContext.getUser().isSystemAdmin()){
				filteredLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_SYS_ADMIN, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_SYS_ADMIN)));
				filteredLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_GLOBAL_VO, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_GLOBAL_VO)));
			} 
			
			if(TKContext.getUser().isSystemAdmin() || TKContext.getUser().isLocationAdmin()){
				filteredLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_LOCATION_ADMIN, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_LOCATION_ADMIN)));
				filteredLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_LOCATION_VO, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_LOCATION_VO)));
			}
		}

		if(kualiForm == null || kualiForm.getDocTypeName().equals("DepartmentMaintenanceDocumentType")){
			if(TKContext.getUser().isSystemAdmin() || TKContext.getUser().isLocationAdmin()){
				filteredLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_DEPT_ADMIN, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_DEPT_ADMIN)));
				filteredLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_DEPT_VO, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_DEPT_VO)));
			}
		}
		
		if(kualiForm == null || kualiForm.getDocTypeName().equals("WorkAreaMaintenanceDocumentType")){
			if(TKContext.getUser().isSystemAdmin() || TKContext.getUser().isLocationAdmin()){
				filteredLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_APPROVER, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_APPROVER)));
				filteredLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_APPROVER_DELEGATE, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_APPROVER_DELEGATE)));
				filteredLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_REVIEWER, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_REVIEWER)));
			}
		}
		
	

		return filteredLabels;
	}
}
