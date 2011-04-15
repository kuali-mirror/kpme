package org.kuali.hr.time.clock.location.validation;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;

/**
 * A really basic unit test testing persistence and searching over persisted business objects.
 */
public class ClockLocationRuleTest extends TkTestCase {

    private static final Logger LOG = Logger.getLogger(ClockLocationRuleTest.class);

    private static final String IP_ADDRESS_ONE = "127.0.0.1";
    private static final String IP_ADDRESS_TWO = "127.0.1.1";

    private BusinessObjectService boService;

    @Before
    public void setUp() throws Exception {
    	super.setUp();
    	boService = KNSServiceLocator.getBusinessObjectService();
    	clearBusinessObjects(ClockLocationRule.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSave() throws Exception {
    	ClockLocationRule clr = new ClockLocationRule();
    	Timestamp ts_now = new Timestamp(System.currentTimeMillis());
    	Date date_now = new Date(System.currentTimeMillis());
    	clr.setPrincipalId("12345");
    	clr.setJobNumber(0L);
    	clr.setActive(true);
    	clr.setTimestamp(ts_now);
    	clr.setEffectiveDate(date_now);
    	clr.setIpAddress("127.0.0.1");

    	assertNull("Should not have ObjectId before persist.", clr.getObjectId());
    	boService.save(clr);
    	assertNotNull("Should have ObjectId after persist.", clr.getObjectId());

    	Collection<ClockLocationRule> collection = boService.findAll(ClockLocationRule.class);
    	assertEquals("One entry should be in list.", 1, collection.size());

    	for (ClockLocationRule crule : collection) {
    		// There is only one
    		assertEquals(crule.getIpAddress(), "127.0.0.1");
    	}
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFindMatching() throws Exception {
    	Timestamp ts_now = new Timestamp(System.currentTimeMillis());
    	Date date_now = new Date(System.currentTimeMillis());

    	ClockLocationRule clr = new ClockLocationRule();
    	clr.setDept("TEST");
    	clr.setPrincipalId("12345");
    	clr.setJobNumber(0L);
    	clr.setActive(true);
    	clr.setTimestamp(ts_now);
    	clr.setEffectiveDate(date_now);
    	clr.setIpAddress(IP_ADDRESS_ONE);

    	boService.save(clr);
    	LOG.info("ID:"  + clr.getTkClockLocationRuleId() + " oID: "  + clr.getObjectId());

    	clr = new ClockLocationRule();
    	clr.setDept("TEST");
    	clr.setPrincipalId("12345");
    	clr.setJobNumber(0L);
    	clr.setActive(false);
    	clr.setTimestamp(ts_now);
    	clr.setEffectiveDate(date_now);
    	clr.setIpAddress(IP_ADDRESS_TWO);

    	boService.save(clr);
    	LOG.info("ID:"  + clr.getTkClockLocationRuleId() + " oID: "  + clr.getObjectId());
    	assertEquals("Should have two records saved", 2, boService.findAll(ClockLocationRule.class).size());

    	Map<String, Object> matchMap = new HashMap<String, Object>();
    	matchMap.put("ipAddress", IP_ADDRESS_TWO);
    	Collection<ClockLocationRule> found = boService.findMatching(ClockLocationRule.class, matchMap);
    	assertEquals(found.size(), 1);
    	for (ClockLocationRule crule : found) {
    		assertEquals(IP_ADDRESS_TWO, crule.getIpAddress());
    		assertEquals(false, crule.isActive());
    	}

    	matchMap = new HashMap<String, Object>();
    	matchMap.put("ipAddress", IP_ADDRESS_ONE);
    	found = boService.findMatching(ClockLocationRule.class, matchMap);
    	assertEquals(1, found.size());
    	for (ClockLocationRule crule : found) {
    		assertEquals(IP_ADDRESS_ONE, crule.getIpAddress());
    		assertEquals(true, crule.isActive());
    	}

	matchMap = new HashMap<String, Object>();
	matchMap.put("dept", "TEST");
	found = boService.findMatching(ClockLocationRule.class, matchMap);
	assertEquals(2, found.size());

    }
    
    @Test
    public void testClockLocationRuleFetch() throws Exception{
    	Timestamp ts_now = new Timestamp(System.currentTimeMillis());
    	Date date_now = new Date(System.currentTimeMillis());
    	ClockLocationRule clr = new ClockLocationRule();
    	clr.setDept("TEST");
    	clr.setWorkArea(1234L);
    	clr.setPrincipalId("12345");
    	clr.setJobNumber(0L);
    	clr.setActive(true);
    	clr.setTimestamp(ts_now);
    	clr.setEffectiveDate(date_now);
    	clr.setIpAddress(IP_ADDRESS_ONE);

    	boService.save(clr);
    	
    	List<ClockLocationRule> clockLocationRule = TkServiceLocator.getClockLocationRuleService().getClockLocationRule("TEST", 1234L, 
    											"12345", 0L, new Date(System.currentTimeMillis()));
    	
    	assertTrue("Clock Location Rule pulled back correctly",clockLocationRule.size()==1);
    	boService.delete(clr);
    	clr = new ClockLocationRule();
    	clr.setDept("TEST");
    	clr.setWorkArea(-1L);
    	clr.setPrincipalId("%");
    	clr.setJobNumber(-1L);
    	clr.setActive(true);
    	clr.setTimestamp(ts_now);
    	clr.setEffectiveDate(date_now);
    	clr.setIpAddress(IP_ADDRESS_ONE);
    	
    	boService.save(clr);
    	clockLocationRule = TkServiceLocator.getClockLocationRuleService().getClockLocationRule("TEST", 1234L, 
				"12345", 0L, new Date(System.currentTimeMillis()));
    	assertTrue("Clock Location Rule pulled back correctly",clockLocationRule.size()==1);
    }
    
    @Test
    public void testClockLocationRuleProcessing() {
    	Timestamp ts_now = new Timestamp(System.currentTimeMillis());
    	Date date_now = new Date(System.currentTimeMillis());
    	ClockLocationRule clr = new ClockLocationRule();
    	clr.setDept("TEST");
    	clr.setWorkArea(1234L);
    	clr.setPrincipalId("12345");
    	clr.setJobNumber(0L);
    	clr.setActive(true);
    	clr.setTimestamp(ts_now);
    	clr.setEffectiveDate(date_now);
    	clr.setIpAddress(IP_ADDRESS_ONE);

    	boService.save(clr);
    	//Test for exact match
    	ClockLog clockLog = new ClockLog();
    	clockLog.setJob(new Job());
    	clockLog.setIpAddress(IP_ADDRESS_ONE);
    	clockLog.setWorkArea(1234L);
    	clockLog.setPrincipalId("12345");
    	clockLog.setJobNumber(0L);
    	clockLog.getJob().setDept("TEST");
    	
    	TkServiceLocator.getClockLocationRuleService().processClockLocationRule(clockLog, new Date(System.currentTimeMillis()));
    	
    	assertTrue("clock location rule no error",GlobalVariables.getMessageMap().hasNoWarnings());
    	
    	boService.delete(clr);
    	
    	clr = new ClockLocationRule();
    	clr.setDept("TEST");
    	clr.setWorkArea(1234L);
    	clr.setPrincipalId("12345");
    	clr.setJobNumber(0L);
    	clr.setActive(true);
    	clr.setTimestamp(ts_now);
    	clr.setEffectiveDate(date_now);
    	clr.setIpAddress(IP_ADDRESS_ONE);
    	
    	boService.save(clr);
    	clockLog = new ClockLog();
    	clockLog.setJob(new Job());
    	clockLog.setIpAddress("127.127.127.127");
    	clockLog.setWorkArea(1234L);
    	clockLog.setPrincipalId("12345");
    	clockLog.setJobNumber(0L);
    	clockLog.getJob().setDept("TEST");
    	
    	TkServiceLocator.getClockLocationRuleService().processClockLocationRule(clockLog, new Date(System.currentTimeMillis()));
    	
    	assertTrue("clock location rule no error",GlobalVariables.getMessageMap().hasWarnings());

    }
    
    @Test
    public void testClockLocationIPAddress() {
    	Timestamp ts_now = new Timestamp(System.currentTimeMillis());
    	Date date_now = new Date(System.currentTimeMillis());
    	ClockLocationRule clr = new ClockLocationRule();
    	clr.setDept("TEST");
    	clr.setWorkArea(1234L);
    	clr.setPrincipalId("12345");
    	clr.setJobNumber(0L);
    	clr.setActive(true);
    	clr.setTimestamp(ts_now);
    	clr.setEffectiveDate(date_now);
    	clr.setIpAddress("%");   // should match every IP

    	boService.save(clr);
    	//Test for exact match
    	ClockLog clockLog = new ClockLog();
    	clockLog.setJob(new Job());
    	clockLog.setIpAddress(IP_ADDRESS_ONE);
    	clockLog.setWorkArea(1234L);
    	clockLog.setPrincipalId("12345");
    	clockLog.setJobNumber(0L);
    	clockLog.getJob().setDept("TEST");
    	
    	this.processRuleWithIPNoWarning(clr, clockLog, "%");
    	this.processRuleWithIPNoWarning(clr, clockLog, "127.%");
    	this.processRuleWithIPNoWarning(clr, clockLog, "127.0.%");
    	this.processRuleWithIPNoWarning(clr, clockLog, "127.0.0.%");
    	this.processRuleWithIPNoWarning(clr, clockLog, IP_ADDRESS_ONE);
    	this.processRuleWithIPNoWarning(clr, clockLog, "%.%.%.%");
    	
    	
    	this.processRuleWithIPWithWarning(clr, clockLog, "128.%");
    	boService.delete(clr);
    }
    
    public void processRuleWithIPNoWarning(ClockLocationRule clr, ClockLog clockLog, String ipAddress) {
    	clr.setIpAddress(ipAddress);
    	boService.save(clr);
    	TkServiceLocator.getClockLocationRuleService().processClockLocationRule(clockLog, new Date(System.currentTimeMillis()));
    	assertTrue("clock location rule no warning message",GlobalVariables.getMessageMap().hasNoWarnings());
    }
    
    public void processRuleWithIPWithWarning(ClockLocationRule clr, ClockLog clockLog, String ipAddress) {
    	clr.setIpAddress(ipAddress);
    	boService.save(clr);
    	TkServiceLocator.getClockLocationRuleService().processClockLocationRule(clockLog, new Date(System.currentTimeMillis()));
    	assertFalse("clock location rule with warning message",GlobalVariables.getMessageMap().hasNoWarnings());
    	assertTrue("clock location rule with 1 warning message",(GlobalVariables.getMessageMap().getWarningCount()== 1));
    }

    @SuppressWarnings("unchecked")
    public void clearBusinessObjects(Class clazz) {
	boService.deleteMatching(clazz, new HashMap());
    }
}
