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
package org.kuali.hr.time.clock.location.validation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.rules.clocklocation.ClockLocationRule;
import org.kuali.kpme.tklm.time.rules.clocklocation.ClockLocationRuleIpAddress;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

/**
 * A really basic unit test testing persistence and searching over persisted business objects.
 */
public class ClockLocationRuleTest extends KPMETestCase {

    private static final Logger LOG = Logger.getLogger(ClockLocationRuleTest.class);

    private static final String IP_ADDRESS_ONE = "127.0.0.1";
    private static final String IP_ADDRESS_TWO = "127.0.1.1";

    private BusinessObjectService boService;

    @Before
    public void setUp() throws Exception {
    	super.setUp();
    	boService = KRADServiceLocator.getBusinessObjectService();
    	clearBusinessObjects(ClockLocationRule.class);
    }

    public ClockLocationRule createClr(String ipAddress, Long workArea, String principalId, Long jobNumber ) {
    	Timestamp ts_now = new Timestamp(System.currentTimeMillis());
    	LocalDate date_now = LocalDate.now();
    	ClockLocationRule clr = new ClockLocationRule();
    	clr.setDept("TEST");
    	clr.setWorkArea(1234L);
    	clr.setPrincipalId("12345");
    	clr.setJobNumber(0L);
    	clr.setActive(true);
    	clr.setTimestamp(ts_now);
    	clr.setEffectiveLocalDate(date_now);
    	ClockLocationRuleIpAddress anIp = new ClockLocationRuleIpAddress();
    	anIp.setIpAddress(ipAddress);
    	List<ClockLocationRuleIpAddress> aList = new ArrayList<ClockLocationRuleIpAddress>();
    	aList.add(anIp);
    	clr.setIpAddresses(aList);

    	boService.save(clr);
    	for(ClockLocationRuleIpAddress ip : clr.getIpAddresses()) {
    		ip.setTkClockLocationRuleId(clr.getTkClockLocationRuleId());
    		boService.save(ip);
    	}
    	return clr;
    }
    
    public void deleteCLR(ClockLocationRule clr) {
    	boService.delete(clr);
    	for(ClockLocationRuleIpAddress ip : clr.getIpAddresses()) {
    		boService.delete(ip);
    	}
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSave() throws Exception {
    	ClockLocationRule clr = new ClockLocationRule();
    	Timestamp ts_now = new Timestamp(System.currentTimeMillis());
    	LocalDate date_now = LocalDate.now();
    	clr.setPrincipalId("12345");
    	clr.setJobNumber(0L);
    	clr.setActive(true);
    	clr.setTimestamp(ts_now);
    	clr.setEffectiveLocalDate(date_now);
    	ClockLocationRuleIpAddress anIp = new ClockLocationRuleIpAddress();
    	anIp.setIpAddress(IP_ADDRESS_ONE);
    	List<ClockLocationRuleIpAddress> aList = new ArrayList<ClockLocationRuleIpAddress>();
    	aList.add(anIp);
    	clr.setIpAddresses(aList);
    	    	
    	Assert.assertNull("Should not have ObjectId before persist.", clr.getObjectId());
    	boService.save(clr);
    	Assert.assertNotNull("Should have ObjectId after persist.", clr.getObjectId());
    	
    	for(ClockLocationRuleIpAddress ip : clr.getIpAddresses()) {
    		ip.setTkClockLocationRuleId(clr.getTkClockLocationRuleId());
    		boService.save(ip);
    	}

    	Collection<ClockLocationRule> collection = boService.findAll(ClockLocationRule.class);
    	Assert.assertEquals("One entry should be in list.", 1, collection.size());
    	
    	for (ClockLocationRule crule : collection) {
    		// There is only one
    		TkServiceLocator.getClockLocationRuleService().populateIPAddressesForCLR(crule);
    		Assert.assertEquals(crule.getIpAddresses().size(), 1);
    		Assert.assertEquals(crule.getIpAddresses().get(0).getIpAddress(), IP_ADDRESS_ONE);
    	}
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFindMatching() throws Exception {    	
    	ClockLocationRule clr = this.createClr(IP_ADDRESS_ONE, 1234L, "1234", 0L);
    	
    	LOG.info("ID:"  + clr.getTkClockLocationRuleId() + " oID: "  + clr.getObjectId());
    	for(ClockLocationRuleIpAddress ip : clr.getIpAddresses()) {
    		ip.setTkClockLocationRuleId(clr.getTkClockLocationRuleId());
    		boService.save(ip);
    	}
    	clr = this.createClr(IP_ADDRESS_TWO,1234L, "1234", 0L);
    	
    	LOG.info("ID:"  + clr.getTkClockLocationRuleId() + " oID: "  + clr.getObjectId());
    	Assert.assertEquals("Should have two records saved", 2, boService.findAll(ClockLocationRule.class).size());
    	Map<String, Object> matchMap = new HashMap<String, Object>();
		matchMap = new HashMap<String, Object>();
		matchMap.put("dept", "TEST");
		Collection<ClockLocationRule> found = boService.findMatching(ClockLocationRule.class, matchMap);
		Assert.assertEquals(2, found.size());

    }
    
    @Test
    public void testClockLocationRuleFetch() throws Exception{
    	ClockLocationRule clr = this.createClr(IP_ADDRESS_ONE, 1234L, "1234", 0L);
    	List<ClockLocationRule> clockLocationRule = TkServiceLocator.getClockLocationRuleService().getClockLocationRule("TEST", 1234L, 
    											"12345", 0L, LocalDate.now());
    	
    	Assert.assertTrue("Clock Location Rule pulled back correctly",clockLocationRule.size()==1);
    	boService.delete(clr);
    	clr = this.createClr(IP_ADDRESS_ONE, -1L, "%", -1L);
    	
    	clockLocationRule = TkServiceLocator.getClockLocationRuleService().getClockLocationRule("TEST", 1234L, 
				"12345", 0L, LocalDate.now());
    	Assert.assertTrue("Clock Location Rule pulled back correctly",clockLocationRule.size()==1);
    }
    
    @Test
    public void testClockLocationRuleProcessing() {
    	ClockLocationRule clr = this.createClr(IP_ADDRESS_ONE, 1234L, "1234", 0L);
    	
    	//Test for exact match
    	ClockLog clockLog = new ClockLog();
    	clockLog.setJob(new Job());
    	clockLog.setIpAddress(IP_ADDRESS_ONE);
    	clockLog.setWorkArea(1234L);
    	clockLog.setPrincipalId("12345");
    	clockLog.setJobNumber(0L);
    	clockLog.getJob().setDept("TEST");
    	
    	TkServiceLocator.getClockLocationRuleService().processClockLocationRule(clockLog, LocalDate.now());
    	
    	Assert.assertTrue("clock location rule no error",GlobalVariables.getMessageMap().hasNoWarnings());
    	Assert.assertFalse("clock log should have 'false' as unapprovedIP.", clockLog.getUnapprovedIP());

    	boService.delete(clr);
    	
    	clr = this.createClr(IP_ADDRESS_ONE, 1234L, "1234", 0L);
    	
    	clockLog = new ClockLog();
    	clockLog.setJob(new Job());
    	clockLog.setIpAddress("127.127.127.127");
    	clockLog.setWorkArea(1234L);
    	clockLog.setPrincipalId("12345");
    	clockLog.setJobNumber(0L);
    	clockLog.getJob().setDept("TEST");
    	
    	TkServiceLocator.getClockLocationRuleService().processClockLocationRule(clockLog, LocalDate.now());
    	
    	Assert.assertTrue("clock location rule no error",GlobalVariables.getMessageMap().hasWarnings());
    	Assert.assertTrue("clock log should have 'true' as unapprovedIP.", clockLog.getUnapprovedIP());

    }
    
    @Test
    public void testClockLocationIPAddress() {
    	//Test for exact match
    	ClockLog clockLog = new ClockLog();
    	clockLog.setJob(new Job());
    	clockLog.setIpAddress(IP_ADDRESS_ONE);
    	clockLog.setWorkArea(1234L);
    	clockLog.setPrincipalId("12345");
    	clockLog.setJobNumber(0L);
    	clockLog.getJob().setDept("TEST");
    	
    	this.processRuleWithIPNoWarning(clockLog, "%");
    	this.processRuleWithIPNoWarning(clockLog, "127.%");
    	this.processRuleWithIPNoWarning(clockLog, "127.0.%");
    	this.processRuleWithIPNoWarning(clockLog, "127.0.0.%");
    	this.processRuleWithIPNoWarning(clockLog, IP_ADDRESS_ONE);
    	this.processRuleWithIPNoWarning(clockLog, "%.%.%.%");
	
    	this.processRuleWithIPWithWarning(clockLog, "128.%");
    }
    
    public void processRuleWithIPNoWarning(ClockLog clockLog, String ipAddress) {
    	ClockLocationRule clr = this.createClr(ipAddress, 1234L, "1234", 0L);
    	TkServiceLocator.getClockLocationRuleService().processClockLocationRule(clockLog, LocalDate.now());
    	Assert.assertTrue("clock location rule no warning message",GlobalVariables.getMessageMap().hasNoWarnings());
    	Assert.assertFalse("clock log should have 'false' as unapprovedIP.", clockLog.getUnapprovedIP());
    }
    
    public void processRuleWithIPWithWarning(ClockLog clockLog, String ipAddress) {
    	clearBusinessObjects(ClockLocationRule.class);
    	ClockLocationRule clr = this.createClr(ipAddress, 1234L, "12345", 0L);
    	TkServiceLocator.getClockLocationRuleService().processClockLocationRule(clockLog, LocalDate.now());
    	Assert.assertFalse("clock location rule with warning message",GlobalVariables.getMessageMap().hasNoWarnings());
    	Assert.assertTrue("clock location rule with 1 warning message",(GlobalVariables.getMessageMap().getWarningCount()== 1));
    	Assert.assertTrue("clock log should have 'true' as unapprovedIP.", clockLog.getUnapprovedIP());
    }

    @SuppressWarnings("unchecked")
    public void clearBusinessObjects(Class clazz) {
    	boService.deleteMatching(clazz, new HashMap());
    }
    
 
}
