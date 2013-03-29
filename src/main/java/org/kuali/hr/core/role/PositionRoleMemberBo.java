package org.kuali.hr.core.role;

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