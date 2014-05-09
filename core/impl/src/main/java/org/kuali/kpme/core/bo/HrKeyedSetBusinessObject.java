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
package org.kuali.kpme.core.bo;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.kpme.core.api.bo.HrKeyedSetBusinessObjectContract;
import org.kuali.kpme.core.bo.derived.HrBusinessObjectKey;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;



public abstract class HrKeyedSetBusinessObject extends HrBusinessObject implements HrKeyedSetBusinessObjectContract {

	private static final long serialVersionUID = -2616362205962723831L;
	
	protected transient Set<String> groupKeyCodeSet;
	protected transient Set<HrGroupKeyBo> groupKeySet;
	

	public abstract Set<? extends HrBusinessObjectKey> getkeySet();
	
	public Set<String> getGroupKeyCodeSet() {
		Set<String> retVal = new HashSet<String>();
		if(CollectionUtils.isEmpty(this.groupKeyCodeSet) && CollectionUtils.isNotEmpty(this.getkeySet())) {
			// iterate over the key set and extract out the group key codes
			Set<? extends HrBusinessObjectKey> keys = this.getkeySet();
			for(HrBusinessObjectKey key : keys) {
				retVal.add(key.getGroupKeyCode());
			}
		}
		// set it so that we dont have to iterate next time
		this.setGroupKeyCodeSet(retVal);
		return retVal;
	}

	public void setGroupKeyCodeSet(Set<String> groupKeyCodeSet) {
		this.groupKeyCodeSet = groupKeyCodeSet;
	}
	
	
	public Set<HrGroupKeyBo> getGroupKeySet() {
		Set<HrGroupKeyBo> retVal = new HashSet<HrGroupKeyBo>();
		if(CollectionUtils.isEmpty(this.groupKeySet) && CollectionUtils.isNotEmpty(this.getkeySet())) {
			// iterate over the key set and extract out the group key objects
			Set<? extends HrBusinessObjectKey> keys = this.getkeySet();
			for(HrBusinessObjectKey key : keys) {
				retVal.add(key.getGroupKey());
			}
		}
		// set it so that we dont have to iterate next time
		this.setGroupKeySet(retVal);
		return retVal;
	}

	public void setGroupKeySet(Set<HrGroupKeyBo> groupKeySet) {
		this.groupKeySet = groupKeySet;
	}
	
	
 
}