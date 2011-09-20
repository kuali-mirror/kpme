package org.kuali.kfs.sys.businessobject.defaultvalue;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.lookup.valueFinder.ValueFinder;

public class NextUniquePositionNumberFinder implements ValueFinder {

    @Override
    public String getValue() {
        return TkServiceLocator.getPositionService().getNextUniquePositionNumber();
    }
}
