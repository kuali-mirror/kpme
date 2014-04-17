package org.kuali.kpme.pm.position;

import org.kuali.kpme.core.bo.derived.HrKeyedBusinessObjectDerived;
import org.kuali.kpme.pm.api.position.PositionKeyedDerivedContract;

public abstract class PositionKeyedDerived extends HrKeyedBusinessObjectDerived implements PositionKeyedDerivedContract {
	
	private static final long serialVersionUID = -4500160649209884023L;
	
	protected String hrPositionId;
	private PositionBo owner;
	private static final String OWNER = "owner";
	
	@Override
	public String getHrPositionId() {
		return hrPositionId;
	}

	public void setHrPositionId(String hrPositionId) {
		this.hrPositionId = hrPositionId;
	}
	
	@Override
	public PositionBo getOwner() {
		if(this.owner == null) {
			refreshReferenceObject(OWNER);
		}
		return this.owner;
	}
	
	public void setOwner(PositionBo owner) {
		this.owner = owner;
	}
}
