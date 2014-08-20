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
package org.kuali.kpme.core.paytype;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.CoreUnitTestCase;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;


public class PayTypeKeyWhiteListTest extends CoreUnitTestCase {
	
	private BusinessObjectService boService;
	
	@Before
    public void setUp() throws Exception {
           super.setUp();
           boService = KNSServiceLocator.getBusinessObjectService();
    }
	
	
	@Test
	public void testEffectiveKeySetAndListLoad() {
		PayTypeBo payTypeBo  = boService.findByPrimaryKey(PayTypeBo.class, Collections.singletonMap("hrPayTypeId", "1"));
		Assert.assertNotNull(payTypeBo);
		
		List<PayTypeKeyBo> effectiveKeyList = payTypeBo.getEffectiveKeyList();
		Assert.assertNotNull(effectiveKeyList);
		Assert.assertEquals(effectiveKeyList.size(), 2);
		
		Set<PayTypeKeyBo> effectiveKeySet = payTypeBo.getEffectiveKeySet(); 
		Assert.assertNotNull(effectiveKeySet);
		Assert.assertEquals(effectiveKeySet.size(), 2);		
		Set<String> idSet = new HashSet<String>();
		for(PayTypeKeyBo keyBo: effectiveKeySet) {
			idSet.add(keyBo.getId());
			Assert.assertTrue(effectiveKeyList.contains(keyBo));
			Assert.assertTrue( ( (keyBo.getId().equals("kpme_paytype_test_1000")) && (keyBo.getGroupKeyCode().equalsIgnoreCase("ISU-IA")) ) ||
							   ( (keyBo.getId().equals("kpme_paytype_test_1001")) && (keyBo.getGroupKeyCode().equalsIgnoreCase("UGA-GA")) )
							 );
		}		
		Assert.assertTrue(idSet.contains("kpme_paytype_test_1000"));
		Assert.assertTrue(idSet.contains("kpme_paytype_test_1001"));
		
		
		
		
		payTypeBo  = boService.findByPrimaryKey(PayTypeBo.class, Collections.singletonMap("hrPayTypeId", "5"));
		Assert.assertNotNull(payTypeBo);
		
		effectiveKeyList = payTypeBo.getEffectiveKeyList();
		Assert.assertNotNull(effectiveKeyList);
		Assert.assertEquals(2, effectiveKeyList.size());
		
		effectiveKeySet = payTypeBo.getEffectiveKeySet(); 
		Assert.assertNotNull(effectiveKeySet);
		Assert.assertEquals(effectiveKeySet.size(), 2);		
		idSet = new HashSet<String>();
		for(PayTypeKeyBo keyBo: effectiveKeySet) {
			Assert.assertEquals(keyBo.getOwnerId(), "5");
			idSet.add(keyBo.getId());
			Assert.assertTrue(effectiveKeyList.contains(keyBo));
			Assert.assertTrue( ( (keyBo.getId().equals("kpme_paytype_test_1002")) && (keyBo.getGroupKeyCode().equalsIgnoreCase("UGA-GA")) ) ||
							   ( (keyBo.getId().equals("kpme_paytype_test_1003")) && (keyBo.getGroupKeyCode().equalsIgnoreCase("IU-IN")) )
							 );
		}		
		Assert.assertTrue(idSet.contains("kpme_paytype_test_1002"));
		Assert.assertTrue(idSet.contains("kpme_paytype_test_1003"));
		
		
		
		
		payTypeBo  = boService.findByPrimaryKey(PayTypeBo.class, Collections.singletonMap("hrPayTypeId", "2"));
		Assert.assertNotNull(payTypeBo);		
		effectiveKeyList = payTypeBo.getEffectiveKeyList();
		Assert.assertTrue( (effectiveKeyList == null) || effectiveKeyList.isEmpty() );
		effectiveKeySet = payTypeBo.getEffectiveKeySet();
		Assert.assertTrue( (effectiveKeySet == null) || effectiveKeySet.isEmpty() );
		
		payTypeBo  = boService.findByPrimaryKey(PayTypeBo.class, Collections.singletonMap("hrPayTypeId", "4"));
		Assert.assertNotNull(payTypeBo);
		effectiveKeyList = payTypeBo.getEffectiveKeyList();
		Assert.assertTrue( (effectiveKeyList == null) || effectiveKeyList.isEmpty() );
		effectiveKeySet = payTypeBo.getEffectiveKeySet(); 
		Assert.assertTrue( (effectiveKeySet == null) || effectiveKeySet.isEmpty() );
		
	}
	
	
	
	
	@Test
	public void testGroupKeySetExtraction() {
		PayTypeBo pstnRptGrpBo  = boService.findByPrimaryKey(PayTypeBo.class, Collections.singletonMap("hrPayTypeId", "1"));
		Assert.assertFalse(pstnRptGrpBo.getGroupKeySet().isEmpty());
		Set<String> extractedGroupKeyCodeWithIdSet = new HashSet<String>();
		Set<HrGroupKeyBo> extractedGroupKeySet = pstnRptGrpBo.getGroupKeySet();
		for(HrGroupKeyBo extractedGroupKey : extractedGroupKeySet) {
			extractedGroupKeyCodeWithIdSet.add(extractedGroupKey.getGroupKeyCode() + "-" + extractedGroupKey.getId());
		}
		Assert.assertEquals(extractedGroupKeyCodeWithIdSet.size(), 2);
		Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("ISU-IA-8"));
		Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("UGA-GA-7"));
		
		
		pstnRptGrpBo  = boService.findByPrimaryKey(PayTypeBo.class, Collections.singletonMap("hrPayTypeId", "5"));
		Assert.assertFalse(pstnRptGrpBo.getGroupKeySet().isEmpty());
		extractedGroupKeyCodeWithIdSet = new HashSet<String>();
		extractedGroupKeySet = pstnRptGrpBo.getGroupKeySet();
		for(HrGroupKeyBo extractedGroupKey : extractedGroupKeySet) {
			extractedGroupKeyCodeWithIdSet.add(extractedGroupKey.getGroupKeyCode() + "-" + extractedGroupKey.getId());
		}
		Assert.assertEquals(extractedGroupKeyCodeWithIdSet.size(), 2);
		Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("IU-IN-5"));
		Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("UGA-GA-7"));
		
		
		pstnRptGrpBo  = boService.findByPrimaryKey(PayTypeBo.class, Collections.singletonMap("hrPayTypeId", "2"));
		extractedGroupKeySet = pstnRptGrpBo.getGroupKeySet();
		Assert.assertTrue( (extractedGroupKeySet == null) || extractedGroupKeySet.isEmpty() );		
		pstnRptGrpBo  = boService.findByPrimaryKey(PayTypeBo.class, Collections.singletonMap("hrPayTypeId", "4"));
		extractedGroupKeySet = pstnRptGrpBo.getGroupKeySet(); 
		Assert.assertTrue( (extractedGroupKeySet == null) || extractedGroupKeySet.isEmpty() );
	}
	
	
	
	
	@Test
	public void testGroupKeyCodeSetExtraction() {
		PayTypeBo pstnRptGrpBo  = boService.findByPrimaryKey(PayTypeBo.class, Collections.singletonMap("hrPayTypeId", "1"));
		Set<String> extractedGroupKeyCodeWithIdSet = pstnRptGrpBo.getGroupKeyCodeSet();
		Assert.assertEquals(extractedGroupKeyCodeWithIdSet.size(), 2);
		Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("ISU-IA"));
		Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("UGA-GA"));
		
		
		pstnRptGrpBo  = boService.findByPrimaryKey(PayTypeBo.class, Collections.singletonMap("hrPayTypeId", "5"));
		extractedGroupKeyCodeWithIdSet = pstnRptGrpBo.getGroupKeyCodeSet();
		Assert.assertEquals(2, extractedGroupKeyCodeWithIdSet.size());
		Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("IU-IN"));
		Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("UGA-GA"));
		
		
		pstnRptGrpBo  = boService.findByPrimaryKey(PayTypeBo.class, Collections.singletonMap("hrPayTypeId", "2"));
		extractedGroupKeyCodeWithIdSet = pstnRptGrpBo.getGroupKeyCodeSet();
		Assert.assertTrue( (extractedGroupKeyCodeWithIdSet == null) || extractedGroupKeyCodeWithIdSet.isEmpty() );		
		pstnRptGrpBo  = boService.findByPrimaryKey(PayTypeBo.class, Collections.singletonMap("hrPayTypeId", "4"));
		extractedGroupKeyCodeWithIdSet = pstnRptGrpBo.getGroupKeyCodeSet(); 
		Assert.assertTrue( (extractedGroupKeyCodeWithIdSet == null) || extractedGroupKeyCodeWithIdSet.isEmpty() );
	}
	
	
	
	
	@Test
	public void testKeyCodeSetOwnerAssignment() {
		PayTypeBo pstnRptGrpBo  = boService.findByPrimaryKey(PayTypeBo.class, Collections.singletonMap("hrPayTypeId", "1"));
		for(PayTypeKeyBo keyBo: pstnRptGrpBo.getEffectiveKeySet()) {
			Assert.assertEquals(keyBo.getOwnerId(), "1");
		}
		
		
		pstnRptGrpBo  = boService.findByPrimaryKey(PayTypeBo.class, Collections.singletonMap("hrPayTypeId", "5"));
		for(PayTypeKeyBo keyBo: pstnRptGrpBo.getEffectiveKeySet()) {
			Assert.assertEquals(keyBo.getOwnerId(), "5");
		}		
	}

}