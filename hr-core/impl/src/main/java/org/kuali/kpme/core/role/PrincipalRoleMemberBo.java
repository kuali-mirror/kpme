package org.kuali.kpme.core.role;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.membership.MemberType;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

@SuppressWarnings("unchecked")
public abstract class PrincipalRoleMemberBo extends KPMERoleMemberBo {

	private static final long serialVersionUID = -524283364141837235L;
	
	public String getPrincipalId() {
		return getMemberId();
	}
	
	public void setPrincipalId(String principalId) {
		setType(MemberType.PRINCIPAL);
		setMemberId(principalId);
	}
	
	public String getPrincipalName() {
		String principalFullName = StringUtils.EMPTY;
		
		if (getMemberId() != null) {
			Person person = KimApiServiceLocator.getPersonService().getPerson(getMemberId());
			
			if (person != null) {
				principalFullName = person.getName();
			}
		}
		
		return principalFullName;
	}
	
}