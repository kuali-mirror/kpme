package org.kuali.hr.time.roles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.maintenance.Maintainable;
import org.kuali.rice.kns.web.ui.Section;

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
            List<TkRole> roles = trg.getRoles();
    		List<TkRole> rolesCopy = new ArrayList<TkRole>();
    		rolesCopy.addAll(roles);
    		if (trg.getInactiveRoles() != null
    				&& trg.getInactiveRoles().size() > 0) {
    			for (TkRole role : trg.getInactiveRoles()) {
    				roles.add(role);
    			}
    		}
            for (TkRole role : roles) {
            	System.out.println("In for "+role.getRoleName());
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
                role.setPrincipalId(trg.getPrincipalId());
                role.setUserPrincipalId(TKContext.getUser().getPrincipalId());
                System.out.println("end of for "+role.getPrincipalId());
            }
            TkServiceLocator.getTkRoleService().saveOrUpdate(roles);
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
		TkServiceLocator.getTkRoleGroupService().populateRoles(tkRoleGroupOld);
		TkServiceLocator.getTkRoleGroupService().populateRoles(tkRoleGroup);
		super.processAfterEdit(document, parameters);
	}
	
	@Override
	public List getSections(MaintenanceDocument document,
			Maintainable oldMaintainable) {
		List sections = super.getSections(document, oldMaintainable);
		for (Object obj : sections) {
			Section sec = (Section) obj;
			if (document.isOldBusinessObjectInDocument()
					&& sec.getSectionId().equals("inactiveRoles")) {
				sec.setHidden(false);
			} else if (!document.isOldBusinessObjectInDocument()
					&& sec.getSectionId().equals("inactiveRoles")) {
				sec.setHidden(true);
			}
		}
		return sections;
	}


}
