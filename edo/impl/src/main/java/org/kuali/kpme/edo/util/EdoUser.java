package org.kuali.kpme.edo.util;


import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tcbradley
 * Date: 10/15/12
 * Time: 3:00 PM
 */
public class EdoUser {

    private List<String> currentRoleList = new ArrayList<String>();
   
    //edo-33
    private String emplId; // emplId implies principal id in rice
    private String name; //fullname
    private String networkId; //networkId implies principal name in rice
    private String emailAddress;
    private String deptName;

    public List<String> getCurrentRoleList() {
        return currentRoleList;
    }
    public void setCurrentRoleList(List<String> currentRoleList) {
		this.currentRoleList = currentRoleList;
	}
    
	public String getEmplId() {
		return emplId;
	}

	public void setEmplId(String emplId) {
		this.emplId = emplId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	//edo target person
	public static void setTargetPerson(Person targetPerson) {
        GlobalVariables.getUserSession().addObject(EdoConstants.EDO_TARGET_USER_PERSON, targetPerson);
    }

    public static boolean isTargetInUse() {
        return (Person) GlobalVariables.getUserSession().retrieveObject(EdoConstants.EDO_TARGET_USER_PERSON) != null;
    }

    public static void clearTargetUser() {
        GlobalVariables.getUserSession().removeObject(EdoConstants.EDO_TARGET_USER_PERSON);
    }
    
    public static List<String> getCurrentTargetRoles() {
        return EdoServiceLocator.getAuthorizationService().getRoleList(getCurrentTargetPerson().getPrincipalId());
    }
    
   
    /**
     * Returns a Person object for the target person if present, otherwise
     * the backdoor, and finally the actual.
     *
     * @return A Person object: target > backdoor > actual.
     */
    public static Person getCurrentTargetPerson() {
        Person p = (Person) GlobalVariables.getUserSession().retrieveObject(EdoConstants.EDO_TARGET_USER_PERSON);
        if (p == null) {
            p = GlobalVariables.getUserSession().getPerson();
        }
        return p;
    }
}
