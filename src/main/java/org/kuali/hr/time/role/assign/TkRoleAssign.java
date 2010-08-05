package org.kuali.hr.time.role.assign;

import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class TkRoleAssign extends PersistableBusinessObjectBase {
    
    private static final long serialVersionUID = 1L;
    
    public static final String TK_EMPLOYEE = "TK_EMPLOYEE";
    public static final String TK_APPROVER = "TK_APPROVER";
    public static final String TK_ORG_ADMIN = "TK_ORG_ADMIN";
    public static final String TK_SYS_ADMIN = "TK_SYS_ADMIN";
    
    private Long tkRoleAssignId;
    private String principalId;
    private String roleName;
    private Long workAreaId;

    public String getPrincipalId() {
	return principalId;
    }

    public void setPrincipalId(String principalId) {
	this.principalId = principalId;
    }

    public String getRoleName() {
	return roleName;
    }

    public void setRoleName(String roleName) {
	this.roleName = roleName;
    }

    public Long getWorkAreaId() {
	return workAreaId;
    }

    public void setWorkAreaId(Long workAreaId) {
	this.workAreaId = workAreaId;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected LinkedHashMap toStringMapper() {
	// TODO Auto-generated method stub
	return null;
    }

    public Long getTkRoleAssignId() {
	return tkRoleAssignId;
    }

    public void setTkRoleAssignId(Long tkRoleAssignId) {
	this.tkRoleAssignId = tkRoleAssignId;
    }

    @Override
    public boolean equals(Object obj) {
	boolean r = true;
	
	if (obj == null || !(obj instanceof TkRoleAssign)) {
	    r = false;
	} else {
	    TkRoleAssign tra = (TkRoleAssign)obj;
	    r &= StringUtils.equals(this.getPrincipalId(), tra.getPrincipalId());
	    r &= StringUtils.equals(this.getRoleName(), tra.getRoleName());
	}
		
	return r;
    }
    
    public String getUsername() {
	StringBuffer sb = new StringBuffer();
	
	Person p = KIMServiceLocator.getPersonService().getPerson(this.principalId);
	sb.append(p.getName());
	
	return sb.toString();
    }

}
