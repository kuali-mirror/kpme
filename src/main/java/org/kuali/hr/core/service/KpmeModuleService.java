package org.kuali.hr.core.service;

import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.krad.service.impl.ModuleServiceBase;

import java.util.ArrayList;
import java.util.List;


public class KpmeModuleService extends ModuleServiceBase {

    @Override
    public List<List<String>> listAlternatePrimaryKeyFieldNames(Class businessObjectInterfaceClass) {
        if (WorkArea.class.isAssignableFrom(businessObjectInterfaceClass)) {
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
