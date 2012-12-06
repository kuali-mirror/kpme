package org.kuali.hr.lm.leaverequest;


import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

public class LeaveRequestActionValuesFinder extends KeyValuesBase {
    private static final String ACTION_CODE_PREFIX = "action:";

    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> statuses = new ArrayList<KeyValue>();
        addCategory(statuses, LeaveRequestActionValue.NO_ACTION);
        addCategory(statuses, LeaveRequestActionValue.APPROVE);
        addCategory(statuses, LeaveRequestActionValue.DISAPPROVE);
        addCategory(statuses, LeaveRequestActionValue.DEFER);
        return statuses;
    }

    private void addCategory(List<KeyValue> statuses, LeaveRequestActionValue action) {
        statuses.add(new ConcreteKeyValue(action.getCode(), action.getLabel()));
    }
}
