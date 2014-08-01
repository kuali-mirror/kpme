package org.kuali.kpme.edo.api.group;

import org.kuali.kpme.core.api.mo.KpmeDataTransferObject;

/**
 * <p>EdoRoleResponsibilityContract interface</p>
 *
 */
public interface EdoRoleResponsibilityContract extends KpmeDataTransferObject {

	/**
	 * The identifier of the EdoRoleResponsibility
	 * 
	 * <p>
	 * edoKimRoleResponsibilityId of the EdoRoleResponsibility
	 * <p>
	 * 
	 * @return edoKimRoleResponsibilityId for EdoRoleResponsibility
	 */
	public String getEdoKimRoleResponsibilityId();
	
	/**
	 * The role name of the EdoRoleResponsibility
	 * 
	 * <p>
	 * kimRoleName of the EdoRoleResponsibility
	 * <p>
	 * 
	 * @return kimRoleName for EdoRoleResponsibility
	 */
    public String getKimRoleName();

    /**
	 * The responsibility name of the EdoRoleResponsibility
	 * 
	 * <p>
	 * kimResponsibilityName of the EdoRoleResponsibility
	 * <p>
	 * 
	 * @return kimResponsibilityName for EdoRoleResponsibility
	 */
    public String getKimResponsibilityName();

    /**
	 * The action type code of the EdoRoleResponsibility
	 * 
	 * <p>
	 * kimActionTypeCode of the EdoRoleResponsibility
	 * <p>
	 * 
	 * @return kimActionTypeCode for EdoRoleResponsibility
	 */
    public String getKimActionTypeCode();

    /**
	 * The action policy code of the EdoRoleResponsibility
	 * 
	 * <p>
	 * kimActionPolicyCode of the EdoRoleResponsibility
	 * <p>
	 * 
	 * @return kimActionPolicyCode for EdoRoleResponsibility
	 */
    public String getKimActionPolicyCode();

    /**
	 * The priority of the EdoRoleResponsibility
	 * 
	 * <p>
	 * kimPriority of the EdoRoleResponsibility
	 * <p>
	 * 
	 * @return kimPriority for EdoRoleResponsibility
	 */
    public int getKimPriority();

    /**
	 * The flag to indicate if force action is set on this role responsibility
	 * 
	 * <p>
	 * kimForceAction of the EdoRoleResponsibility
	 * <p>
	 * 
	 * @return true or false
	 */
    public boolean isKimForceAction();
}
