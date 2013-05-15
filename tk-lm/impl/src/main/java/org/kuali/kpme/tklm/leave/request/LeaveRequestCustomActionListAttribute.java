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
package org.kuali.kpme.tklm.leave.request;


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
