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
package org.kuali.kpme.core.bo.workarea.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.workarea.WorkArea;

public interface WorkAreaDao {

    public WorkArea getWorkArea(Long workArea, LocalDate asOfDate);
    public List<WorkArea> getWorkArea(String department, LocalDate asOfDate);
    public void saveOrUpdate(WorkArea workArea);
    public WorkArea getWorkArea(String tkWorkAreaId);
    public Long getNextWorkAreaKey();
    public List<WorkArea> getWorkAreas(String dept, String workArea, String description, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory);
    public int getWorkAreaCount(String dept, Long workArea);
}
