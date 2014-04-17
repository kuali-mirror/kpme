/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.leave.workflow;

import org.kuali.kpme.tklm.leave.payout.LeavePayout;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.RouteHelper;
import org.kuali.rice.kew.engine.node.SplitNode;
import org.kuali.rice.kew.engine.node.SplitResult;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;

import java.util.ArrayList;
import java.util.List;

public class LeavePayoutSplitNode implements SplitNode {

    public SplitResult process(RouteContext context, RouteHelper helper) throws Exception {
        List<String> resultList = new ArrayList<String>();


        String documentNumber = context.getDocument().getDocumentId();
        MaintenanceDocument document = null;
        try {
            document = (MaintenanceDocument) KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(documentNumber);
        } catch (WorkflowException e) {
            e.printStackTrace();
        }

        LeavePayout leavePayout= null;
        if (document != null && document.getNewMaintainableObject() != null) {
            leavePayout = (LeavePayout) document.getNewMaintainableObject().getDataObject();
        }

        if (leavePayout != null) {
            if (leavePayout.getAccrualCategoryRule() == null) {
                resultList.add("maintenance");
            } else {
                resultList.add("calendar");
            }

        }
        return new SplitResult(resultList);
    }
}
