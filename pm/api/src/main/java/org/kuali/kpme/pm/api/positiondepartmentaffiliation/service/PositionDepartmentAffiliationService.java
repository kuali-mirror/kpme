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
package org.kuali.kpme.pm.api.positiondepartmentaffiliation.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.api.positiondepartmentaffiliation.PositionDepartmentAffiliationContract;

public interface PositionDepartmentAffiliationService {

	/**
	 * retrieve the PositionDepartmentAffiliation with given id
	 * @param pmPositionDeptAfflId
	 * @return
	 */
	public PositionDepartmentAffiliationContract getPositionDepartmentAffiliationById(String pmPositionDeptAfflId);
	
	/**
	 * retrieve the PositionDepartmentAffiliation with given affiliation type
	 * @param positionDeptAfflType
	 * @return
	 */
	public PositionDepartmentAffiliationContract getPositionDepartmentAffiliationByType(String positionDeptAfflType);
	
	public List<? extends PositionDepartmentAffiliationContract> getPositionDepartmentAffiliationList(String positionDeptAfflType, LocalDate asOfDate);
	
	/**
	 * Retrieve the list of all active affiliations	
	 * @return
	 */
	public List<? extends PositionDepartmentAffiliationContract> getAllActiveAffiliations();
}
