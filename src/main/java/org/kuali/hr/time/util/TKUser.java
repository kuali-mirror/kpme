package org.kuali.hr.time.util;

import com.google.common.collect.Multimap;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kew.web.UserLoginFilter;
import org.kuali.rice.kew.web.session.UserSession;
import org.kuali.rice.kim.bo.Person;

import java.util.*;

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
	
	public Multimap<String, Long> getReportingApprovalDepartments(){
		UserRoles userRoles = getCurrentRoles();
        Set<Long> workAreas = new HashSet<Long>();
        workAreas.addAll(userRoles.getApproverWorkAreas());
        workAreas.addAll(userRoles.getReviewerWorkAreas());
        // see the comment in the getDeptWorkAreasByWorkAreas() for the explanation of Multimap
        Multimap<String, Long> reportingApprovalDepartments = TkServiceLocator.getTimeApproveService().getDeptWorkAreasByWorkAreas(workAreas);

        //KPME-1338
		/*Set<String> depts = new HashSet<String>();
		depts.addAll(userRoles.getDepartmentViewOnlyDepartments());
		depts.addAll(userRoles.getOrgAdminDepartments());
        if (depts.size() > 0) {
            reportingApprovalDepartments.putAll(TkServiceLocator.getTimeApproveService().getDeptWorkAreasByDepts(depts));
        }*/
		
		return reportingApprovalDepartments;
	}
	
	public Set<Long> getReportingWorkAreas(){
		UserRoles userRoles = getCurrentRoles();
		Set<Long> reportingWorkAreas = new HashSet<Long>();
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

    public SortedSet<Long> getWorkAreasFromUserRoles() {
        SortedSet<Long> workAreas = new TreeSet<Long>();
        workAreas.addAll(this.getCurrentRoles().getApproverWorkAreas());
        workAreas.addAll(this.getCurrentRoles().getReviewerWorkAreas());
        
        if(this.getCurrentRoles().isDepartmentAdmin()){
        	Set<String> deptAdminDepts = this.getCurrentRoles().getOrgAdminDepartments();
        	for(String dept : deptAdminDepts){
        		List<WorkArea> was = TkServiceLocator.getWorkAreaService().getWorkAreas(dept, TKUtils.getCurrentDate());
        		for(WorkArea wa : was){
        			workAreas.add(wa.getWorkArea());
        		}
        	}
        }

        return workAreas;
    }

    public List<Job> getJobs() {
        return TkServiceLocator.getJobService().getJobs(getCurrentPerson().getPrincipalId(),TKUtils.getCurrentDate());
    }

    public Set<String> getDepartments() {
        List<Job> jobs = getJobs();
        Set<String> depts = new HashSet<String>();

        for ( Job job : jobs ) {
            depts.add(job.getDept());
        }

        return depts;
    }
}
