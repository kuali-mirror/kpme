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
package org.kuali.kpme.edo.api.candidate;

import org.kuali.kpme.core.api.mo.KpmeEffectiveKeyedDataTransferObject;
import org.kuali.kpme.core.api.util.HrApiConstants;

/**
 * <p>EdoCandidateContract interface</p>
 *
 */
public interface EdoCandidateContract extends KpmeEffectiveKeyedDataTransferObject {
	
	public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "EdoCandidateContract";

	public String getEdoCandidateId();

	public String getLastName();

	public String getFirstName();

	public String getPrincipalName();

	public String getPrimaryDeptId();

	public String getTnpDeptId();

	public String getCandidacySchool();
    
}