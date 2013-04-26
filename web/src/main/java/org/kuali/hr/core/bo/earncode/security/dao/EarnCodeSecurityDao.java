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
package org.kuali.hr.core.bo.earncode.security.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.core.bo.earncode.security.EarnCodeSecurity;

public interface EarnCodeSecurityDao {

	public void saveOrUpdate(EarnCodeSecurity earnCodeSecurity);

	public void saveOrUpdate(List<EarnCodeSecurity> earnCodeSecList);

	public List<EarnCodeSecurity> getEarnCodeSecurities(String department, String hr_sal_group, String location, LocalDate asOfDate);

	public EarnCodeSecurity getEarnCodeSecurity(String hrEarnCodeSecId);
	
	public List<EarnCodeSecurity> searchEarnCodeSecurities(String dept, String salGroup, String earnCode, String location, LocalDate fromEffdt, LocalDate toEffdt, 
														   String active, String showHistory);
	
	public int getEarnCodeSecurityCount(String dept, String salGroup, String earnCode, String employee, String approver, String location,
                                        String active, LocalDate effdt, String hrDeptEarnCodeId);
	
	public int getNewerEarnCodeSecurityCount(String earnCode, LocalDate effdt);
}
