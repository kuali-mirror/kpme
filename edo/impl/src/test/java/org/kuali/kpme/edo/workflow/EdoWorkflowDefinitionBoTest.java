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
package org.kuali.kpme.edo.workflow;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.edo.api.workflow.EdoWorkflowDefinition;

public class EdoWorkflowDefinitionBoTest {
	private static Map<String, EdoWorkflowDefinition> testEdoWorkflowDefinitionBos;
	public static EdoWorkflowDefinition.Builder edoWorkflowDefinitionBuilder = EdoWorkflowDefinition.Builder.create();
	
	static {
		testEdoWorkflowDefinitionBos = new HashMap<String, EdoWorkflowDefinition>();
		edoWorkflowDefinitionBuilder.setEdoWorkflowId("0001");
		edoWorkflowDefinitionBuilder.setWorkflowName("MainWorkFlow");
		edoWorkflowDefinitionBuilder.setWorkflowDescription("MainWorkFlowDescription");
		edoWorkflowDefinitionBuilder.setUserPrincipalId("admin");
		edoWorkflowDefinitionBuilder.setVersionNumber(1L);
		edoWorkflowDefinitionBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		edoWorkflowDefinitionBuilder.setActionFullDateTime(new DateTime(2014, 3, 1, 0, 0));
		
		testEdoWorkflowDefinitionBos.put(edoWorkflowDefinitionBuilder.getEdoWorkflowId(), edoWorkflowDefinitionBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	EdoWorkflowDefinition immutable = EdoWorkflowDefinitionBoTest.getEdoWorkflowDefinition("0001");
    	EdoWorkflowDefinitionBo bo = EdoWorkflowDefinitionBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoWorkflowDefinitionBo.to(bo));
    }

    public static EdoWorkflowDefinition getEdoWorkflowDefinition(String edoWorkflowId) {
        return testEdoWorkflowDefinitionBos.get(edoWorkflowId);
    }
}
