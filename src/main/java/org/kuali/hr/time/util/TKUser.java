package org.kuali.hr.time.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kew.web.UserLoginFilter;
import org.kuali.rice.kew.web.session.UserSession;
import org.kuali.rice.kim.bo.Person;

/**
 * This class houses the concept of a user in the Timekeeping system.  It
 * is essentially a lightweight wrapper around multiple KIM Person objects.
 *
 * One for the actual ACTUAL person
 *
 * One for the user the ACTUAL person is backdooring as: Back Door user is like
 * doing 'su - <username>' in unix. You "become" that person, assume all of their
 * roles, etc.
 *
 * One for the user the ACTUAL person is targeting: Targeting a user is being
 * granted read/write access to the users data.
 *
 * See Javadocs for:
 *
 * getCurrentTargetPerson(), getCurrentPerson(), getActualPerson(),
 * getBackdoorPerson(), getTargetPerson().
 *
 * the getCurrent*() methods are most likely what you should be using in any
 * end user display logic. The methods get[ABT]*() can return null.
 *
 */
public class TKUser {
	private Person actualPerson = null;
	private Person backdoorPerson = null;
	private Person targetPerson = null;

	private UserRoles actualPersonRoles = null;
	private UserRoles backdoorPersonRoles = null;
    private UserRoles targetPersonRoles = null;

    /**
     * Uses the current "target" principal ID to fetch the timezone to use for
     * render/display purposes.
     *
     * @return A timezone string, see: http://joda-time.sourceforge.net/timezones.html
     */
    public String getUserTimezone() {
        return TkServiceLocator.getTimezoneService().getUserTimezone(this.getTargetPrincipalId());
    }

	public Person getActualPerson() {
		return actualPerson;
	}

	public void setActualPerson(Person person) {
		this.actualPerson = person;
	}

	public Person getBackdoorPerson() {
		return backdoorPerson;
	}

	public void setBackdoorPerson(Person backdoorPerson) {
		this.backdoorPerson = backdoorPerson;
	}

	/**
	 * @return The principal ID of the User.  Precedence order:
	 *
	 *  Back Door User > Actual User
	 */
	public String getPrincipalId() {
		return getCurrentPerson().getPrincipalId();
	}

	/**
	 * @return The principal name of the User.
	 *
	 * Back Door User > Actual User
	 */
	public String getPrincipalName() {
		return getCurrentPerson().getPrincipalName();
	}

    /**
     * Target > Back Door > Actual
     * @return The Principal ID of the user we are viewing.
     */
    public String getTargetPrincipalId() {
        Person p = getTargetPerson();
        if (p == null)
            p = getBackdoorPerson();
        if (p == null)
            p = getActualPerson();

        return p.getPrincipalId();
    }

	public void clearBackdoorUser() {
		this.backdoorPerson = null;
		this.backdoorPersonRoles = null;
	}

	public void clearTargetUser() {
		this.targetPerson = null;
	}

	public void clearTargetUserFromSession(){
		UserSession userSession = UserLoginFilter.getUserSession(TKContext.getHttpServletRequest());
        userSession.getObjectMap().remove(TkConstants.TK_TARGET_USER_PERSON);
        clearTargetUser();
	}

    /**
     * Provides the ACTUAL target person. Null is possible.
     * @return The target Person object, if present, otherwise null.
     */
	public Person getTargetPerson() {
		return targetPerson;
	}

	public void setTargetPerson(Person targetPerson) {
		this.targetPerson = targetPerson;
	}

	public UserRoles getActualPersonRoles() {
		return actualPersonRoles;
	}

	public void setActualPersonRoles(UserRoles actualPersonRoles) {
		this.actualPersonRoles = actualPersonRoles;
	}

	public UserRoles getBackdoorPersonRoles() {
		return backdoorPersonRoles;
	}

	public void setBackdoorPersonRoles(UserRoles backdoorPersonRoles) {
		this.backdoorPersonRoles = backdoorPersonRoles;
	}

    /**
     * Returns a Person object for the target person if present, otherwise
     * the backdoor, and finally the actual.
     *
     * @return A Person object: target > backdoor > actual.
     */
    public Person getCurrentTargetPerson() {
        Person p = this.getTargetPerson();
        if (p == null)
            p = this.getCurrentPerson();
        return p;
    }

    /**
     * Returns a UserRoles object for the target person if present, otherwise
     * the backdoor, and finally the actual.
     *
     * @return A UserRoles object: target > backdoor > actual.
     */
    public UserRoles getCurrentTargetRoles() {
        UserRoles r = this.targetPersonRoles;
        if (r == null)
            r = getCurrentRoles();
        return r;
    }

    /**
     * Provides access to the current roles.
     * @return The roles of the current 'acting' Person (backdoor or actual).
     */
	public UserRoles getCurrentRoles() {
		if (getBackdoorPersonRoles() != null) {
			return getBackdoorPersonRoles();
		} else {
			return getActualPersonRoles();
		}
	}

	public boolean isSystemAdmin() {
		UserRoles userRoles = getCurrentRoles();
		return userRoles.isSystemAdmin();
	}

	public boolean isLocationAdmin() {
		return getLocationAdminAreas().size() > 0;
	}

	public boolean isDepartmentAdmin() {
		return getDepartmentAdminAreas().size() > 0;
	}

	public boolean isGlobalViewOnly() {
		UserRoles userRoles = getCurrentRoles();
		return userRoles.isGlobalViewOnly();
	}

	public boolean isDepartmentViewOnly() {
		UserRoles userRoles = getCurrentRoles();
		return userRoles.getDepartmentViewOnlyDepartments().size() > 0;
	}

	public boolean isReviewer(){
		UserRoles userRoles = getCurrentRoles();
		return userRoles.getReviewerWorkAreas().size() > 0;
	}

	public boolean isApprover() {
		UserRoles userRoles = getCurrentRoles();
		return userRoles.getApproverWorkAreas().size() > 0;
	}
	
	public List<String> getReportingApprovalDepartments(){
		UserRoles userRoles = getCurrentRoles();
		List<String> reportingApprovalDepartments = new ArrayList<String>();
		Set<Long> workAreas = new HashSet<Long>();
		workAreas.addAll(userRoles.getApproverWorkAreas());
		workAreas.addAll(userRoles.getReviewerWorkAreas());
		
		for(Long workArea : workAreas){
			WorkArea workAreaObj = TkServiceLocator.getWorkAreaService().getWorkArea(workArea, TKUtils.getCurrentDate());
			if(workAreaObj != null){
				if(!reportingApprovalDepartments.contains(workAreaObj.getDept())){
					reportingApprovalDepartments.add(workAreaObj.getDept());
				}
			}
		}
		Set<String> depts = new HashSet<String>();
		depts.addAll(userRoles.getDepartmentViewOnlyDepartments());
		depts.addAll(userRoles.getOrgAdminDepartments());
		
		for(String dept : depts){
			if(!reportingApprovalDepartments.contains(dept)){
				reportingApprovalDepartments.add(dept);
			}
		}
		return reportingApprovalDepartments;
	}
	
	public List<Long> getReportingWorkAreas(){
		UserRoles userRoles = getCurrentRoles();
		List<Long> reportingWorkAreas = new ArrayList<Long>();
		List<String> depts = new ArrayList<String>();
		
		reportingWorkAreas.addAll(userRoles.getApproverWorkAreas());
		for(Long workArea : userRoles.getApproverWorkAreas()){
			if(!reportingWorkAreas.contains(workArea)){
				reportingWorkAreas.add(workArea);
			}
		}
		
		for(Long workArea : userRoles.getReviewerWorkAreas()){
			if(!reportingWorkAreas.contains(workArea)){
				reportingWorkAreas.add(workArea);
			}
		}
		
		reportingWorkAreas.addAll(userRoles.getReviewerWorkAreas());
		
		depts.addAll(userRoles.getDepartmentViewOnlyDepartments());
		depts.addAll(userRoles.getOrgAdminDepartments());
		
		for(String dept : depts){
			List<WorkArea> workAreas = TkServiceLocator.getWorkAreaService().getWorkAreas(dept, TKUtils.getCurrentDate());
			for(WorkArea workArea : workAreas){
				if(!reportingWorkAreas.contains(workArea.getWorkArea())){
					reportingWorkAreas.add(workArea.getWorkArea());
				}
			}
		}
		
		
		return reportingWorkAreas;
	}

	public Set<String> getLocationAdminAreas() {
		UserRoles userRoles = getCurrentRoles();
		return userRoles.getOrgAdminCharts();
	}

	public Set<String> getDepartmentAdminAreas() {
		UserRoles userRoles = getCurrentRoles();
		return userRoles.getOrgAdminDepartments();
	}

    /**
     * Returns the current 'acting' person. This will be either the back door
     * person, or the actual person.
     *
     * @return the current 'acting' Person (backdoor or actual).
     */
	public Person getCurrentPerson() {
		if (backdoorPerson != null)
			return getBackdoorPerson();

		return actualPerson;
	}

    /**
     * @return UserRoles for the target person if present, null otherwise.
     */
    public UserRoles getTargetPersonRoles() {
        return targetPersonRoles;
    }

    public void setTargetPersonRoles(UserRoles targetPersonRoles) {
        this.targetPersonRoles = targetPersonRoles;
    }
}
