package org.kuali.hr.time.timeblock;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkConstants;

public class TimeBlockHistoryTest extends TkTestCase {

    private static Date DEFAULT_EFFDT = new Date((new DateTime(2010, 1, 1, 0, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
    Timestamp beginTimestamp = new Timestamp(new DateTime(2010, 1, 1, 8, 0, 0, 0, DateTimeZone.forID("EST")).getMillis());
    Timestamp endTimestamp = new Timestamp(new DateTime(2010, 1, 1, 16, 0, 0, 0, DateTimeZone.forID("EST")).getMillis());

    @Test
    public void testTimeBlockHistory() throws Exception {
        TimesheetDocument td = TkTestUtils.populateBlankTimesheetDocument(DEFAULT_EFFDT);
        List<TimeBlock> tbs = TkServiceLocator.getTimeBlockService().buildTimeBlocks(td.getAssignments().get(0), "RGH", td, beginTimestamp, endTimestamp, new BigDecimal("8"), BigDecimal.ZERO, true);
        TkServiceLocator.getTimeBlockService().saveTimeBlocks(td.getTimeBlocks(), tbs);

        List<TimeBlockHistory> tbhs = new ArrayList<TimeBlockHistory>();
        for(TimeBlock tb : td.getTimeBlocks()) {
            tbhs.add(TkServiceLocator.getTimeBlockHistoryService().getTimeBlockHistoryByTkTimeBlockId(tb.getTkTimeBlockId()));
        }

        for(TimeBlockHistory tbh : tbhs) {
            assertEquals(TkConstants.ACTIONS.ADD_TIME_BLOCK, tbh.getActionHistory());
        }
    }
}
