package org.kuali.hr.lm.leaverequest;


import org.kuali.rice.kew.actionlist.CustomActionListAttribute;
import org.kuali.rice.kew.api.action.ActionItem;
import org.kuali.rice.kew.api.action.ActionRequestType;
import org.kuali.rice.kew.api.action.ActionSet;
import org.kuali.rice.kew.api.actionlist.DisplayParameters;

public class LeaveRequestCustomActionListAttribute implements CustomActionListAttribute {
    @Override
    public DisplayParameters getDocHandlerDisplayParameters(String principalId, ActionItem actionItem) throws Exception {
        return null;
    }

    @Override
    public ActionSet getLegalActions(String principalId, ActionItem actionItem) throws Exception {
        ActionSet.Builder as = ActionSet.Builder.create();
        if (actionItem.getActionRequestCd().equals(ActionRequestType.ACKNOWLEDGE.getCode())) {
            as.addAcknowledge();
        }
        return as.build();
    }
}
