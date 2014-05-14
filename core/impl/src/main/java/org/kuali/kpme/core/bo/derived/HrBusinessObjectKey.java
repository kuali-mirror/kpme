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

import org.kuali.kpme.core.api.bo.derived.HrBusinessObjectKeyContract;
import org.kuali.kpme.core.bo.HrKeyedSetBusinessObject;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.core.service.HrServiceLocator;

public abstract class HrBusinessObjectKey <O extends HrKeyedSetBusinessObject<O, K>, 
										   K extends HrBusinessObjectKey<O, K>> extends HrBusinessObjectDerived<O> implements HrBusinessObjectKeyContract {
	
	private static final long serialVersionUID = 4931341075109022350L;
	
	protected String groupKeyCode;
	protected transient HrGroupKeyBo groupKey;

	@Override
	public O getOwner() {
		return super.getOwner();
	}
	
	@Override
	public void setOwner(O owner) {
		super.setOwner(owner);
	}
	
	@Override
	public String getGroupKeyCode() {
		return groupKeyCode;
	}

	public void setGroupKeyCode(String groupKeyCode) {
		this.groupKeyCode = groupKeyCode;
	}
	
	@Override
	public HrGroupKeyBo getGroupKey() {
		if (groupKey == null) {
			groupKey = HrGroupKeyBo.from(HrServiceLocator.getHrGroupKeyService().getHrGroupKey(getGroupKeyCode(), this.getEffectiveLocalDateOfOwner()));
		}
		return groupKey;
	}

	public void setGroupKey(HrGroupKeyBo groupKey) {
		this.groupKey = groupKey;
	}

}