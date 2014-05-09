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

import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.kpme.core.api.earncode.group.EarnCodeGroupDefinition;
import org.kuali.kpme.core.api.earncode.service.EarnCodeService;
import org.kuali.kpme.core.earncode.EarnCodeBoTest;
import org.mockito.Mockito;

public class EarnCodeGroupDefinitionBoTest {
	
	    private static Map<String, EarnCodeGroupDefinition> testEarnCodeGroupDefinitionBos;
	    private static EarnCodeService mockEarnCodeService;
	    
	    public static EarnCodeGroupDefinition.Builder earnCodeGroupDefinitionBuilder = EarnCodeGroupDefinition.Builder.create("TST-EarnCode");
	    
	    static {
	        testEarnCodeGroupDefinitionBos = new HashMap<String, EarnCodeGroupDefinition>();
	        
	        earnCodeGroupDefinitionBuilder.setHrEarnCodeGroupId("KPME-TEST-0001");
	        earnCodeGroupDefinitionBuilder.setEarnCode("TST-EarnCode");
	        earnCodeGroupDefinitionBuilder.setHrEarnCodeGroupDefId("TST-ECGDEF");
	        earnCodeGroupDefinitionBuilder.setEarnCodeDesc(EarnCodeBoTest.getTestEarnCode(earnCodeGroupDefinitionBuilder.getEarnCode()).getDescription());
	        earnCodeGroupDefinitionBuilder.setVersionNumber(1L);
	        earnCodeGroupDefinitionBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
	        earnCodeGroupDefinitionBuilder.setId(earnCodeGroupDefinitionBuilder.getHrEarnCodeGroupDefId());
	        earnCodeGroupDefinitionBuilder.setActive(true);
	        earnCodeGroupDefinitionBuilder.setUserPrincipalId("admin");
	        
	        testEarnCodeGroupDefinitionBos.put(earnCodeGroupDefinitionBuilder.getEarnCode(), earnCodeGroupDefinitionBuilder.build());
	        
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
	        EarnCodeGroupDefinition immutable = EarnCodeGroupDefinitionBoTest.getEarnCodeGroupDefinition("TST-EarnCode");
	        EarnCodeGroupDefinitionBo bo = EarnCodeGroupDefinitionBo.from(immutable);
	        bo.setEarnCodeService(mockEarnCodeService);
	        Assert.assertFalse(bo.equals(immutable));
	        Assert.assertFalse(immutable.equals(bo));
	        Assert.assertEquals(immutable, EarnCodeGroupDefinitionBo.to(bo));
	    }
	    
	    

	    public static EarnCodeGroupDefinition getEarnCodeGroupDefinition(String EarnCodeGroupDefinition) {
	        return testEarnCodeGroupDefinitionBos.get(EarnCodeGroupDefinition);
	    }
}