package org.kuali.kpme.core.api.role;

import java.util.Date;

import org.kuali.rice.kim.api.role.RoleMemberContract;

/**
 * <p>KPMERoleMemberBoContract interface.</p>
 *
 */
public interface KPMERoleMemberBoContract extends RoleMemberContract {
	
	/**
	 * Date on which the KPMERoleMemberBo becomes effective
	 * 
	 * <p>
	 * effectiveDate of KPMERoleMemberBo
	 * <p>
	 * 
	 * @return effectiveDate of KPMERoleMemberBo
	 */
	public Date getEffectiveDate();
	
	/**
	 * Date the KPMERoleMemberBo expires on
	 * 
	 * <p>
	 * expirationDate of KPMERoleMemberBo
	 * <p>
	 * 
	 * @return expirationDate of KPMERoleMemberBo
	 */
	public Date getExpirationDate();
	
	/**
	 * Role name of the KPMERoleMemberBo
	 * 
	 * <p>
	 * roleName of KPMERoleMemberBo
	 * <p>
	 * 
	 * @return roleName of KPMERoleMemberBo
	 */
	public String getRoleName();

}
