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
package org.kuali.kpme.tklm.time.timesheet;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.core.api.assignment.service.AssignmentService;
import org.kuali.kpme.tklm.TKLMIntegrationTestCase;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.workflow.service.TimesheetDocumentHeaderService;

import java.util.List;


@IntegrationTest
public class TimesheetUtilsTest extends TKLMIntegrationTestCase {

    //AssignmentService assignmentService = null;
    AssignmentService assignmentService = null;
    TimesheetDocumentHeaderService tsDocService = null;
    TimesheetDocument td = null;


    @Before
    public void setUp() throws Exception {
        super.setUp();
        td = TkServiceLocator.getTimesheetService().getTimesheetDocument((String) "5001");
    }

    @Test
    public void testGetLeaveBlocksForTimesheet() throws Exception {
        List<LeaveBlock> lbs = TimesheetUtils.getLeaveBlocksForTimesheet(td);
        Assert.assertEquals(lbs.size(), 2);
    }

    @Test
    public void testGetTimesheetTimeblocksForProcessing() throws Exception {
        List<TimeBlock> ttb =  TimesheetUtils.getTimesheetTimeblocksForProcessing(td, (boolean)true);
        Assert.assertEquals(ttb.size(), 3);
    }

    @Test
    public void testProcessTimeBlocksWithRuleChange() throws Exception {
        try {
            List<LeaveBlock> lbs = TimesheetUtils.getLeaveBlocksForTimesheet(td);
            List<TimeBlock> rtb = TimesheetUtils.getReferenceTimeBlocks(td.getTimeBlocks());
            TimesheetUtils.processTimeBlocksWithRuleChange(td.getTimeBlocks(), rtb, lbs, td.getCalendarEntry(),td, (String)"admin");
        } catch (RuntimeException re) {
            Assert.assertTrue(re.getMessage().contains("processTimeBlocksWithRuleChange did not run successfully"));
        }
    }

    @Test
    public void testGetReferenceTimeBlocks() throws Exception {
        List<TimeBlock> rtb = TimesheetUtils.getReferenceTimeBlocks(td.getTimeBlocks());
        Assert.assertEquals(rtb.size(), 2);
    }
}