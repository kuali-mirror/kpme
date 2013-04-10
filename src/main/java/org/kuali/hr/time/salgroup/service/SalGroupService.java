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
package org.kuali.hr.time.salgroup.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.time.salgroup.SalGroup;
import org.springframework.cache.annotation.Cacheable;

public interface SalGroupService {
	/**
	 * Fetch a SalGroup as of a particular date
	 * @param salGroup
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= SalGroup.CACHE_NAME, key="'salGroup=' + #p0 + '|' + 'asOfDate=' + #p1")
	public SalGroup getSalGroup(String salGroup, LocalDate asOfDate);

    @Cacheable(value= SalGroup.CACHE_NAME, key="'hrSalGroupId=' + #p0")
	public SalGroup getSalGroup(String hrSalGroupId);
	
	public int getSalGroupCount(String salGroup);

    List<SalGroup> getSalGroups(String salGroup, String descr, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist);
}
