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
package org.kuali.kpme.core.institution.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.institution.Institution;

public interface InstitutionService {

	public Institution getInstitutionById(String institutionId);
	
	public List<Institution> getActiveInstitutionsAsOf(LocalDate asOfDate);
	
	public List<Institution> getInstitutionsByCode(String code);
	
	/**
	 * retrieve the institution with given code and exists before given date
	 * @param institutionCode
	 * @param asOfDate
	 * @return
	 */
	public Institution getInstitution(String institutionCode, LocalDate asOfDate);
	
	/**
	 * Get the count of institutions that match the given institutionCode and eff date
	 * @param institutionCode
	 * @param asOfDate
	 * @return
	 */
	public int getInstitutionCount(String institutionCode, LocalDate asOfDate);
	
}
