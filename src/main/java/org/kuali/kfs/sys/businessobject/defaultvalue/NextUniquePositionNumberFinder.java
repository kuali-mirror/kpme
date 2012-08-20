package org.kuali.kfs.sys.businessobject.defaultvalue;

import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.valuefinder.ValueFinder;

public class NextUniquePositionNumberFinder implements ValueFinder {

    @Override
    public String getValue() {
        //making this work without the dumbness of what was done before. Still, this class shouldn't exist.  We don't need
        // a value finder for a sequence value....
        return KRADServiceLocator.getSequenceAccessorService().getNextAvailableSequenceNumber("hr_position_s", Position.class).toString();
        //return TkServiceLocator.getPositionService().getNextUniquePositionNumber();
    }
}
