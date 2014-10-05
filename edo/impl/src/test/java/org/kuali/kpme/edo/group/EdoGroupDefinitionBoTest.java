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
package org.kuali.kpme.edo.group;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.edo.api.group.EdoGroupDefinition;

public class EdoGroupDefinitionBoTest {
	private static Map<String, EdoGroupDefinition> testEdoGroupDefinitionBos;
	public static EdoGroupDefinition.Builder edoEdoGroupDefinitionBuilder = EdoGroupDefinition.Builder.create();
	
	static {
		testEdoGroupDefinitionBos = new HashMap<String, EdoGroupDefinition>();
		edoEdoGroupDefinitionBuilder.setEdoGroupId("0001");
		edoEdoGroupDefinitionBuilder.setEdoWorkflowId("0001");
		edoEdoGroupDefinitionBuilder.setWorkflowLevel("01");
		edoEdoGroupDefinitionBuilder.setWorkflowType("DEFAULT");
		edoEdoGroupDefinitionBuilder.setDossierType("PT");
		edoEdoGroupDefinitionBuilder.setKimRoleName("DEFAULT");
		edoEdoGroupDefinitionBuilder.setKimTypeName("DEFAULT");
		edoEdoGroupDefinitionBuilder.setUserPrincipalId("admin");
		edoEdoGroupDefinitionBuilder.setVersionNumber(1L);
		edoEdoGroupDefinitionBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		edoEdoGroupDefinitionBuilder.setActive(true);
		edoEdoGroupDefinitionBuilder.setId(edoEdoGroupDefinitionBuilder.getEdoGroupId());
		edoEdoGroupDefinitionBuilder.setEffectiveLocalDate(new LocalDate(2014, 3, 1));
		
		testEdoGroupDefinitionBos.put(edoEdoGroupDefinitionBuilder.getEdoGroupId(), edoEdoGroupDefinitionBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	EdoGroupDefinition immutable = EdoGroupDefinitionBoTest.getEdoGroupDefinition("0001");
    	EdoGroupDefinitionBo bo = EdoGroupDefinitionBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoGroupDefinitionBo.to(bo));
    }

    public static EdoGroupDefinition getEdoGroupDefinition(String edoGroupDefinition) {
        return testEdoGroupDefinitionBos.get(edoGroupDefinition);
    }
   
}
