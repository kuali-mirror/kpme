/**
 * Copyright 2004-2014 The Kuali Foundation
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
