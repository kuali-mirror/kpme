package org.kuali.hr.time.department;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.Maintainable;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.web.ui.Section;

public class DepartmentMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	private static final long serialVersionUID = -330523155799598560L;

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
	
	@SuppressWarnings("rawtypes")
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
		return TkServiceLocator.getDepartmentService().getDepartment(id);
	}

	@Override
	public void customSaveLogic(HrBusinessObject hrObj) {
		Department dept = (Department)hrObj;
		List<TkRole> roles = dept.getRoles();
		List<TkRole> rolesCopy = new ArrayList<TkRole>();
		
		rolesCopy.addAll(roles);
		
		if (dept.getInactiveRoles() != null
				&& dept.getInactiveRoles().size() > 0) {
			for (TkRole role : dept.getInactiveRoles()) {
				roles.add(role);
			}
		}

		dept.setRoles(roles);
		for (TkRole role : roles) {
			role.setDepartmentObj(dept);
			role.setUserPrincipalId(TKContext.getPrincipalId());
		}
		TkServiceLocator.getTkRoleService().saveOrUpdate(roles);
	}
}
