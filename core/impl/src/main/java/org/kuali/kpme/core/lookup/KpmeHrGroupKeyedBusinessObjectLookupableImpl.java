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
package org.kuali.kpme.core.lookup;

import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.LookupService;

public class KpmeHrGroupKeyedBusinessObjectLookupableImpl extends KpmeHrBusinessObjectLookupableImpl {

	private static final long serialVersionUID = -1720282920640239124L;
	
	private static final String KPME_GRP_KEYED_HR_BUSINESS_OBJECT_LOOKUP_SERVICE = "kpmeHrGroupKeyedBusinessObjectLookupService";
	
	// override to create the correct service type
	@Override
	protected LookupService createLookupServiceInstance() {
		return (LookupService) KRADServiceLocatorWeb.getService(KPME_GRP_KEYED_HR_BUSINESS_OBJECT_LOOKUP_SERVICE);
	}
}
