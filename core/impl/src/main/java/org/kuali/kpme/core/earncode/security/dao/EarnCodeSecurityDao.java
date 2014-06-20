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
package org.kuali.kpme.core.earncode.security.dao;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.earncode.security.EarnCodeSecurityBo;

import java.util.List;

public interface EarnCodeSecurityDao {

	public void saveOrUpdate(EarnCodeSecurityBo earnCodeSecurity);

	public void saveOrUpdate(List<EarnCodeSecurityBo> earnCodeSecList);

	public List<EarnCodeSecurityBo> getEarnCodeSecurities(String department, String hrSalGroup, LocalDate asOfDate, String groupKeyCode);

	public EarnCodeSecurityBo getEarnCodeSecurity(String hrEarnCodeSecId);
	
	public List<EarnCodeSecurityBo> searchEarnCodeSecurities(String dept, String salGroup, String earnCode, LocalDate fromEffdt, LocalDate toEffdt,
														   String active, String showHistory, String groupKeyCode);
	
	public int getEarnCodeSecurityCount(String dept, String salGroup, String earnCode, String employee, String approver, String payrollProcessor,
                                        String active, LocalDate effdt, String hrDeptEarnCodeId, String groupKeyCode);
	
	public int getNewerEarnCodeSecurityCount(String earnCode, LocalDate effdt);
	
	public List<EarnCodeSecurityBo> getEarnCodeSecurityList(String dept,
			String salGroup, String earnCode, String employee, String approver, String payrollProcessor, 
			String active, LocalDate effdt, String groupKeyCode);
}
