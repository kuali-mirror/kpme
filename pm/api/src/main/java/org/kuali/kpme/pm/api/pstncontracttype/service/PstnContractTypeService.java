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
package org.kuali.kpme.pm.api.pstncontracttype.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.api.pstncontracttype.PstnContractType;

public interface PstnContractTypeService {
	/**
	 * retrieve the PstnContractType with given id
	 * @param pmCntrctTypeId
	 * @return
	 */
	public PstnContractType getPstnContractTypeById(String pmCntrctTypeId);
	
	/**
	 * Get list of PstnContractType with given group, institution, location and effective date
	 * wild card allowed
	 * @param institution
	 * @param location
	 * @param asOfDate
	 * @return
	 */
	public List<PstnContractType> getPstnContractTypeList(String groupKeyCode, LocalDate asOfDate);
	
	/**
	 * Get list of PstnContractType with given name, institution, location and effective date
	 * wild card allowed
	 * @param name
	 * @param institution
	 * @param location
	 * @param asOfDate
	 * @return
	 */
	public List<PstnContractType> getPstnContractTypeList(String name, String groupKeyCode, LocalDate asOfDate);
}
