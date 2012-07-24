package org.kuali.hr.time.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.util.GlobalVariables;

import com.google.common.collect.Multimap;

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

	private Person targetPerson = null;

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
		return GlobalVariables.getUserSession().getActualPerson();
	}
    
    /**
     * Returns the current 'acting' person. This will be either the back door
     * person, or the actual person.
     *
     * @return the current 'acting' Person (backdoor or actual).
     */
	public Person getCurrentPerson() {
		return GlobalVariables.getUserSession().getPerson();
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

	public void clearTargetUser() {
		GlobalVariables.getUserSession().getObjectMap().remove(TkConstants.TK_TARGET_USER_PERSON);
		this.targetPerson = null;
	}

	public UserRoles getActualPersonRoles() {
		return getUserRoles(GlobalVariables.getUserSession().getActualPerson().getPrincipalId());
	}
	
    /**
     * Provides access to the current roles.
     * @return The roles of the current 'acting' Person (backdoor or actual).
     */
	public UserRoles getCurrentPersonRoles() {
		return getUserRoles(GlobalVariables.getUserSession().getPrincipalId());
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
     * Target > Back Door > Actual
     * @return The Principal ID of the user we are viewing.
     */
    public String getTargetPrincipalId() {
        Person p = getTargetPerson();
        if (p == null) {
            p = getCurrentPerson();
        }
        return p.getPrincipalId();
    }

    /**
     * Returns a UserRoles object for the target person if present, otherwise
     * the backdoor, and finally the actual.
     *
     * @return A UserRoles object: target > backdoor > actual.
     */
    public UserRoles getCurrentTargetRoles() {
    	return getUserRoles(getTargetPrincipalId());
    }
    
    public static TKUser getUser(Person target, Date asOfDate) {
        TKUser tkUser = new TKUser();

        tkUser.setTargetPerson(target);

        return tkUser;
    }

    public static TkUserRoles getUserRoles(String principalId) {
    	List<TkRole> roles = TkServiceLocator.getTkRoleService().getRoles(principalId, TKUtils.getCurrentDate());
		List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(principalId, TKUtils.getCurrentDate());
		
		return new TkUserRoles(principalId, roles, assignments);
    }

	public boolean isSystemAdmin() {
		UserRoles userRoles = getCurrentPersonRoles();
		return userRoles.isSystemAdmin();
	}

	public boolean isLocationAdmin() {
		return getLocationAdminAreas().size() > 0;
	}

	public boolean isDepartmentAdmin() {
		return getDepartmentAdminAreas().size() > 0;
	}

	public boolean isGlobalViewOnly() {
		UserRoles userRoles = getCurrentPersonRoles();
		return userRoles.isGlobalViewOnly();
	}

	public boolean isDepartmentViewOnly() {
		UserRoles userRoles = getCurrentPersonRoles();
		return userRoles.getDepartmentViewOnlyDepartments().size() > 0;
	}

	public boolean isReviewer(){
		UserRoles userRoles = getCurrentPersonRoles();
		return userRoles.getReviewerWorkAreas().size() > 0;
	}

	public boolean isApprover() {
		UserRoles userRoles = getCurrentPersonRoles();
		return userRoles.getApproverWorkAreas().size() > 0;
	}
	
	public Multimap<String, Long> getReportingApprovalDepartments(){
		UserRoles userRoles = getCurrentPersonRoles();
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
		UserRoles userRoles = getCurrentPersonRoles();
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
		UserRoles userRoles = getCurrentPersonRoles();
		return userRoles.getOrgAdminCharts();
	}

	public Set<String> getDepartmentAdminAreas() {
		UserRoles userRoles = getCurrentPersonRoles();
		return userRoles.getOrgAdminDepartments();
	}

    public SortedSet<Long> getWorkAreasFromUserRoles() {
        SortedSet<Long> workAreas = new TreeSet<Long>();
        workAreas.addAll(this.getCurrentPersonRoles().getApproverWorkAreas());
        workAreas.addAll(this.getCurrentPersonRoles().getReviewerWorkAreas());
        
        if(this.getCurrentPersonRoles().isDepartmentAdmin()){
        	Set<String> deptAdminDepts = this.getCurrentPersonRoles().getOrgAdminDepartments();
        	for(String dept : deptAdminDepts){
        		List<WorkArea> was = TkServiceLocator.getWorkAreaService().getWorkAreas(dept, TKUtils.getCurrentDate());
        		for(WorkArea wa : was){
        			workAreas.add(wa.getWorkArea());
        		}
        	}
        }

        return workAreas;
    }

}