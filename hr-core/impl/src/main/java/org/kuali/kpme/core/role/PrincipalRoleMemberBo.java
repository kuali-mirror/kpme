/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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