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
package org.kuali.hr.core.task.service;

import java.util.List;
import java.util.Map;

import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.time.authorization.TkAuthorizedLookupableHelperBase;
import org.kuali.hr.tklm.time.util.TKUtils;
import org.kuali.rice.kns.web.ui.Field;
import org.kuali.rice.kns.web.ui.Row;
import org.kuali.rice.krad.bo.BusinessObject;

@SuppressWarnings("deprecation")
public class TaskLookupableHelper extends TkAuthorizedLookupableHelperBase {

	private static final long serialVersionUID = 7525044777337159013L;

	@Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        String task = fieldValues.get("task");
        String description = fieldValues.get("description");
        String workArea = fieldValues.get("workArea");
        String fromEffdt = TKUtils.getFromDateString(fieldValues.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));

        return HrServiceLocator.getTaskService().getTasks(task, description, workArea, TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt));
    }

    @Override
    // remove the default value for Task for lookup
    public List<Row> getRows() {
        List<Row> rowList = super.getRows();
        for (Row aRow : rowList) {
            List<Field> fields = aRow.getFields();
            for (Field aField : fields) {
                if (aField.getFieldLabel() != null && aField.getFieldLabel().equals("Task")) {
                    if (aField.getPropertyValue() != null && aField.getPropertyValue().equals("0")) {
                        aField.setPropertyValue("");
                    }
                }
            }
        }
        return rowList;
    }

    @Override
    public boolean shouldShowBusinessObject(BusinessObject bo) {
        return true;
    }
}
