package org.kuali.hr.time.util;

import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.user.pref.UserPreferences;
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

	private UserPreferences actualUserPreferences;
    private UserPreferences backdoorUserPreferences;
    private UserPreferences targetUserPreferences;

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
	 *  Target User > Back Door User > Actual User
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
     * Returns the UserPreferences for the current user. (backdoor > actual)
     * @return A UserPreferences object for backdoor or actual.
     */
	public UserPreferences getCurrentUserPreferences() {
		UserPreferences p = this.backdoorUserPreferences;
        if (p == null)
            p = this.actualUserPreferences;

        return p;
	}

    /**
     * UserPreferences for target > backdoor > actual.
     * @return UserPreferences object for either target if present, backdoor, or
     * actual (in that order).
     */
    public UserPreferences getTargetUserPreferences() {
        UserPreferences p = this.targetUserPreferences;
        if (p == null)
            p = this.getCurrentUserPreferences();

        return p;
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

    public UserPreferences getActualUserPreferences() {
        return actualUserPreferences;
    }

    public void setActualUserPreferences(UserPreferences actualUserPreference) {
        this.actualUserPreferences = actualUserPreference;
    }

    public UserPreferences getBackdoorUserPreferences() {
        return backdoorUserPreferences;
    }

    public void setBackdoorUserPreferences(UserPreferences backdoorUserPreference) {
        this.backdoorUserPreferences = backdoorUserPreference;
    }

    public void setTargetUserPreferences(UserPreferences targetUserPreference) {
        this.targetUserPreferences = targetUserPreference;
    }
}
