package org.kuali.kpme.core.department.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.lookup.KpmeHrGroupKeyedBusinessObjectLookupableImpl;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.LookupForm;

public class DepartmentLookupableImpl  extends KpmeHrGroupKeyedBusinessObjectLookupableImpl {
	
	private static final long serialVersionUID = -2021762891452575556L;

	protected List<DepartmentBo> filterLookupDepartments(List<DepartmentBo> rawResults, String userPrincipalId) {
		List<DepartmentBo> returnList = new ArrayList<DepartmentBo>();
    	for (DepartmentBo departmentObj : rawResults) {
        	Map<String, String> roleQualification = new HashMap<String, String>();
        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
        	roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), departmentObj.getDept());
        	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), departmentObj.getLocation());
        	
        	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
    		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
        		returnList.add(departmentObj);
        	}
    	}
        
        return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected List<?> getSearchResults(LookupForm form, Map<String, String> searchCriteria, boolean unbounded) {
		String userPrincipalId = GlobalVariables.getUserSession().getPrincipalId();
		 List<DepartmentBo> results = (List<DepartmentBo>) super.getSearchResults(form, searchCriteria, unbounded);
	        List<DepartmentBo> filteredResults = filterLookupDepartments(results, userPrincipalId);
	        return filteredResults;
	}

}
