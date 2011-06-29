package org.kuali.hr.time.roles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
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

	@Override
	public void processAfterEdit(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		TkRoleGroup tkRoleGroup = (TkRoleGroup)document.getNewMaintainableObject().getBusinessObject();
		TkRoleGroup tkRoleGroupOld = (TkRoleGroup)document.getOldMaintainableObject().getBusinessObject();
		List<Job> jobs = TkServiceLocator.getJobSerivce().getJobs(tkRoleGroup.getPrincipalId(), TKUtils.getCurrentDate());
		List<TkRole> positionRoles = new ArrayList<TkRole>();
		for(Job job : jobs){
			positionRoles.addAll(TkServiceLocator.getTkRoleService().getRolesByPosition(job.getPositionNumber()));
		}
		tkRoleGroup.setPositionRoles(positionRoles);
		tkRoleGroupOld.setPositionRoles(positionRoles);
		super.processAfterEdit(document, parameters);
	}

}
