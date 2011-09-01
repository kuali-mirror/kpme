package org.kuali.hr.time.roles.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.hr.time.roles.TkRoleGroup;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;

public class TkRoleLookupableHelper extends HrEffectiveDateActiveLookupableHelper {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "rawtypes", "serial" })
	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(
				businessObject, pkNames);
		if (TKContext.getUser().isSystemAdmin() || 
			TKContext.getUser().isLocationAdmin() ||
			TKContext.getUser().isDepartmentAdmin()) {
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
		List<BusinessObject> roleGroupList = new ArrayList<BusinessObject>();
		String principalId = fieldValues.get("principalId");
		if(principalId!=""){
			Person person = KIMServiceLocator.getPersonService().getPerson(principalId);
			if(isAuthorizedToEditUserRole(person)){
				TkRoleGroup tkRoleGroup = getRoleGroupFromPerson(person);
				if(tkRoleGroup != null){
					roleGroupList.add(getRoleGroupFromPerson(person));
				}
			}
		} else{
			List<Person> personList = KIMServiceLocator.getPersonService().findPeople(null);
			for(Person person : personList){
				if(isAuthorizedToEditUserRole(person)){
					roleGroupList.add(getRoleGroupFromPerson(person));
				}
			}
		}
		for(BusinessObject businessObject : roleGroupList){
			TkRoleGroup roleGroup = (TkRoleGroup)businessObject;
			TkRoleGroup tkRoleGroup = TkServiceLocator.getTkRoleGroupService().getRoleGroup(roleGroup.getPrincipalId());
			if(tkRoleGroup == null){
				TkServiceLocator.getTkRoleGroupService().saveOrUpdate(roleGroup);
			}
		}
		return roleGroupList;
	}
	
	private boolean isAuthorizedToEditUserRole(Person person){
		boolean isAuthorized = false;
		//System admin can do anything
		if(TKContext.getUser().isSystemAdmin()){
			return true;
		}
		
		List<Job> lstJobs = TkServiceLocator.getJobSerivce().getJobs(person.getPrincipalId(), TKUtils.getCurrentDate());
		Set<String> locationAdminAreas = TKContext.getUser().getLocationAdminAreas();
		//Confirm if any job matches this users location admin roles
		for(String location : locationAdminAreas){
			for(Job job : lstJobs){
				if(StringUtils.equals(location, job.getLocation())){
					return true;
				}
			}
		}
		
		Set<String> departmentAdminAreas = TKContext.getUser().getDepartmentAdminAreas();
		//Confirm if any job matches this users department admin roles
		for(String dept : departmentAdminAreas){
			for(Job job : lstJobs){
				if(StringUtils.equals(dept, job.getDept())){
					return true;
				}
			}
		}
		return isAuthorized;
	}
	
	private TkRoleGroup getRoleGroupFromPerson(Person person){

		TkRoleGroup tkRoleGroup = new TkRoleGroup();
		if(person == null) {
			return null;
		}
		tkRoleGroup.setPerson(person);
		tkRoleGroup.setPrincipalId(person.getPrincipalId());
		return tkRoleGroup;
	}

}
