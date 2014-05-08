package org.kuali.kpme.core.api.mo;

public interface KpmeEffectiveKey extends KpmeEffectiveDerivedDataTransferObject, KeyedData {
	
	@Override
	KpmeEffectiveKeyedSetDataTransferObject getOwner();

}