package org.kuali.kpme.pm.api.position;

import org.kuali.kpme.core.api.mo.KPMEEffectiveKeyedDerivedDataTransferObject;

public interface PositionKeyedDerivedContract extends KPMEEffectiveKeyedDerivedDataTransferObject {
	
	PositionContract getOwner();
	
	String getHrPositionId();
}
