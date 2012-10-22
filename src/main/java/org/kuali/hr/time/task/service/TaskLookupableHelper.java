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
package org.kuali.hr.time.task.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.authorization.TkAuthorizedLookupableHelperBase;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.document.authorization.BusinessObjectRestrictions;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.web.struts.form.LookupForm;
import org.kuali.rice.kns.web.ui.Field;
import org.kuali.rice.kns.web.ui.Row;
import org.kuali.rice.krad.bo.BusinessObject;

public class TaskLookupableHelper extends TkAuthorizedLookupableHelperBase {

	private static final long serialVersionUID = 7525044777337159013L;

	@Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {

        String task = fieldValues.get("task");
        String description = fieldValues.get("description");
        String workArea = fieldValues.get("workArea");
        String fromEffdt = fieldValues.get("rangeLowerBoundKeyPrefix_effectiveDate");
        String toEffdt = StringUtils.isNotBlank(fieldValues.get("effectiveDate")) ? fieldValues.get("effectiveDate").replace("<=", "") : "";

        List<Task> tasks = TkServiceLocator.getTaskService().getTasks(task, description, workArea, TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt));

        return tasks;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public HtmlData getReturnUrl(BusinessObject businessObject,
                                 LookupForm lookupForm, List returnKeys,
                                 BusinessObjectRestrictions businessObjectRestrictions) {
        if (lookupForm.getFieldConversions().containsKey("effectiveDate")) {
            lookupForm.getFieldConversions().remove("effectiveDate");
        }
        if (returnKeys.contains("effectiveDate")) {
            returnKeys.remove("effectiveDate");
        }
        if (lookupForm.getFieldConversions().containsKey("workArea")) {
            lookupForm.getFieldConversions().remove("workArea");
        }
        if (returnKeys.contains("workArea")) {
            returnKeys.remove("workArea");
        }

        return super.getReturnUrl(businessObject, lookupForm, returnKeys,
                businessObjectRestrictions);
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
