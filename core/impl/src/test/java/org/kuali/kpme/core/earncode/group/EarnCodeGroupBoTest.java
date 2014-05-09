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
package org.kuali.kpme.core.earncode.group;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.kpme.core.api.earncode.group.EarnCodeGroup;
import org.kuali.kpme.core.api.earncode.group.EarnCodeGroupDefinition;
import org.kuali.kpme.core.api.earncode.service.EarnCodeService;
import org.kuali.kpme.core.earncode.EarnCodeBoTest;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.name.EntityName;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.mockito.Mockito;


public class EarnCodeGroupBoTest {

	    private static Map<String, EarnCodeGroup> testEarnCodeGroupBos;
	    private static EarnCodeService mockEarnCodeService;
	    public static EarnCodeGroup.Builder earnCodeGroupBuilder = EarnCodeGroup.Builder.create("TST-ECG");
	    static {
	    	List<EarnCodeGroupDefinition.Builder> earnCodeGroupDefinitions = new ArrayList<EarnCodeGroupDefinition.Builder>();
	    	earnCodeGroupDefinitions.add(EarnCodeGroupDefinition.Builder.create(EarnCodeGroupDefinitionBoTest.getEarnCodeGroupDefinition("TST-EarnCode"))); 
	    	
	        testEarnCodeGroupBos = new HashMap<String, EarnCodeGroup>();
	        
	        earnCodeGroupBuilder.setEarnCodeGroup("TST-ECG");
	        earnCodeGroupBuilder.setDescr("Testing Immutable EarnCodeGroup");
	        earnCodeGroupBuilder.setWarningText("This is the warning test for earncodegroup");
	        earnCodeGroupBuilder.setHrEarnCodeGroupId("KPME_TEST_0001");
	        earnCodeGroupBuilder.setVersionNumber(1L);
	        earnCodeGroupBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
	        earnCodeGroupBuilder.setActive(true);
	        earnCodeGroupBuilder.setId(earnCodeGroupBuilder.getHrEarnCodeGroupId());
	        earnCodeGroupBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
	        earnCodeGroupBuilder.setCreateTime(DateTime.now());
	        earnCodeGroupBuilder.setUserPrincipalId("admin");
	        earnCodeGroupBuilder.setEarnCodeGroups(earnCodeGroupDefinitions);
	        testEarnCodeGroupBos.put(earnCodeGroupBuilder.getEarnCodeGroup(), earnCodeGroupBuilder.build());
	        
	    }
	    
	    @Before
	    public void setup() throws Exception {
	    	
	    	EarnCode earnCode = EarnCodeBoTest.getTestEarnCode("TST-EarnCode");
	        mockEarnCodeService = mock(EarnCodeService.class);
	        {
	            when( mockEarnCodeService.getEarnCode(anyString(), Mockito.any(LocalDate.class))).thenReturn(earnCode);
	        }
	    }

	    @Test
	    public void testNotEqualsWithGroup() {
	        EarnCodeGroup immutable = EarnCodeGroupBoTest.getEarnCodeGroup("TST-ECG");
	        EarnCodeGroupBo bo = EarnCodeGroupBo.from(immutable);
	        bo.getEarnCodeGroups().get(0).setEarnCodeService(mockEarnCodeService);
	        Assert.assertFalse(bo.equals(immutable));
	        Assert.assertFalse(immutable.equals(bo));
	        Assert.assertEquals(immutable, EarnCodeGroupBo.to(bo));
	    }

	    public static EarnCodeGroup getEarnCodeGroup(String earnCodeGroup) {
	        return testEarnCodeGroupBos.get(earnCodeGroup);
	    }
}