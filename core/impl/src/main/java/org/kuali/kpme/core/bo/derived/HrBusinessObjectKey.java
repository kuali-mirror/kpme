package org.kuali.kpme.core.bo.derived;

import org.kuali.kpme.core.api.bo.derived.HrBusinessObjectKeyContract;
import org.kuali.kpme.core.bo.HrKeyedSetBusinessObject;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.core.service.HrServiceLocator;

public abstract class HrBusinessObjectKey extends HrBusinessObjectDerived implements HrBusinessObjectKeyContract {
	
	private static final long serialVersionUID = 4931341075109022350L;
	
	protected String groupKeyCode;
	protected transient HrGroupKeyBo groupKey;

	@Override
	public HrKeyedSetBusinessObject getOwner() {
		return (HrKeyedSetBusinessObject) super.getOwner();
	}
	
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
			groupKey = HrGroupKeyBo.from(HrServiceLocator.getHrGroupKeyService().getHrGroupKey(getGroupKeyCode(), this.getEffectiveLocalDateOfOwner()));
		}
		return groupKey;
	}

	public void setGroupKey(HrGroupKeyBo groupKey) {
		this.groupKey = groupKey;
	}

}