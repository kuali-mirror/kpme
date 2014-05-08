package org.kuali.kpme.core.api.bo.derived;

import org.kuali.kpme.core.api.bo.HrKeyedSetBusinessObjectContract;
import org.kuali.kpme.core.api.mo.KpmeEffectiveKey;

public interface HrBusinessObjectKeyContract extends HrBusinessObjectDerivedContract, KpmeEffectiveKey {
	
	HrKeyedSetBusinessObjectContract getOwner();

}