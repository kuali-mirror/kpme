package org.kuali.hr.time.department;

import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;

import java.util.List;
import java.util.Map;

public class DepartmentMaintainableImpl extends KualiMaintainableImpl {

	private static final long serialVersionUID = -330523155799598560L;

	@Override
	public void saveBusinessObject() {

		Department dept = (Department) this.getBusinessObject();
		List<TkRole> roles = dept.getRoles();

		for(TkRole role : roles) {
			role.setDepartment(dept.getDept());
			role.setUserPrincipalId(TKContext.getPrincipalId());
		}

		dept.setRoles(roles);
		KNSServiceLocator.getBusinessObjectService().save(dept);
        TkServiceLocator.getTkRoleService().saveOrUpdate(roles);

	}

    @Override
	protected void setNewCollectionLineDefaultValues(String arg0,
			PersistableBusinessObject arg1) {
    	if(arg1 instanceof TkRole){
    		TkRole role = (TkRole)arg1;
    		Department dept = (Department) this.getBusinessObject();
    		role.setEffectiveDate(dept.getEffectiveDate());
    	}
		super.setNewCollectionLineDefaultValues(arg0, arg1);
	}

	@Override
    public void processAfterEdit( MaintenanceDocument document, Map<String,String[]> parameters ) {
        Department dOld = (Department)document.getOldMaintainableObject().getBusinessObject();
        Department dNew = (Department)document.getNewMaintainableObject().getBusinessObject();

        TkServiceLocator.getDepartmentService().populateDepartmentRoles(dOld);
        TkServiceLocator.getDepartmentService().populateDepartmentRoles(dNew);
    }
	
	@Override
    public void addNewLineToCollection(String collectionName) {
        if (collectionName.equals("roles")) {
        	TkRole aRole = (TkRole)newCollectionLines.get(collectionName );
            if ( aRole != null ) {
            	if(!aRole.getPrincipalId().isEmpty()) {
            		Person aPerson = KIMServiceLocator.getPersonService().getPerson(aRole.getPrincipalId());
            		if(aPerson == null) {
            			GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KNSConstants.MAINTENANCE_NEW_MAINTAINABLE +"roles", 
                				"dept.role.person.notExist",aRole.getPrincipalId());
                		return;
            		}
            	}
            }
        }
        super.addNewLineToCollection(collectionName);
	}
}
