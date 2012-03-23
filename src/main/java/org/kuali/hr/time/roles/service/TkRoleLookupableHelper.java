package org.kuali.hr.time.roles.service;

import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.hr.time.roles.TkRoleGroup;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;

import java.util.List;
import java.util.Map;

public class TkRoleLookupableHelper extends HrEffectiveDateActiveLookupableHelper {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @SuppressWarnings({"rawtypes", "serial"})
    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
                                              List pkNames) {
        List<HtmlData> customActionUrls = super.getCustomActionUrls(
                businessObject, pkNames);
        if (TKContext.getUser().isSystemAdmin() ||
                TKContext.getUser().isLocationAdmin() ||
                TKContext.getUser().isDepartmentAdmin() ||
                TKContext.getUser().isGlobalViewOnly()) {
            TkRoleGroup tkRoleGroup = (TkRoleGroup) businessObject;
            final String className = this.getBusinessObjectClass().getName();
            final String principalId = tkRoleGroup.getPrincipalId();
            HtmlData htmlData = new HtmlData() {

                @Override
                public String constructCompleteHtmlTag() {
                    return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
                            + className
                            + "&methodToCall=start&principalId="
                            + principalId + "\">view</a>";
                }
            };
            customActionUrls.add(htmlData);
        } else if (customActionUrls.size() != 0) {
            customActionUrls.remove(0);
        }
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
        return roleGroupList;
    }
}
