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

package org.kuali.kpme.pm.positiontype;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.pm.PMIntegrationTestCase;
import org.kuali.kpme.pm.api.positiontype.PositionType;
import org.kuali.kpme.pm.positionreportgroup.PositionReportGroupBo;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;

import javax.swing.text.Position;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mlemons on 6/4/14.
 */

public class PositionTypeGroupKeyWhiteListTest extends PMIntegrationTestCase {
    private BusinessObjectService boService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        boService = KRADServiceLocator.getBusinessObjectService();
    }

    @Test
    public void testEffectiveKeySetAndListLoad()
    {
        PositionTypeBo pstnTypBo = boService.findByPrimaryKey(PositionTypeBo.class, Collections.singletonMap("pmPositionTypeId", "1"));
        Assert.assertNotNull(pstnTypBo);

        List<PositionTypeGroupKeyBo> effectiveKeyList = pstnTypBo.getEffectiveKeyList();
        Assert.assertNotNull(effectiveKeyList);
        Assert.assertEquals(effectiveKeyList.size(), 2);

        Set<PositionTypeGroupKeyBo> effectiveKeySet = pstnTypBo.getEffectiveKeySet();
        Assert.assertNotNull(effectiveKeySet);
        Assert.assertEquals(effectiveKeySet.size(), 2);
        Set<String> idSet =  new HashSet<String>();
        for(PositionTypeGroupKeyBo keyBo: effectiveKeySet)
        {
            idSet.add(keyBo.getId());
            Assert.assertTrue(effectiveKeyList.contains(keyBo));
            Assert.assertTrue (( (keyBo.getId().equals("kpme_mlemons_test_1000")) && (keyBo.getGroupKeyCode().equalsIgnoreCase("ISU-IA")) ) ||
                            ( (keyBo.getId().equals("kpme_mlemons_test_1001")) && (keyBo.getGroupKeyCode().equalsIgnoreCase("UGA-GA")) )
                            );
        }
        Assert.assertTrue(idSet.contains("kpme_mlemons_test_1000"));
        Assert.assertTrue(idSet.contains("kpme_mlemons_test_1001"));



        pstnTypBo = boService.findByPrimaryKey(PositionTypeBo.class, Collections.singletonMap("pmPositionTypeId", "4"));
        Assert.assertNotNull(pstnTypBo);

        effectiveKeyList = pstnTypBo.getEffectiveKeyList();
        Assert.assertNotNull(effectiveKeyList);
        Assert.assertEquals(effectiveKeyList.size(), 2);

        effectiveKeySet = pstnTypBo.getEffectiveKeySet();
        Assert.assertNotNull(effectiveKeyList);
        Assert.assertEquals(effectiveKeySet.size(), 2);
        idSet = new HashSet<String>();
        for (PositionTypeGroupKeyBo keyBo : effectiveKeySet)
        {
            org.junit.Assert.assertEquals(keyBo.getOwnerId(), "4");
            idSet.add(keyBo.getId());
            org.junit.Assert.assertTrue(effectiveKeyList.contains(keyBo));
            org.junit.Assert.assertTrue(((keyBo.getId().equals("kpme_mlemons_test_1002")) && (keyBo.getGroupKeyCode().equalsIgnoreCase("UGA-GA"))) ||
                            ((keyBo.getId().equals("kpme_mlemons_test_1003")) && (keyBo.getGroupKeyCode().equalsIgnoreCase("IU-IN")))
            );
        }
        Assert.assertTrue(idSet.contains("kpme_mlemons_test_1002"));
        Assert.assertTrue(idSet.contains("kpme_mlemons_test_1003"));

        pstnTypBo = boService.findByPrimaryKey(PositionTypeBo.class, Collections.singletonMap("pmPositionTypeId", "2"));
        Assert.assertNotNull(pstnTypBo);
        effectiveKeyList = pstnTypBo.getEffectiveKeyList();
        Assert.assertTrue( (effectiveKeyList == null) || (effectiveKeyList.isEmpty()) );
        effectiveKeySet = pstnTypBo.getEffectiveKeySet();
        Assert.assertTrue( (effectiveKeyList == null) || (effectiveKeyList.isEmpty()) );

        pstnTypBo = boService.findByPrimaryKey(PositionTypeBo.class, Collections.singletonMap("pmPositionTypeId", "3"));
        Assert.assertNotNull(pstnTypBo);
        effectiveKeyList = pstnTypBo.getEffectiveKeyList();
        Assert.assertTrue( (effectiveKeyList == null) || (effectiveKeyList.isEmpty()) );
        effectiveKeySet = pstnTypBo.getEffectiveKeySet();
        Assert.assertTrue( (effectiveKeyList == null) || (effectiveKeyList.isEmpty()) );
    }

    @Test
    public void testGroupKeySetExtraction()
    {
        PositionTypeBo pstnTypBo = boService.findByPrimaryKey(PositionTypeBo.class, Collections.singletonMap("pmPositionTypeId", "1"));
        Assert.assertFalse(pstnTypBo.getGroupKeyCodeSet().isEmpty());

        Set<String> extractedGroupKeyCodeWithIdSet = new HashSet<String>();
        Set<HrGroupKeyBo> extractedGroupKeySet = pstnTypBo.getGroupKeySet();
        for(HrGroupKeyBo extractedGroupKey: extractedGroupKeySet)
        {
            extractedGroupKeyCodeWithIdSet.add(extractedGroupKey.getGroupKeyCode() + "-" + extractedGroupKey.getId());
        }
        Assert.assertEquals(extractedGroupKeyCodeWithIdSet.size(), 2);
        Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("ISU-IA-8"));
        Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("UGA-GA-7"));

        pstnTypBo = boService.findByPrimaryKey(PositionTypeBo.class, Collections.singletonMap("pmPositionTypeId", "4"));
        Assert.assertFalse(pstnTypBo.getGroupKeySet().isEmpty());
        extractedGroupKeyCodeWithIdSet = new HashSet<String>();
        extractedGroupKeySet = pstnTypBo.getGroupKeySet();
        for(HrGroupKeyBo extractedGroupKey : extractedGroupKeySet) {
            extractedGroupKeyCodeWithIdSet.add(extractedGroupKey.getGroupKeyCode() + "-" + extractedGroupKey.getId());
        }
        Assert.assertEquals(extractedGroupKeyCodeWithIdSet.size(),2);
        Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("IU-IN-5"));
        Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("UGA-GA-7"));

        pstnTypBo = boService.findByPrimaryKey(PositionTypeBo.class, Collections.singletonMap("pmPositionTypeId", "2"));
        extractedGroupKeySet = pstnTypBo.getGroupKeySet();
        Assert.assertTrue( (extractedGroupKeySet == null) || (extractedGroupKeySet.isEmpty()) );
        pstnTypBo = boService.findByPrimaryKey(PositionTypeBo.class, Collections.singletonMap("pmPositionTypeId", "3"));
        extractedGroupKeySet = pstnTypBo.getGroupKeySet();
        Assert.assertTrue( (extractedGroupKeySet == null) || (extractedGroupKeySet.isEmpty()) );
    }

    @Test
    public void testGroupKeyCodeSetExtraction()
    {
        PositionTypeBo pstnTypBo = boService.findByPrimaryKey(PositionTypeBo.class, Collections.singletonMap("pmPositionTypeId", "1"));
        Set<String> extractedGroupKeyCodeWithIdSet = pstnTypBo.getGroupKeyCodeSet();
        Assert.assertEquals(extractedGroupKeyCodeWithIdSet.size(), 2);
        Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("ISU-IA"));
        Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("UGA-GA"));

        pstnTypBo = boService.findByPrimaryKey(PositionTypeBo.class, Collections.singletonMap("pmPositionTypeId", "1"));
        extractedGroupKeyCodeWithIdSet = pstnTypBo.getGroupKeyCodeSet();
        Assert.assertEquals(extractedGroupKeyCodeWithIdSet.size(), 2);
        Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("ISU-IA"));
        Assert.assertTrue(extractedGroupKeyCodeWithIdSet.contains("UGA-GA"));

        pstnTypBo = boService.findByPrimaryKey(PositionTypeBo.class, Collections.singletonMap("pmPositionTypeId", "2"));
        extractedGroupKeyCodeWithIdSet = pstnTypBo.getGroupKeyCodeSet();
        Assert.assertTrue( (extractedGroupKeyCodeWithIdSet == null) || extractedGroupKeyCodeWithIdSet.isEmpty() );

        pstnTypBo = boService.findByPrimaryKey(PositionTypeBo.class, Collections.singletonMap("pmPositionTypeId", "3"));
        extractedGroupKeyCodeWithIdSet = pstnTypBo.getGroupKeyCodeSet();
        Assert.assertTrue( (extractedGroupKeyCodeWithIdSet == null) || extractedGroupKeyCodeWithIdSet.isEmpty() );
    }

    @Test
    public void testKeyCodeSetOwnerAssignment()
    {
        PositionTypeBo pstnTypBo = boService.findByPrimaryKey(PositionTypeBo.class, Collections.singletonMap("pmPositionTypeId", "1"));
        for (PositionTypeGroupKeyBo keyBo: pstnTypBo.getEffectiveKeySet())
        {
            Assert.assertEquals(keyBo.getOwnerId(), "1");
        }


        pstnTypBo = boService.findByPrimaryKey(PositionTypeBo.class, Collections.singletonMap("pmPositionTypeId", "5"));
        for (PositionTypeGroupKeyBo keyBo: pstnTypBo.getEffectiveKeySet())
        {
            Assert.assertEquals(keyBo.getOwnerId(), "5");
        }
    }
}
