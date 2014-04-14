package org.kuali.kpme.core.bo;

import org.kuali.kpme.core.api.bo.HrKeyedBusinessObjectContract;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.core.service.HrServiceLocator;

public abstract class HrKeyedBusinessObject extends HrBusinessObject implements HrKeyedBusinessObjectContract {

	private static final long serialVersionUID = -4676316817255855400L;
	
	protected String groupKeyCode;
	protected transient HrGroupKeyBo groupKey;
	
	@Override
	public String getGroupKeyCode() {
		return groupKeyCode;
	}

	public void setGroupKeyCode(String groupKeyCode) {
		this.groupKeyCode = groupKeyCode;
	}

	@Override
	public HrGroupKeyBo getGroupKey() {
		if (groupKey == null) {
			groupKey = HrGroupKeyBo.from(HrServiceLocator
					.getHrGroupKeyService().getHrGroupKey(getGroupKeyCode(),
							getEffectiveLocalDate()));
		}
		return groupKey;
	}

	public void setGroupKey(HrGroupKeyBo groupKey) {
		this.groupKey = groupKey;
	}

}