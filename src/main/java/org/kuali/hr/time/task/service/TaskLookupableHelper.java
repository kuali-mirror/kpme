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
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @SuppressWarnings({"unchecked"})
    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {

        String task = fieldValues.get("task");
        String description = fieldValues.get("description");
        String workArea = fieldValues.get("workArea");
        String workAreaDescription = fieldValues.get("workAreaDescription");
        String fromEffdt = fieldValues.get("rangeLowerBoundKeyPrefix_effectiveDate");
        String toEffdt = StringUtils.isNotBlank(fieldValues.get("effectiveDate")) ? fieldValues.get("effectiveDate").replace("<=", "") : "";

        List<Task> tasks = TkServiceLocator.getTaskService().getTasks(task, description, workArea, workAreaDescription,
                TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt));

        return tasks;
//		List finalList = new ArrayList<Task>();
//		String wad = "";
//		String wa = "";
//		List<String> taskFound = new ArrayList<String>();
//		if (fieldValues.containsKey("workAreaDescription") && !fieldValues.get("workAreaDescription").isEmpty()) {
//			wad = fieldValues.get("workAreaDescription");
//			fieldValues.remove("workAreaDescription");
//		}
//		if(fieldValues.containsKey("workArea") && !fieldValues.get("workArea").isEmpty()) {
//			wa = fieldValues.get("workArea");
//			fieldValues.remove("workArea");
//		}
//		List aList = super.getSearchResults(fieldValues);
//
//		for(int i = 0; i< aList.size(); i++) {
//			Task aTask = (Task) aList.get(i);
//			if(aTask.getTkWorkAreaId() != null) {
//				WorkArea aWorkArea = TkServiceLocator.getWorkAreaService().getWorkArea(aTask.getTkWorkAreaId());
//				if(aWorkArea != null) {
//					if( (StringUtils.isBlank(wa) || aWorkArea.getWorkArea().toString().equals(wa))
//							|| (StringUtils.isBlank(wad) || aWorkArea.getDescription().equals(wad))) {
//						if(!taskFound.contains(aWorkArea.getWorkArea() + "_"+aTask.getTask())   ){
//							finalList.add(aTask);
//							taskFound.add(aWorkArea.getWorkArea() +"_"+aTask.getTask());
//						}
//					}
//				}
//			}
//		}

//        return finalList;
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
