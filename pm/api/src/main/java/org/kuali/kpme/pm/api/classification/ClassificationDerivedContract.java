package org.kuali.kpme.pm.api.classification;

import org.kuali.kpme.core.api.mo.KpmeEffectiveDerivedDataTransferObject;

public interface ClassificationDerivedContract extends KpmeEffectiveDerivedDataTransferObject {
	
	ClassificationContract getOwner();
	
	String getPmPositionClassId();
	

}