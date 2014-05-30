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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.kpme.core.api.bo.HrKeyedSetBusinessObjectContract;
import org.kuali.kpme.core.bo.derived.HrBusinessObjectKey;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;



public abstract class HrKeyedSetBusinessObject<O extends HrKeyedSetBusinessObject<O, K>, 
											   K extends HrBusinessObjectKey<O, K>> extends HrBusinessObject implements HrKeyedSetBusinessObjectContract {

	private static final long serialVersionUID = -2616362205962723831L;
	
	protected List<K> effectiveKeyList = new ArrayList<K>();
	
	@Override
	public Set<K> getEffectiveKeySet() {
		HashSet<K> effectiveKeySet = new HashSet<K>();
		if(CollectionUtils.isNotEmpty(this.getEffectiveKeyList())) {
			effectiveKeySet = new HashSet<K>(this.getEffectiveKeyList()); 
		}
		return effectiveKeySet;
	}
	
	
	public List<K> getEffectiveKeyList() {
		return this.effectiveKeyList;
	}
	
	public void setEffectiveKeyList(List<K> effectiveKeyList) {
		this.effectiveKeyList = effectiveKeyList;
	}
	
	public Set<String> getGroupKeyCodeSet() {
		Set<String> computedSet = new HashSet<String>();
		Set<K> keys = this.getEffectiveKeySet();
		if(CollectionUtils.isNotEmpty(keys)) {
			// iterate over the key set and extract out the group key codes
			for(K key : keys) {
				computedSet.add(key.getGroupKeyCode());
			}
		}
		return computedSet;
	}	
	
	public Set<HrGroupKeyBo> getGroupKeySet() {
		Set<HrGroupKeyBo> computedSet = new HashSet<HrGroupKeyBo>();
		Set<K> keys = this.getEffectiveKeySet();
		if(CollectionUtils.isNotEmpty(keys)) {
			// iterate over the key set and extract out the group key objects
			for(K key : keys) {
				HrGroupKeyBo groupKey = key.getGroupKey();
				if(groupKey != null) {
					computedSet.add(groupKey);
				}
			}
		}
		return computedSet;
	}
	
 
}