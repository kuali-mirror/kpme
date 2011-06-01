package org.kuali.hr.time.roles;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;

public class TkRoleGroupMaintainableImpl extends KualiMaintainableImpl {

    @Override
    public void saveBusinessObject() {
        BusinessObject bo = this.getBusinessObject();
        if (bo instanceof TkRoleGroup) {
            TkRoleGroup trg = (TkRoleGroup)bo;
            for (TkRole role : trg.getRoles()) {
                if (StringUtils.equals(role.getRoleName(), TkConstants.ROLE_TK_SYS_ADMIN)) {
                    AttributeSet qualifier = new AttributeSet();

                    KIMServiceLocator.getRoleUpdateService().assignPrincipalToRole(role.getPrincipalId(), TkConstants.ROLE_NAMESAPCE, role.getRoleName(), qualifier);
                }
            }
        }
    }


}
