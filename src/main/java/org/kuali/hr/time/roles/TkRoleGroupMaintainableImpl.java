package org.kuali.hr.time.roles;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.cache.CacheUtils;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.Maintainable;
import org.kuali.rice.kns.web.ui.Section;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

import java.sql.Timestamp;
import java.util.*;

public class TkRoleGroupMaintainableImpl extends HrBusinessObjectMaintainableImpl {

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
                if (StringUtils.equals(role.getRoleName(), TkConstants.ROLE_TK_SYS_ADMIN)) {
                    String principalId = role.getPrincipalId();
                    if(StringUtils.isBlank(principalId)){
                    	principalId = trg.getPrincipalId();
                    }
                    if(StringUtils.isBlank(principalId)){
                    	KimApiServiceLocator.getRoleService().assignPrincipalToRole(principalId, TkConstants.ROLE_NAMESAPCE, role.getRoleName(), new HashMap<String,String>());
                    }
                }
                role.setPrincipalId(trg.getPrincipalId());
                role.setUserPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
                
                HrBusinessObject oldHrObj = this.getObjectById(role.getId());
                
    			if(oldHrObj!= null){
    				//if the effective dates are the same do not create a new row just inactivate the old one
    				if(role.getEffectiveDate().equals(oldHrObj.getEffectiveDate())){
    					oldHrObj.setActive(false);
    					oldHrObj.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(TKUtils.getCurrentDate().getTime()))); 
    				} else{
    					//if effective dates not the same add a new row that inactivates the old entry based on the new effective date
    					oldHrObj.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(TKUtils.getCurrentDate().getTime())));
    					oldHrObj.setEffectiveDate(role.getEffectiveDate());
    					oldHrObj.setActive(false);
    					oldHrObj.setId(null);
    				}
    				KRADServiceLocator.getBusinessObjectService().save(oldHrObj);
                    CacheUtils.flushCache(TkRole.CACHE_NAME);
                    CacheUtils.flushCache(TkRoleGroup.CACHE_NAME);

    				role.setTimestamp(new Timestamp(System.currentTimeMillis()));
    				role.setId(null);
    			}
            }
     
            TkServiceLocator.getTkRoleService().saveOrUpdate(roles);
            trg.setRoles(rolesCopy);
        }
    }

	@Override
	public void processAfterEdit(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		TkRoleGroup tkRoleGroup = (TkRoleGroup)document.getNewMaintainableObject().getBusinessObject();
		TkRoleGroup tkRoleGroupOld = (TkRoleGroup)document.getOldMaintainableObject().getBusinessObject();
		List<Job> jobs = TkServiceLocator.getJobService().getJobs(tkRoleGroup.getPrincipalId(), TKUtils.getCurrentDate());
		List<TkRole> positionRoles = new ArrayList<TkRole>();
		List<TkRole> inactivePositionRoles = new ArrayList<TkRole>();
		Set<String> positionNumbers = new HashSet<String>(); 
		for(Job job : jobs){
			positionNumbers.add(job.getPositionNumber());
		}
		for(String pNo : positionNumbers){
			TkRole positionRole = TkServiceLocator.getTkRoleService().getRolesByPosition(pNo);
			if(positionRole != null)
				positionRoles.add(positionRole);
			TkRole inactivePositionRole = TkServiceLocator.getTkRoleService().getInactiveRolesByPosition(pNo);
			if(inactivePositionRole != null)
				inactivePositionRoles.add(inactivePositionRole);
		}
		tkRoleGroup.setInactivePositionRoles(inactivePositionRoles);
		tkRoleGroupOld.setInactivePositionRoles(inactivePositionRoles);
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

	@Override
	public HrBusinessObject getObjectById(String id) {
		return (HrBusinessObject)TkServiceLocator.getTkRoleService().getRole(id);
	}

}
