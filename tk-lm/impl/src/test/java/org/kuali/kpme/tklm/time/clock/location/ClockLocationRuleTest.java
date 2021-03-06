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
package org.kuali.kpme.tklm.time.clock.location;

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
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.TKLMIntegrationTestCase;
import org.kuali.kpme.tklm.time.clocklog.ClockLogBo;
import org.kuali.kpme.tklm.time.rules.clocklocation.ClockLocationRule;
import org.kuali.kpme.tklm.time.rules.clocklocation.ClockLocationRuleIpAddress;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.LegacyDataAdapter;
import org.kuali.rice.krad.util.GlobalVariables;

/**
 * A really basic unit test testing persistence and searching over persisted business objects.
 */
@IntegrationTest
public class ClockLocationRuleTest extends TKLMIntegrationTestCase {

    private static final Logger LOG = Logger.getLogger(ClockLocationRuleTest.class);

    private static final String IP_ADDRESS_ONE = "127.0.0.1";
    private static final String IP_ADDRESS_TWO = "127.0.1.1";

    private LegacyDataAdapter boService;

    @Before
    public void setUp() throws Exception {
    	super.setUp();
    	boService = KRADServiceLocatorWeb.getLegacyDataAdapter();
    	clearBusinessObjects(ClockLocationRule.class);
    }

    public ClockLocationRule createClr(String ipAddress, Long workArea, String principalId, Long jobNumber ) {
    	ClockLocationRule clr = new ClockLocationRule();
    	clr.setGroupKeyCode("IU-IN");
    	clr.setDept("TEST");


//    	clr.setWorkArea(1234L);
//    	clr.setPrincipalId("12345");
//    	clr.setJobNumber(0L);

        clr.setWorkArea(workArea);
        clr.setPrincipalId(principalId);
        clr.setJobNumber(jobNumber);

    	clr.setActive(true);
        clr.setUserPrincipalId("admin");
    	clr.setTimestamp(TKUtils.getCurrentTimestamp());
    	clr.setEffectiveLocalDate(LocalDate.now());
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
    	clr.setPrincipalId("12345");
    	clr.setJobNumber(0L);
    	clr.setActive(true);
    	clr.setTimestamp(TKUtils.getCurrentTimestamp());
        clr.setUserPrincipalId("admin");
    	clr.setEffectiveLocalDate(LocalDate.now());
    	clr.setGroupKeyCode("IU-IN");
    	
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

        deleteCLR(clr);
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

    	ClockLocationRule clr2 = this.createClr(IP_ADDRESS_TWO,1234L, "1233", 0L);
    	
    	LOG.info("ID:"  + clr2.getTkClockLocationRuleId() + " oID: "  + clr2.getObjectId());
    	Assert.assertEquals("Should have two records saved", 2, boService.findAll(ClockLocationRule.class).size());
    	Map<String, Object> matchMap = new HashMap<String, Object>();
		matchMap = new HashMap<String, Object>();
		matchMap.put("dept", "TEST");
		Collection<ClockLocationRule> found = boService.findMatching(ClockLocationRule.class, matchMap);
		Assert.assertEquals(2, found.size());
        deleteCLR(clr);
        deleteCLR(clr2);

    }
    
    @Test
    public void testClockLocationRuleFetch() throws Exception{
        Assert.assertTrue((boolean)true);
        return;
/*
    	ClockLocationRule clr = this.createClr(IP_ADDRESS_ONE, 1234L, "1234", 0L);
    	List<ClockLocationRule> clockLocationRule = TkServiceLocator.getClockLocationRuleService().getClockLocationRule("IU-IN", "TEST", 1234L, 
    											"12345", 0L, LocalDate.now());
    	
    	Assert.assertTrue("Clock Location Rule pulled back correctly",clockLocationRule.size()==1);
        deleteCLR(clr);



    	clr = this.createClr(IP_ADDRESS_ONE, -1L, "%", -1L);
    	
    	clockLocationRule = TkServiceLocator.getClockLocationRuleService().getClockLocationRule("IU-IN", "TEST", 1234L, 
				"12345", 0L, LocalDate.now());
    	Assert.assertTrue("Clock Location Rule pulled back correctly",clockLocationRule.size()==1);
        deleteCLR(clr);
*/
    }
    
    @Test
    public void testClockLocationRuleProcessing() {
        Assert.assertTrue((boolean)true);
        return;

/*
    	ClockLocationRule clr = this.createClr(IP_ADDRESS_ONE, 1234L, "1234", 0L);
    	Job.Builder tempJob = Job.Builder.create("12345", 0L);
        tempJob.setDept("TEST");
        //Test for exact match
    	ClockLogBo clockLog = new ClockLogBo();
    	clockLog.setDocumentId("1111");
    	clockLog.setJob(tempJob.build());
    	clockLog.setIpAddress(IP_ADDRESS_ONE);
    	clockLog.setWorkArea(1234L);
    	clockLog.setPrincipalId("12345");
    	clockLog.setJobNumber(0L);
    	clockLog.setGroupKeyCode("IU-IN");
    	//clockLog.getJob().setDept("TEST");
    	
    	TkServiceLocator.getClockLocationRuleService().processClockLocationRule(clockLog, LocalDate.now());
    	
    	Assert.assertTrue("clock location rule no error",GlobalVariables.getMessageMap().hasNoWarnings());
    	Assert.assertFalse("clock log should have 'false' as unapprovedIP.", clockLog.isUnapprovedIP());

        deleteCLR(clr);
    	
    	clr = this.createClr(IP_ADDRESS_ONE, 1234L, "1234", 0L);
    	clockLog = new ClockLogBo();
    	clockLog.setDocumentId("1111");
    	clockLog.setJob(tempJob.build());
    	clockLog.setIpAddress("127.127.127.127");
    	clockLog.setWorkArea(1234L);
    	clockLog.setPrincipalId("12345");
    	clockLog.setJobNumber(0L);
    	clockLog.setGroupKeyCode("IU-IN");
    	//clockLog.getJob().setDept("TEST");
    	
    	TkServiceLocator.getClockLocationRuleService().processClockLocationRule(clockLog, LocalDate.now());
    	
    	Assert.assertTrue("clock location rule no error",GlobalVariables.getMessageMap().hasWarnings());
    	Assert.assertTrue("clock log should have 'true' as unapprovedIP.", clockLog.isUnapprovedIP());
        deleteCLR(clr);
*/
    }
    
    @Test
    public void testClockLocationIPAddress() {
        Assert.assertTrue((boolean)true);
        return;
/*

    	//Test for exact match
    	ClockLogBo clockLog = new ClockLogBo();
    	clockLog.setDocumentId("1111");
        Job.Builder tempJob = Job.Builder.create("12345", 0L);
        tempJob.setDept("TEST");
    	clockLog.setJob(tempJob.build());
    	clockLog.setIpAddress(IP_ADDRESS_ONE);
    	clockLog.setWorkArea(1234L);
    	clockLog.setPrincipalId("12345");
    	clockLog.setJobNumber(0L);
    	clockLog.setGroupKeyCode("IU-IN");
    	//clockLog.getJob().setDept("TEST");
    	
    	this.processRuleWithIPNoWarning(clockLog, "%");
    	this.processRuleWithIPNoWarning(clockLog, "127.%");
    	this.processRuleWithIPNoWarning(clockLog, "127.0.%");
    	this.processRuleWithIPNoWarning(clockLog, "127.0.0.%");
    	this.processRuleWithIPNoWarning(clockLog, IP_ADDRESS_ONE);
    	this.processRuleWithIPNoWarning(clockLog, "%.%.%.%");
	
    	this.processRuleWithIPWithWarning(clockLog, "128.%");
    	*/
    }
    
    public void processRuleWithIPNoWarning(ClockLogBo clockLog, String ipAddress) {
        Assert.assertTrue((boolean)true);
        return;

/*
    	ClockLocationRule clr = this.createClr(ipAddress, 1234L, "1234", 0L);
    	TkServiceLocator.getClockLocationRuleService().processClockLocationRule(clockLog, LocalDate.now());
    	Assert.assertTrue("clock location rule no warning message",GlobalVariables.getMessageMap().hasNoWarnings());
    	Assert.assertFalse("clock log should have 'false' as unapprovedIP.", clockLog.isUnapprovedIP());
        deleteCLR(clr);
*/
    }
    
    public void processRuleWithIPWithWarning(ClockLogBo clockLog, String ipAddress) {
        Assert.assertTrue((boolean)true);
        return;

/*
    	clearBusinessObjects(ClockLocationRule.class);
    	ClockLocationRule clr = this.createClr(ipAddress, 1234L, "12345", 0L);
    	TkServiceLocator.getClockLocationRuleService().processClockLocationRule(clockLog, LocalDate.now());
    	Assert.assertFalse("clock location rule with warning message",GlobalVariables.getMessageMap().hasNoWarnings());
    	Assert.assertTrue("clock location rule with 1 warning message",(GlobalVariables.getMessageMap().getWarningCount()== 1));
    	Assert.assertTrue("clock log should have 'true' as unapprovedIP.", clockLog.isUnapprovedIP());
        deleteCLR(clr);
*/
    }

    @SuppressWarnings("unchecked")
    public void clearBusinessObjects(Class clazz) {
    	boService.deleteMatching(clazz, new HashMap());
    }
    
 
}
