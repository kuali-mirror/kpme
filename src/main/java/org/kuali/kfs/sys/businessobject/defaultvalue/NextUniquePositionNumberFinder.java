package org.kuali.kfs.sys.businessobject.defaultvalue;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.krad.valuefinder.ValueFinder;

public class NextUniquePositionNumberFinder implements ValueFinder {

    @Override
    public String getValue() {
        return TkServiceLocator.getPositionService().getNextUniquePositionNumber();
    }
}
