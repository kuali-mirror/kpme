/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.core.bo.derived;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.bo.derived.HrBusinessObjectDerivedContract;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public abstract class HrBusinessObjectDerived<O extends HrBusinessObject> extends PersistableBusinessObjectBase implements HrBusinessObjectDerivedContract {

	private static final long serialVersionUID = 1318362548103383417L;
	protected O owner;
	private static final String OWNER = "owner";

	
	public abstract String getId();
	public abstract void setId(String id);
	
	public abstract String getOwnerId();
	public abstract void setOwnerId(String id);
	
	
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
	public O getOwner() {
		if((this.owner == null) && (StringUtils.isNotBlank(this.getOwnerId()))) {
			refreshReferenceObject(OWNER);
		}
		return this.owner;
	}
	
	public void setOwner(O owner) {
		this.owner = owner;
	}
	
	// utility method to set back reference to an owner of a derived collection
	public static <T extends HrBusinessObject> void setOwnerOfDerivedCollection(T owner, Collection<? extends HrBusinessObjectDerived<T>> derivedObjs) {
		if(derivedObjs != null) {
			for(HrBusinessObjectDerived<T> derivedObj: derivedObjs) {
				derivedObj.setOwner(owner);
			}
		}
	}

}