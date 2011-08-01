package org.kuali.hr.time.user.service;

import java.sql.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.service.AssignmentService;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.roles.service.TkRoleService;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;

public class UserServiceImpl implements UserService {

    @Override
    public TKUser buildTkUser(String actualPrincipalId, Date asOfDate) {
        Person person = KIMServiceLocator.getPersonService().getPerson(actualPrincipalId);
        return buildTkUser(person, null, null, asOfDate);
    }

    @Override
    public TKUser buildTkUser(Person actual, Person backdoor, Person target, Date asOfDate) {
        TKUser tkUser = new TKUser();

        if (actual == null) {
            throw new RuntimeException("Can not create user with empty principal id.");
        }

        tkUser.setActualPerson(actual);
        tkUser.setBackdoorPerson(backdoor);
        tkUser.setTargetPerson(target);
        
        tkUser.setActualUserPrincipalCalendar(TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(actual.getPrincipalId(), asOfDate));
        tkUser.setActualUserJobs(TkServiceLocator.getJobSerivce().getJobs(actual.getPrincipalId(), asOfDate));
        if(!tkUser.getActualUserJobs().isEmpty()){
        	tkUser.setActualUserAssignments(TkServiceLocator.getAssignmentService().getAssignments(actual.getPrincipalId(), asOfDate));
        }
        	
        tkUser.setActualUserPreferences(TkServiceLocator.getUserPreferenceService().getUserPreferences(actual.getPrincipalId()));
        if (backdoor != null){
            tkUser.setBackdoorUserPreferences(TkServiceLocator.getUserPreferenceService().getUserPreferences(backdoor.getPrincipalId()));
            tkUser.setBackdoorUserPrincipalCalendar(TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(backdoor.getPrincipalId(), asOfDate));
            tkUser.setBackdoorUserJobs(TkServiceLocator.getJobSerivce().getJobs(backdoor.getPrincipalId(), asOfDate));
            if(!tkUser.getBackdoorUserJobs().isEmpty()){
            	tkUser.setBackdoorUserAssignments(TkServiceLocator.getAssignmentService().getAssignments(backdoor.getPrincipalId(), asOfDate));
            }
        }    
        if (target != null) {
            tkUser.setTargetUserPreferences(TkServiceLocator.getUserPreferenceService().getUserPreferences(target.getPrincipalId()));
            tkUser.setTargetUserPrincipalCalendar(TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(target.getPrincipalId(), asOfDate));
            tkUser.setTargetUserJobs(TkServiceLocator.getJobSerivce().getJobs(target.getPrincipalId(), asOfDate));
            if(!tkUser.getTargetUserJobs().isEmpty()){
            	tkUser.setTargetUserAssignments(TkServiceLocator.getAssignmentService().getAssignments(target.getPrincipalId(), asOfDate));
            }
        }
        loadRoles(tkUser);

        return tkUser;
    }

    /**
	 * Helper method to load roles.
	 *
	 * We are looking looking for the roles with the most recent effective
	 * date.
	 *
	 * @param user
	 */
	public static void loadRoles(TKUser user) {
		TkRoleService roleService = TkServiceLocator.getTkRoleService();
		AssignmentService assignmentService = TkServiceLocator.getAssignmentService();

		Date asOfDate = TKUtils.getCurrentDate();
		Date payPeriodBeginDate = TKUtils.getCurrentDate();

		if (user.getBackdoorPerson() != null) {
			List<TkRole> roles = TkServiceLocator.getTkRoleService().getRoles(user.getBackdoorPerson().getPrincipalId(), asOfDate);
			List<Assignment> assignments = assignmentService.getAssignments(user.getBackdoorPerson().getPrincipalId(), payPeriodBeginDate);
			user.setBackdoorPersonRoles(new TkUserRoles(user.getBackdoorPerson().getPrincipalId(), roles,assignments));
		}

        if (user.getTargetPerson() != null) {
            List<TkRole> roles = TkServiceLocator.getTkRoleService().getRoles(user.getTargetPerson().getPrincipalId(), asOfDate);
            List<Assignment> assignments = assignmentService.getAssignments(user.getTargetPerson().getPrincipalId(), payPeriodBeginDate);
            user.setTargetPersonRoles(new TkUserRoles(user.getTargetPerson().getPrincipalId(), roles,assignments));
        }

		List<TkRole> roles = roleService.getRoles(user.getActualPerson().getPrincipalId(), asOfDate);
		List<Assignment> assignments = assignmentService.getAssignments(user.getActualPerson().getPrincipalId(), payPeriodBeginDate);
		user.setActualPersonRoles(new TkUserRoles(user.getActualPerson().getPrincipalId(), roles, assignments));
	}

}
