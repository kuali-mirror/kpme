package org.kuali.kpme.pm.position;

import org.kuali.kpme.core.bo.derived.HrBusinessObjectDerived;
import org.kuali.kpme.pm.api.position.PositionDerivedContract;

public abstract class PositionDerived extends HrBusinessObjectDerived implements PositionDerivedContract {

	private static final long serialVersionUID = -4500160649209884023L;
	
	protected String hrPositionId;
	private PositionBo positionBo;
	private static final String POSITION_BO = "positionBo";
	
	
	public PositionBo getPositionBo() {
		if(this.positionBo == null) {
			refreshReferenceObject(POSITION_BO);
		}
		return this.positionBo;
	}

	public void setPositionBo(PositionBo positionBo) {
		this.positionBo = positionBo;
	}

	@Override
	public PositionBo getOwner() {
		return getPositionBo();
	}
	
	public String getHrPositionId() {
		return hrPositionId;
	}

	public void setHrPositionId(String hrPositionId) {
		this.hrPositionId = hrPositionId;
	}
	

}