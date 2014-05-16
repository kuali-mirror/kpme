package org.kuali.kpme.pm.report.group;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.pm.PMIntegrationTestCase;
import org.kuali.kpme.pm.positionreportgroup.PositionReportGroupBo;
import org.kuali.kpme.pm.positionreportgroup.PositionReportGroupKeyBo;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;


public class PositionReportGroupKeyWhiteListTest extends PMIntegrationTestCase {
	
	private BusinessObjectService boService;
	
	@Before
    public void setUp() throws Exception {
           super.setUp();
           boService = KRADServiceLocator.getBusinessObjectService();
    }
	
	
	@Test
	public void testEffectiveKeySetLoad() {
		PositionReportGroupBo pstnRptGrpBo  = boService.findByPrimaryKey(PositionReportGroupBo.class, Collections.singletonMap("pmPositionReportGroupId", "1"));
		Assert.assertNotNull(pstnRptGrpBo);
		Set<PositionReportGroupKeyBo> effectiveKeySet = pstnRptGrpBo.getEffectiveKeySet(); 
		Assert.assertNotNull(effectiveKeySet);
		Assert.assertEquals(effectiveKeySet.size(), 2);		
		Set<String> idSet = new HashSet<String>();
		for(PositionReportGroupKeyBo keyBo: effectiveKeySet) {
			idSet.add(keyBo.getId());
			Assert.assertTrue( ( (keyBo.getId().equals("kpme_neeraj_test_1000")) && (keyBo.getGroupKeyCode().equalsIgnoreCase("ISU-IA")) ) ||
							   ( (keyBo.getId().equals("kpme_neeraj_test_1001")) && (keyBo.getGroupKeyCode().equalsIgnoreCase("UGA-GA")) )
							 );
		}		
		Assert.assertTrue(idSet.contains("kpme_neeraj_test_1000"));
		Assert.assertTrue(idSet.contains("kpme_neeraj_test_1001"));
		
		
		
		
		pstnRptGrpBo  = boService.findByPrimaryKey(PositionReportGroupBo.class, Collections.singletonMap("pmPositionReportGroupId", "4"));
		Assert.assertNotNull(pstnRptGrpBo);
		effectiveKeySet = pstnRptGrpBo.getEffectiveKeySet(); 
		Assert.assertNotNull(effectiveKeySet);
		Assert.assertEquals(effectiveKeySet.size(), 2);		
		idSet = new HashSet<String>();
		for(PositionReportGroupKeyBo keyBo: effectiveKeySet) {
			Assert.assertEquals(keyBo.getOwnerId(), "4");
			idSet.add(keyBo.getId());
			Assert.assertTrue( ( (keyBo.getId().equals("kpme_neeraj_test_1002")) && (keyBo.getGroupKeyCode().equalsIgnoreCase("UGA-GA")) ) ||
							   ( (keyBo.getId().equals("kpme_neeraj_test_1003")) && (keyBo.getGroupKeyCode().equalsIgnoreCase("IU-IN")) )
							 );
		}		
		Assert.assertTrue(idSet.contains("kpme_neeraj_test_1002"));
		Assert.assertTrue(idSet.contains("kpme_neeraj_test_1003"));
		
		
		
		
		pstnRptGrpBo  = boService.findByPrimaryKey(PositionReportGroupBo.class, Collections.singletonMap("pmPositionReportGroupId", "2"));
		Assert.assertNotNull(pstnRptGrpBo);
		effectiveKeySet = pstnRptGrpBo.getEffectiveKeySet(); 
		Assert.assertTrue( (effectiveKeySet == null) || effectiveKeySet.isEmpty() );		
		pstnRptGrpBo  = boService.findByPrimaryKey(PositionReportGroupBo.class, Collections.singletonMap("pmPositionReportGroupId", "3"));
		Assert.assertNotNull(pstnRptGrpBo);
		effectiveKeySet = pstnRptGrpBo.getEffectiveKeySet(); 
		Assert.assertTrue( (effectiveKeySet == null) || effectiveKeySet.isEmpty() );
		
		//
		//Assert.notEmpty(pstnRptGrpBo.getGroupKeyCodeSet());
	}
	
	
	
	
	@Test
	public void testGroupKeySetExtraction() {
		PositionReportGroupBo pstnRptGrpBo  = boService.findByPrimaryKey(PositionReportGroupBo.class, Collections.singletonMap("pmPositionReportGroupId", "1"));
		Assert.assertFalse(pstnRptGrpBo.getGroupKeySet().isEmpty());
		Set<String> extractedGroupKeyCodeWithIdSet = new HashSet<String>();
		Set<HrGroupKeyBo> extractedGroupKeySet = pstnRptGrpBo.getGroupKeySet();
		for(HrGroupKeyBo extractedGroupKey : extractedGroupKeySet) {
			extractedGroupKeyCodeWithIdSet.add(extractedGroupKey.getGroupKeyCode() + "-" + extractedGroupKey.getId());
		}
		Assert.assertEquals(extractedGroupKeyCodeWithIdSet.size(), 2);
		Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("ISU-IA-8"));
		Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("UGA-GA-7"));
		
		
		pstnRptGrpBo  = boService.findByPrimaryKey(PositionReportGroupBo.class, Collections.singletonMap("pmPositionReportGroupId", "4"));
		Assert.assertFalse(pstnRptGrpBo.getGroupKeySet().isEmpty());
		extractedGroupKeyCodeWithIdSet = new HashSet<String>();
		extractedGroupKeySet = pstnRptGrpBo.getGroupKeySet();
		for(HrGroupKeyBo extractedGroupKey : extractedGroupKeySet) {
			extractedGroupKeyCodeWithIdSet.add(extractedGroupKey.getGroupKeyCode() + "-" + extractedGroupKey.getId());
		}
		Assert.assertEquals(extractedGroupKeyCodeWithIdSet.size(), 2);
		Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("IU-IN-5"));
		Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("UGA-GA-7"));
		
		
		pstnRptGrpBo  = boService.findByPrimaryKey(PositionReportGroupBo.class, Collections.singletonMap("pmPositionReportGroupId", "2"));
		extractedGroupKeySet = pstnRptGrpBo.getGroupKeySet();
		Assert.assertTrue( (extractedGroupKeySet == null) || extractedGroupKeySet.isEmpty() );		
		pstnRptGrpBo  = boService.findByPrimaryKey(PositionReportGroupBo.class, Collections.singletonMap("pmPositionReportGroupId", "3"));
		extractedGroupKeySet = pstnRptGrpBo.getGroupKeySet(); 
		Assert.assertTrue( (extractedGroupKeySet == null) || extractedGroupKeySet.isEmpty() );
	}
	
	
	
	
	@Test
	public void testGroupKeyCodeSetExtraction() {
		PositionReportGroupBo pstnRptGrpBo  = boService.findByPrimaryKey(PositionReportGroupBo.class, Collections.singletonMap("pmPositionReportGroupId", "1"));
		Set<String> extractedGroupKeyCodeWithIdSet = pstnRptGrpBo.getGroupKeyCodeSet();
		Assert.assertEquals(extractedGroupKeyCodeWithIdSet.size(), 2);
		Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("ISU-IA"));
		Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("UGA-GA"));
		
		
		pstnRptGrpBo  = boService.findByPrimaryKey(PositionReportGroupBo.class, Collections.singletonMap("pmPositionReportGroupId", "4"));
		extractedGroupKeyCodeWithIdSet = pstnRptGrpBo.getGroupKeyCodeSet();
		Assert.assertEquals(extractedGroupKeyCodeWithIdSet.size(), 2);
		Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("IU-IN"));
		Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("UGA-GA"));
		
		
		pstnRptGrpBo  = boService.findByPrimaryKey(PositionReportGroupBo.class, Collections.singletonMap("pmPositionReportGroupId", "2"));
		extractedGroupKeyCodeWithIdSet = pstnRptGrpBo.getGroupKeyCodeSet();
		Assert.assertTrue( (extractedGroupKeyCodeWithIdSet == null) || extractedGroupKeyCodeWithIdSet.isEmpty() );		
		pstnRptGrpBo  = boService.findByPrimaryKey(PositionReportGroupBo.class, Collections.singletonMap("pmPositionReportGroupId", "3"));
		extractedGroupKeyCodeWithIdSet = pstnRptGrpBo.getGroupKeyCodeSet(); 
		Assert.assertTrue( (extractedGroupKeyCodeWithIdSet == null) || extractedGroupKeyCodeWithIdSet.isEmpty() );
	}
	
	
	
	
	@Test
	public void testKeyCodeSetOwnerAssignment() {
		PositionReportGroupBo pstnRptGrpBo  = boService.findByPrimaryKey(PositionReportGroupBo.class, Collections.singletonMap("pmPositionReportGroupId", "1"));
		for(PositionReportGroupKeyBo keyBo: pstnRptGrpBo.getEffectiveKeySet()) {
			Assert.assertEquals(keyBo.getOwnerId(), "1");
		}
		
		
		pstnRptGrpBo  = boService.findByPrimaryKey(PositionReportGroupBo.class, Collections.singletonMap("pmPositionReportGroupId", "4"));
		for(PositionReportGroupKeyBo keyBo: pstnRptGrpBo.getEffectiveKeySet()) {
			Assert.assertEquals(keyBo.getOwnerId(), "4");
		}		
	}

}