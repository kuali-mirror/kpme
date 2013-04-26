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
package org.kuali.kpme.core.bo.salarygroup.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.salarygroup.SalGroup;

public interface SalGroupDao {
	public void saveOrUpdate(SalGroup salGroup);
	public SalGroup getSalGroup(String salGroup, LocalDate asOfDate);
	public SalGroup getSalGroup(String hrSalGroupId);
	public int getSalGroupCount(String salGroup);

    List<SalGroup> getSalGroups(String hrSalGroup, String descr, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist);
}
