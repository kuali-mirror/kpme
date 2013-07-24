package org.kuali.kpme.core.api.role.location;

import java.util.Map;

import org.joda.time.DateTime;
import org.kuali.kpme.core.api.role.PrincipalRoleMemberBoContract;
import org.kuali.rice.core.api.membership.MemberType;

/**
 * <p>LocationPrincipalRoleMemberBoContract interface.</p>
 * 
 */
public interface LocationPrincipalRoleMemberBoContract extends PrincipalRoleMemberBoContract {
	
	/**
	 * Id for the LocationPrincipalRoleMemberBo
	 * 
	 * <p>
	 * id of LocationPrincipalRoleMemberBo
	 * </p>
	 * 
	 * @return id for LocationPrincipalRoleMemberBo
	 */
	public String getId();

	/**
	 * Rold Id for the LocationPrincipalRoleMemberBo
	 * 
	 * <p>
	 * roleId of LocationPrincipalRoleMemberBo
	 * </p>
	 * 
	 * @return roleId for LocationPrincipalRoleMemberBo
	 */
    public String getRoleId();
    
    /**
	 * Attribute map for the LocationPrincipalRoleMemberBo
	 * 
	 * <p>
	 * attributes of LocationPrincipalRoleMemberBo
	 * </p>
	 * 
	 * @return attributes for LocationPrincipalRoleMemberBo
	 */
    public Map<String, String> getAttributes();
    
    /**
	 * Id of the member associated with the LocationPrincipalRoleMemberBo
	 * 
	 * <p>
	 * memberId of LocationPrincipalRoleMemberBo
	 * </p>
	 * 
	 * @return memberId for LocationPrincipalRoleMemberBo
	 */
    public String getMemberId();

    /**
	 * Member type object associated with the LocationPrincipalRoleMemberBo
	 * 
	 * <p>
	 * memberType of LocationPrincipalRoleMemberBo
	 * </p>
	 * 
	 * @return memberType for LocationPrincipalRoleMemberBo
	 */
    public MemberType getType();

    /**
	 * Date the LocationPrincipalRoleMemberBo becomes active
	 * 
	 * <p>
	 * activeFromDate of LocationPrincipalRoleMemberBo
	 * </p>
	 * 
	 * @return activeFromDate for LocationPrincipalRoleMemberBo
	 */
    public DateTime getActiveFromDate();

    /**
	 * Last day the LocationPrincipalRoleMemberBo stays active
	 * 
	 * <p>
	 * activeToDate of LocationPrincipalRoleMemberBo
	 * </p>
	 * 
	 * @return activeToDate for LocationPrincipalRoleMemberBo
	 */
    public DateTime getActiveToDate();
    
    /**
	 * Indicates if the LocationPrincipalRoleMemberBo is active on a given date
	 *  
	 * @return true if active on activeAsOfDate, false if not
	 */
    public boolean isActive(DateTime activeAsOfDate);

    /**
	 * Indicates if the LocationPrincipalRoleMemberBo is active on the current date
	 *  
	 * @return true if active on current date, false if not
	 */
    public boolean isActive();

    /**
	 * Version number of the LocationPrincipalRoleMemberBo
	 * 
	 * <p>
	 * versionNumber of LocationPrincipalRoleMemberBo
	 * </p>
	 * 
	 * @return versionNUmber for LocationPrincipalRoleMemberBo
	 */
    public Long getVersionNumber();

    /**
   	 * Object Id of the LocationPrincipalRoleMemberBo
   	 * 
   	 * <p>
   	 * objectId of LocationPrincipalRoleMemberBo
   	 * </p>
   	 * 
   	 * @return objectId for LocationPrincipalRoleMemberBo
   	 */
    public String getObjectId();
}
