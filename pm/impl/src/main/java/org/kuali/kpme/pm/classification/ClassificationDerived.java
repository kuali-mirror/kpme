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
package org.kuali.kpme.pm.classification;

import org.kuali.kpme.core.bo.derived.HrBusinessObjectDerived;
import org.kuali.kpme.pm.api.classification.ClassificationDerivedContract;

public abstract class ClassificationDerived extends HrBusinessObjectDerived<ClassificationBo> implements ClassificationDerivedContract {
	
	private static final long serialVersionUID = -7394015906298684406L;

	protected String pmPositionClassId;
	
	
	@Override
	public String getPmPositionClassId() {
		return pmPositionClassId;
	}
	
	public void setPmPositionClassId(String pmPositionClassId) {
		this.pmPositionClassId = pmPositionClassId;
	}

	@Override
	public ClassificationBo getOwner() {
		return super.getOwner();
	}

	public void setOwner(ClassificationBo owner) {
		super.setOwner(owner);
	}
	
	public String getOwnerId() {
		return this.getPmPositionClassId();
	}
	
	public void setOwnerId(String ownerId) {
		this.setPmPositionClassId(ownerId);
	}

}