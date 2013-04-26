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
package org.kuali.kpme.core.bo.workarea.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.workarea.WorkArea;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface WorkAreaService {
	
    @Cacheable(value= WorkArea.CACHE_NAME, key="'tkWorkAreaId=' + #p0")
    WorkArea getWorkArea(String tkWorkAreaId);
    
    Long getNextWorkAreaKey();
    
    List<WorkArea> getWorkAreas(String dept, String workArea, String workAreaDescr, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory);
    
    /**
     * Fetch the count of the work areas with the given department and workarea
     * @param dept
     * @param workArea
     * @return count count of the work areas with the given department and workarea
     */
    int getWorkAreaCount(String dept, Long workArea);
    
	/**
	 * Fetch WorkArea as of a particular date
	 * @param workArea
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= WorkArea.CACHE_NAME, key="'workArea=' + #p0 + '|' + 'asOfDate=' + #p1")
    WorkArea getWorkArea(Long workArea, LocalDate asOfDate);

    /**
     * Fetch a List of WorkArea objects for a given department as of the
     * indicated date.
     *
     * @param department The department we want to use.
     * @param asOfDate An effective date.
     * @return A List<WorkArea> that matches the provided params.
     */
    @Cacheable(value= WorkArea.CACHE_NAME, key="'department=' + #p0 + '|' + 'asOfDate=' + #p1")
    List<WorkArea> getWorkAreas(String department, LocalDate asOfDate);

    /**
     * Save or Update given work area
     * @param workArea
     */
    @CacheEvict(value={WorkArea.CACHE_NAME}, allEntries = true)
    void saveOrUpdate(WorkArea workArea);

}