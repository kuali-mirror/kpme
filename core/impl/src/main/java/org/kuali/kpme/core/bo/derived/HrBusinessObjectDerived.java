package org.kuali.kpme.core.bo.derived;

import java.util.Date;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.bo.derived.HrBusinessObjectDerivedContract;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public abstract class HrBusinessObjectDerived extends PersistableBusinessObjectBase implements HrBusinessObjectDerivedContract {

	private static final long serialVersionUID = 1318362548103383417L;

	@Override
	public LocalDate getEffectiveLocalDateOfOwner() {
		LocalDate retVal = null;
		if(this.getOwner() != null) {
			retVal = this.getOwner().getEffectiveLocalDate();
		}
		return retVal;
	}

	@Override
	public Date getEffectiveDateOfOwner() {
		Date retVal = null;
		if(this.getOwner() != null) {
			retVal = this.getOwner().getEffectiveDate();
		}
		return retVal;
	}

	@Override
	public boolean isEquivalentTo(HrBusinessObjectDerivedContract obj) {
		return this.equals(obj);
	}

	@Override
	public abstract HrBusinessObject getOwner();

}