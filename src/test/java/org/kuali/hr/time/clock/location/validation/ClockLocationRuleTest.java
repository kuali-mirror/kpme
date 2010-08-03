package org.kuali.hr.time.clock.location.validation;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;

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

	//clr.setDepartment("TEST");
	//clr.setWorkArea(BigDecimal.TEN);
	clr.setPrincipalId("12345");
	clr.setJobNumber(new BigInteger("0"));
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
	//clr.setDepartment("TEST");
	//clr.setWorkArea(BigDecimal.TEN);
	clr.setPrincipalId("12345");
	clr.setJobNumber(new BigInteger("0"));
	clr.setActive(true);
	clr.setTimestamp(ts_now);
	clr.setEffectiveDate(date_now);
	clr.setIpAddress(IP_ADDRESS_ONE);

	boService.save(clr);
	LOG.info("ID:"  + clr.getClockLocationRuleId() + " oID: "  + clr.getObjectId());

	clr = new ClockLocationRule();
	//clr.setDepartment("TEST");
	//clr.setWorkArea(BigDecimal.TEN);
	clr.setPrincipalId("12345");
	clr.setJobNumber(new BigInteger("0"));
	clr.setActive(false);
	clr.setTimestamp(ts_now);
	clr.setEffectiveDate(date_now);
	clr.setIpAddress(IP_ADDRESS_TWO);

	boService.save(clr);
	LOG.info("ID:"  + clr.getClockLocationRuleId() + " oID: "  + clr.getObjectId());
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
	matchMap.put("department", "TEST");
	found = boService.findMatching(ClockLocationRule.class, matchMap);
	assertEquals(2, found.size());
    }

    @SuppressWarnings("unchecked")
    public void clearBusinessObjects(Class clazz) {
	boService.deleteMatching(clazz, new HashMap());
    }
}
