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

import org.kuali.kpme.core.bo.derived.HrBusinessObjectKey;

public abstract class HrKeyedSetBusinessObjectMaintainableImpl<O extends HrKeyedSetBusinessObject<O, K>, K extends HrBusinessObjectKey<O, K>> extends HrBusinessObjectMaintainableImpl {

	private static final long serialVersionUID = -1831580725621204948L;

	@SuppressWarnings("unchecked")
	@Override
    public void customSaveLogic(HrBusinessObject hrObj){
		O owner = (O) hrObj;
        for (K key : owner.getEffectiveKeyList()) {
            key.setId(null);
            key.setOwnerId(owner.getId());
        }
    }
	

}
