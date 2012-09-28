/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.task.dao;


import org.kuali.hr.time.task.Task;

import java.sql.Date;
import java.util.List;

public interface TaskDao {
    public Task getTask(Long task, Date asOfDate);

    public void saveOrUpdate(Task task);
    public void saveOrUpdate(List<Task> tasks);
    public Task getMaxTask();

    List<Task> getTasks(Long task, String description, Long workArea, String workAreaDesc, Date fromEffdt, Date toEffdt);
    public int getTaskCount(Long task);
}
