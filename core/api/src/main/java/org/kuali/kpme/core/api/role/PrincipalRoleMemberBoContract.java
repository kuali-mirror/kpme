package org.kuali.kpme.core.api.role;

import org.kuali.rice.kim.api.identity.Person;

/**
 * <p>PrincipalRoleMemberBoContract interface.</p>
 *
 */
public interface PrincipalRoleMemberBoContract extends KPMERoleMemberBoContract {
	
	/**
	 * Id of the person that is associated with the Role 
	 * 
	 * <p>
	 * principalId of PrincipalRoleMemberBo
	 * </p>
	 * 
	 * @return principalId for PrincipalRoleMemberBo
	 */
	public String getPrincipalId();
	
	/**
	 * Name of the person that is associated with the Role 
	 * 
	 * <p>
	 * principalName of PrincipalRoleMemberBo
	 * </p>
	 * 
	 * @return principalName for PrincipalRoleMemberBo
	 */
	public String getPrincipalName();

	/**
	 * The person that is associated with the Role 
	 * 
	 * <p>
	 * person of PrincipalRoleMemberBo
	 * </p>
	 * 
	 * @return person for PrincipalRoleMemberBo
	 */
	public Person getPerson();
}
