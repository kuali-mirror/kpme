package org.kuali.hr.time.overtime.web;

import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.util.TkConstants;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * https://wiki.kuali.org/display/KPME/Test+Cases+-+Business+Logic+Daily+Overtime
 *
 * Implementations of tests from this page.
 */
@Ignore
public class DOTIntegrationConfluenceTests extends TkTestCase {

    public static final String USER_PRINCIPAL_ID = "admin";
    private Date JAN_AS_OF_DATE = new Date((new DateTime(2010, 1, 1, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());


    @Test
    /**
     * Daily OT - Shift hours met (in a single day) but Max Gap minutes exceeded (in a single gap)
     *
     * https://jira.kuali.org/browse/KPME-788
     *
     * Daily OT should not apply
     */
    public void testKPME788() throws Exception {
        Long jobNumber = 30L;
        Long workArea = 30L;
        Long task = 30L;
        createDailyOvertimeRule("REG", "OVT", "SD1", "BW", "TEST-DEPT", workArea, task, new BigDecimal(8), new BigDecimal("1.00"), null);
    }

    private void createDailyOvertimeRule(String fromEarnGroup, String earnCode, String location, String paytype, String dept, Long workArea, Long task, BigDecimal minHours, BigDecimal maxGap, String overtimePref) {
        DailyOvertimeRule rule = new DailyOvertimeRule();
        rule.setEffectiveDate(JAN_AS_OF_DATE);
        rule.setFromEarnGroup(fromEarnGroup);
        rule.setEarnCode(earnCode);
        rule.setLocation(location);
        rule.setPaytype(paytype);
        rule.setDept(dept);
        rule.setWorkArea(workArea);
        rule.setMaxGap(maxGap);
        rule.setMinHours(minHours);
        rule.setActive(true);
        TkServiceLocator.getDailyOvertimeRuleService().saveOrUpdate(rule);
    }
}
