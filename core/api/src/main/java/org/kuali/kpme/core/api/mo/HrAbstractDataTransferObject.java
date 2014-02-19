package org.kuali.kpme.core.api.mo;

import org.kuali.rice.core.api.mo.AbstractDataTransferObject;

import java.util.Map;


public abstract class HrAbstractDataTransferObject extends AbstractDataTransferObject {
    public abstract Map<String, Object> getBusinessKeyValuesMap();

    public boolean areAllBusinessKeyValuesAvailable() {
        boolean retVal = true;
        try {
            if(this.getBusinessKeyValuesMap().isEmpty()) {
                retVal = false;
            }
        }
        catch(Exception e) {
            retVal = false;
        }
        return retVal;
    }
}
