/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.pm.api.pstncontracttype;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.institution.InstitutionContract;
import org.kuali.kpme.core.api.location.LocationContract;

/**
 * <p>PstnContractTypeContract interface</p>
 *
 */
public interface PstnContractTypeContract extends HrBusinessObjectContract {

	/**
	 * The primary key of a PstnContractType entry saved in a database
	 * 
	 * <p>
	 * pmCntrctTypeId of a PstnContractType.
	 * <p>
	 * 
	 * return pmCntrctTypeId for PstnContractType
	 */
	public String getPmCntrctTypeId();

	/**
	 * The short text descriptive of the PstnContractType
	 * 
	 * <p>
	 * name of a PstnContractType.
	 * <p>
	 * 
	 * return name for PstnContractType
	 */
	public String getName();

	/**
	 * The long description of the PstnContractType
	 * 
	 * <p>
	 * description of a PstnContractType.
	 * <p>
	 * 
	 * return description for PstnContractType
	 */
	public String getDescription();

	/**
	 * The institution associated with the PstnContractType
	 * 
	 * <p>
	 * institution of a PstnContractType.
	 * <p>
	 * 
	 * return institution for PstnContractType
	 */
	public String getInstitution();

	/**
	 * The location associated with the PstnContractType
	 * 
	 * <p>
	 * location of a PstnContractType.
	 * <p>
	 * 
	 * return location for PstnContractType
	 */
	public String getLocation();

	/**
	 * The Location object associated with the PstnContractType
	 * 
	 * <p>
	 * location of a PstnContractType.
	 * <p>
	 * 
	 * return location for PstnContractType
	 */
	public LocationContract getLocationObj();

	/**
	 * The Institution object associated with the PstnContractType
	 * 
	 * <p>
	 * institution of a PstnContractType.
	 * <p>
	 * 
	 * return institution object for PstnContractType
	 */
	public InstitutionContract getInstitutionObj();

}
