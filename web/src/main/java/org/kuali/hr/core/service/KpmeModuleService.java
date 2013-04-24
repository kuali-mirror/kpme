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
package org.kuali.hr.core.service;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.core.leaveplan.LeavePlan;
import org.kuali.hr.core.task.Task;
import org.kuali.hr.core.workarea.WorkArea;
import org.kuali.rice.krad.service.impl.ModuleServiceBase;


public class KpmeModuleService extends ModuleServiceBase {

    @Override
    public List<List<String>> listAlternatePrimaryKeyFieldNames(Class businessObjectInterfaceClass) {
        if (LeavePlan.class.isAssignableFrom(businessObjectInterfaceClass)) {
            List<List<String>> retList = new ArrayList<List<String>>();
            List<String> keyList = new ArrayList<String>();
            keyList.add("leavePlan");
            keyList.add("effectiveDate");
            retList.add(keyList);
            return retList;
        } else if (Task.class.isAssignableFrom(businessObjectInterfaceClass)) {
            List<List<String>> retList = new ArrayList<List<String>>();
            List<String> keyList = new ArrayList<String>();
            keyList.add("task");
            keyList.add("effectiveDate");
            retList.add(keyList);
            return retList;
        } else if (WorkArea.class.isAssignableFrom(businessObjectInterfaceClass)) {
            List<List<String>> retList = new ArrayList<List<String>>();
            List<String> keyList = new ArrayList<String>();
            keyList.add("workArea");
            keyList.add("effectiveDate");
            retList.add(keyList);
            return retList;
        }
        return super.listAlternatePrimaryKeyFieldNames(businessObjectInterfaceClass);
    }
}
