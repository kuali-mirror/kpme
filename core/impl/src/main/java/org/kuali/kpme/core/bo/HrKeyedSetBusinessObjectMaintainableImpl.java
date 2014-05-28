package org.kuali.kpme.core.bo;

import org.kuali.kpme.core.bo.derived.HrBusinessObjectKey;

public abstract class HrKeyedSetBusinessObjectMaintainableImpl<O extends HrKeyedSetBusinessObject<O, K>, K extends HrBusinessObjectKey<O, K>> extends HrBusinessObjectMaintainableImpl {

	private static final long serialVersionUID = -1831580725621204948L;

	@SuppressWarnings("unchecked")
	@Override
    public void customSaveLogic(HrBusinessObject hrObj){
		O owner = (O) hrObj;
        for (K key : owner.getEffectiveKeyList()) {
            key.setId(null);
            key.setOwnerId(owner.getId());
        }
    }
	

}
