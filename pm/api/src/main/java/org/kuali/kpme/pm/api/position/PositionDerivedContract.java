package org.kuali.kpme.pm.api.position;

import org.kuali.kpme.core.api.mo.KpmeEffectiveDerivedDataTransferObject;

public interface PositionDerivedContract extends KpmeEffectiveDerivedDataTransferObject {
	
	PositionContract getOwner();

}