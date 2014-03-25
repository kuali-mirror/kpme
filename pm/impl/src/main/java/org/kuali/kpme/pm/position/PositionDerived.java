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