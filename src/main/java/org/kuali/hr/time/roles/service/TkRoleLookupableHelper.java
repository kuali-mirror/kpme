package org.kuali.hr.time.roles.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.hr.time.roles.TkRoleGroup;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

public class TkRoleLookupableHelper extends HrEffectiveDateActiveLookupableHelper {

    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
    	List<HtmlData> customActionUrls = new ArrayList<HtmlData>();
		
		List<HtmlData> defaultCustomActionUrls = super.getCustomActionUrls(businessObject, pkNames);
        
		TkRoleGroup tkRoleGroup = (TkRoleGroup) businessObject;
        String principalId = tkRoleGroup.getPrincipalId();
        String location = null;
        if (StringUtils.isNotBlank(tkRoleGroup.getDepartment())) {
            location = TkServiceLocator.getDepartmentService().getDepartment(tkRoleGroup.getDepartment(), TKUtils.getCurrentDate()).getLocation();
        }
        String department = tkRoleGroup.getDepartment();
        
        boolean systemAdmin = TKContext.getUser().isSystemAdmin();
		boolean locationAdmin = TKContext.getUser().getLocationAdminAreas().contains(location);
		boolean departmentAdmin = TKContext.getUser().getDepartmentAdminAreas().contains(department);
		
		for (HtmlData defaultCustomActionUrl : defaultCustomActionUrls){
			if (StringUtils.equals(defaultCustomActionUrl.getMethodToCall(), "edit")) {
				if (systemAdmin || locationAdmin || departmentAdmin) {
					customActionUrls.add(defaultCustomActionUrl);
				}
			} else {
				customActionUrls.add(defaultCustomActionUrl);
			}
		}
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("principalId", principalId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
    }

    @Override
    public List<? extends BusinessObject> getSearchResults(
            Map<String, String> fieldValues) {

        String principalId = fieldValues.get("principalId");
        String principalName = fieldValues.get("principalName");
        String workArea = fieldValues.get("workArea");
        String department = fieldValues.get("department");
        String roleName = fieldValues.get("roleName");

        List<TkRoleGroup> roleGroupList = TkServiceLocator.getTkRoleGroupService().getRoleGroups(principalId, principalName, workArea, department, roleName);

        //BELOW is done to allow grouping of roles by principal id
        //TODO find a better way todo this
        for(TkRoleGroup roleGroup : roleGroupList){
        	TkRoleGroup tkRoleGroup = TkServiceLocator.getTkRoleGroupService().getRoleGroup(roleGroup.getPrincipalId());
        	if(tkRoleGroup == null){
        		tkRoleGroup = new TkRoleGroup();
				tkRoleGroup.setPrincipalId(roleGroup.getPrincipalId());
				TkServiceLocator.getTkRoleGroupService().saveOrUpdate(roleGroup);
        	}
        }
        
        /* We have the full set of roles, but since we are displaying only Principal IDs in the result list, we should return a distinct list */
        Map<String,TkRoleGroup> mapDistinctPrincipalIdsToRoleGroups = new HashMap<String, TkRoleGroup>();
        for(TkRoleGroup roleGroup : roleGroupList){
            mapDistinctPrincipalIdsToRoleGroups.put(roleGroup.getPrincipalId(), roleGroup);
        }
        List<TkRoleGroup> distinctRoleGroupPrincipalIds = new ArrayList<TkRoleGroup>();
        for(String distinctPrincipalId : mapDistinctPrincipalIdsToRoleGroups.keySet()){
        	distinctRoleGroupPrincipalIds.add(mapDistinctPrincipalIdsToRoleGroups.get(distinctPrincipalId));
        }
        
//		if(principalId!=""){
//			Person person = KIMServiceLocator.getPersonService().getPerson(principalId);
//			if (isAuthorizedToEditUserRole(person)
//					&& (StringUtils.isEmpty(principalName) || StringUtils.equals(
//							person.getPrincipalName(), principalName))) {
//				TkRoleGroup tkRoleGroup = getRoleGroupFromPerson(person);
//				if(tkRoleGroup != null ){
//					roleGroupList.add(getRoleGroupFromPerson(person));
//				}
//			}
//		} else if(principalName!=""){
//			Person person = KIMServiceLocator.getPersonService().getPersonByPrincipalName(principalName);
//			if(isAuthorizedToEditUserRole(person)){
//				TkRoleGroup tkRoleGroup = getRoleGroupFromPerson(person);
//				if(tkRoleGroup != null ){
//					roleGroupList.add(getRoleGroupFromPerson(person));
//				}
//			}
//		}else{
//			List<Person> personList = KIMServiceLocator.getPersonService().findPeople(null);
//			for(Person person : personList){
//				if(isAuthorizedToEditUserRole(person)){
//					roleGroupList.add(getRoleGroupFromPerson(person));
//				}
//			}
//		}
//		Iterator<BusinessObject> itr = roleGroupList.iterator();
//		while(itr.hasNext()){
//			TkRoleGroup roleGroup = (TkRoleGroup)itr.next();
//			TkRoleGroup tkRoleGroup = TkServiceLocator.getTkRoleGroupService().getRoleGroup(roleGroup.getPrincipalId());
//			if(tkRoleGroup == null){
//				tkRoleGroup = new TkRoleGroup();
//				tkRoleGroup.setPrincipalId(roleGroup.getPrincipalId());
//				TkServiceLocator.getTkRoleGroupService().saveOrUpdate(roleGroup);
//			}
//			String workArea = fieldValues.get("workArea");
//			String department = fieldValues.get("department");
//			String roleName = fieldValues.get("roleName");
//			boolean isAllowed = false;
//			if (tkRoleGroup.getRoles()!= null && tkRoleGroup.getRoles().size() > 0) {
//				for(TkRole tkRole : tkRoleGroup.getRoles()){
//					if ((StringUtils.isEmpty(workArea) || (tkRole.getWorkArea() != null && StringUtils
//							.equals(workArea, tkRole.getWorkArea().toString())))
//							&& (StringUtils.isEmpty(department) || StringUtils
//									.equals(department, tkRole.getDepartment()))
//							&& (StringUtils.isEmpty(roleName) || StringUtils
//									.equals(roleName, tkRole.getRoleName()))) {
//						isAllowed = true;
//						break;
//					}
//				}
//			} else if (StringUtils.isEmpty(workArea)
//					&& StringUtils.isEmpty(department)
//					&& StringUtils.isEmpty(roleName)) {
//				isAllowed = true;
//			}
//			if(!isAllowed){
//				itr.remove();
//			}
//		}
        return distinctRoleGroupPrincipalIds;
    }
}
