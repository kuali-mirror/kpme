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

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

@SuppressWarnings("unchecked")
public abstract class PositionRoleMemberBo extends KPMERoleMemberBo {

	private static final long serialVersionUID = 2946101125828699043L;

	public String getPositionNumber() {
		String positionNumber = MapUtils.getString(getAttributes(), KPMERoleMemberAttribute.POSITION.getRoleMemberAttributeName());
		
		return StringUtils.defaultString(positionNumber);
	}
	
}