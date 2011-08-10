package org.kuali.hr.time.roles;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

public class TkRoleValuesFinder extends KeyValuesBase {

	@Override
	public List<KeyLabelPair> getKeyValues() {
		//Filter this list based on your roles for this user
		List<KeyLabelPair> filteredLabels = new ArrayList<KeyLabelPair>();
		if(TKContext.getUser().isSystemAdmin()){
			filteredLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_GLOBAL_VO, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_GLOBAL_VO)));
			filteredLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_SYS_ADMIN, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_SYS_ADMIN)));
		} 
		
		if(TKContext.getUser().isSystemAdmin() || TKContext.getUser().isLocationAdmin()){
			filteredLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_LOCATION_ADMIN, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_LOCATION_ADMIN)));
			filteredLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_LOCATION_VO, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_LOCATION_VO)));
		}
		
		//Safe to assume these roles are ok to assign as you cant get to this page without this level of access
		filteredLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_DEPT_VO, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_DEPT_VO)));
		filteredLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_DEPT_ADMIN, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_DEPT_ADMIN)));
		filteredLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_REVIEWER, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_REVIEWER)));
		filteredLabels.add(new KeyLabelPair(TkConstants.ROLE_TK_APPROVER, TkConstants.ALL_ROLES_MAP.get(TkConstants.ROLE_TK_APPROVER)));
		
		return filteredLabels;
	}
}
