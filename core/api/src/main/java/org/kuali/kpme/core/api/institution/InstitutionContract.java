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
package org.kuali.kpme.core.api.institution;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;

/**
 * <p>InstitutionContract interface.</p>
 *
 */
public interface InstitutionContract extends HrBusinessObjectContract {
	
	/**
	 * Text that identifies this Institution
	 * 
	 * <p>
	 * institutionCode of Institution
	 * </p>
	 * 
	 * @return institutionCode for Institution
	 */
	public String getInstitutionCode();
	
	/**
	 * Text that describes this Institution
	 * 
	 * <p>
	 * description of Institution
	 * </p>
	 * 
	 * @return description for Institution
	 */
	public String getDescription();
	
	/**
	 * Primary key of Institution object associated with this Assignment
	 * 
	 * <p>
	 * pmInstitutionId of Institution
	 * </p>
	 * 
	 * @return pmInstitutionId for Institution
	 */
	public String getPmInstitutionId();
}
