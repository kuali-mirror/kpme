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
package org.kuali.kpme.core.principal.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.cache.CacheUtils;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.rice.kim.api.identity.name.EntityName;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;

public class PrincipalHRAttributesMaintainableImpl extends HrBusinessObjectMaintainableImpl {
	private static final long serialVersionUID = 1L;

	@Override
	public void processAfterCopy(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		super.processAfterCopy(document, parameters);
		PrincipalHRAttributes principalHRAttributes = (PrincipalHRAttributes) document.getNewMaintainableObject().getDataObject();
		principalHRAttributes.setPrincipalId(null);
	}

	@Override
	public void saveBusinessObject() {
		super.saveBusinessObject();
		CacheUtils.flushCache(PrincipalHRAttributes.CACHE_NAME);
		CacheUtils.flushCache(HrConstants.CacheNamespace.KPME_GLOBAL_CACHE_NAME);
	}

	@Override
	public HrBusinessObject getObjectById(String id) {
		return (HrBusinessObject) HrServiceLocator.getPrincipalHRAttributeService().getPrincipalHRAttributes(id);
	}

    //attribute query, populates name when principalID is selected
    public EntityName getName(String principalId) {
        return KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId).getDefaultName();
    }

}
