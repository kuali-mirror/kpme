package org.kuali.kpme.pm.api.positionreportgroup;

import org.kuali.kpme.core.api.mo.KpmeEffectiveKey;

public interface PositionReportGroupKeyContract extends KpmeEffectiveKey {
	
	@Override
	PositionReportGroupContract getOwner();
	
	String  getPmPositionReportGroupId();
	String getPositionReportGroupKeyId();

}