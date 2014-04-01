package org.kuali.kpme.pm.classification;

import org.kuali.kpme.core.bo.derived.HrBusinessObjectDerived;
import org.kuali.kpme.pm.api.classification.ClassificationDerivedContract;

public class ClassificationDerived extends HrBusinessObjectDerived implements ClassificationDerivedContract {
	
	private static final long serialVersionUID = -7394015906298684406L;

	protected String pmPositionClassId;
	private Classification owner;
	private static final String OWNER = "owner";
	
	@Override
	public String getPmPositionClassId() {
		return pmPositionClassId;
	}
	
	public void setPmPositionClassId(String pmPositionClassId) {
		this.pmPositionClassId = pmPositionClassId;
	}

	@Override
	public Classification getOwner() {
		if(this.owner == null) {
			refreshReferenceObject(OWNER);
		}
		return this.owner;
	}

	public void setOwner(Classification owner) {
		this.owner = owner;
	}

}