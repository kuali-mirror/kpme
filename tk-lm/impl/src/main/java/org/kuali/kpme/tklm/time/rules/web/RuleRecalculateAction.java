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
package org.kuali.kpme.tklm.time.rules.web;


import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.timesheet.TimesheetUtils;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.api.document.DocumentStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RuleRecalculateAction extends KPMEAction {

    public ActionForward recalculateDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RuleRecalculateActionForm ruleRecalculateActionForm = (RuleRecalculateActionForm) form;
        String documentId = ruleRecalculateActionForm.getRuleRecalculateDocumentId();
        if (StringUtils.isNotBlank(documentId)) {
            if (documentId != null) {
                TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
                if (tdh != null && (StringUtils.equals(tdh.getDocumentStatus(), DocumentStatus.SAVED.getCode())
                                    || StringUtils.equals(tdh.getDocumentStatus(), DocumentStatus.ENROUTE.getCode())
                                    || StringUtils.equals(tdh.getDocumentStatus(), DocumentStatus.EXCEPTION.getCode()))) {
                    //Get the time blocks
                    TimesheetDocument td = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
                    List<LeaveBlock> leaveBlocks = TimesheetUtils.getLeaveBlocksForTimesheet(td);
                    List<TimeBlock> initialBlocks = TimesheetUtils.getTimesheetTimeblocksForProcessing(td, true);
                    List<TimeBlock> referenceTimeBlocks = TimesheetUtils.getReferenceTimeBlocks(initialBlocks);

                    //reset time block
                    List<TimeBlock> tbs = TkServiceLocator.getTimesheetService().resetTimeBlock(initialBlocks, td.getAsOfDate());
                    TimesheetUtils.processTimeBlocksWithRuleChange(tbs, referenceTimeBlocks, leaveBlocks, td.getCalendarEntry(), td, HrContext.getPrincipalId());
                }
            }
        }

        return mapping.findForward("basic");
    }
}