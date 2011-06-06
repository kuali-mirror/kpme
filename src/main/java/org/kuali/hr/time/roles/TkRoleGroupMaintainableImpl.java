package org.kuali.hr.time.roles;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;

public class TkRoleGroupMaintainableImpl extends KualiMaintainableImpl {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void saveBusinessObject() {
        BusinessObject bo = this.getBusinessObject();
        if (bo instanceof TkRoleGroup) {
            TkRoleGroup trg = (TkRoleGroup)bo;
            for (TkRole role : trg.getRoles()) {
                if (StringUtils.equals(role.getRoleName(), TkConstants.ROLE_TK_SYS_ADMIN)) {
                    AttributeSet qualifier = new AttributeSet();
                    String principalId = role.getPrincipalId();
                    if(StringUtils.isBlank(principalId)){
                    	principalId = trg.getPrincipalId();
                    }
                    if(StringUtils.isBlank(principalId)){
                    	KIMServiceLocator.getRoleUpdateService().assignPrincipalToRole(principalId, TkConstants.ROLE_NAMESAPCE, role.getRoleName(), qualifier);
                    }
                }
            }
        }
    }


}
