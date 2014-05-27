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

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.bo.derived.HrBusinessObjectKeyContract;
import org.kuali.kpme.core.api.mo.EffectiveKey;
import org.kuali.kpme.core.bo.HrKeyedSetBusinessObject;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.core.service.HrServiceLocator;

public abstract class HrBusinessObjectKey <O extends HrKeyedSetBusinessObject<O, K>, 
										   K extends HrBusinessObjectKey<O, K>> extends HrBusinessObjectDerived<O> implements HrBusinessObjectKeyContract {
	
	private static final long serialVersionUID = 4931341075109022350L;
	
	protected String groupKeyCode;
	protected transient HrGroupKeyBo groupKey;
	
	// the foreign key linking back to the owner
	private String ownerId;
	// the PK for the table corresponding to this BO
	private String id;

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
	
	
	@Override
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	public String getId() {
		return id;
	}
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	public boolean equals(Object other) {
		boolean retVal = false;
	    if (this == other) {
	    	retVal = true;
	    }
	    else if( (other != null) && (other.getClass() == this.getClass()) ) {
	    	if(StringUtils.equals(this.groupKeyCode, this.getClass().cast(other).groupKeyCode)) {
	    		retVal = true;
	    	}
	    }	    
	    return retVal;	
	}
	
	 
	public int hashCode() {
		int hash = 1;
		hash = hash * 31  + (this.groupKeyCode == null ? 0 : groupKeyCode.hashCode());
		return hash;
	}
	
	protected static <T extends HrBusinessObjectKey<?,?>> T commonFromLogic(EffectiveKey im, T keyBo) {
		if (im == null) {
			return null;
		}
		keyBo.setId(im.getId());
		keyBo.setOwnerId(im.getOwnerId());
		keyBo.setVersionNumber(im.getVersionNumber());
		keyBo.setObjectId(im.getObjectId());
		keyBo.setGroupKey(HrGroupKeyBo.from(im.getGroupKey()));
		keyBo.setGroupKeyCode(im.getGroupKeyCode());
		return keyBo;
	}
	
	public static EffectiveKey to(HrBusinessObjectKey<?,?> bo) {
		if (bo == null) {
			return null;
		}
		return EffectiveKey.Builder.create(bo).build();
	}

}