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
package org.kuali.kpme.edo.reviewlayerdef;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.edo.api.reviewlayerdef.EdoReviewLayerDefinition;

public class EdoReviewLayerDefinitionBoTest {
							 
	private static Map<String, EdoReviewLayerDefinition> testReviewlayerDefinitionBo;
	
	public static EdoReviewLayerDefinition.Builder edoReviewLayerDefinitionBuilder = EdoReviewLayerDefinition.Builder.create();
	
	static {
		testReviewlayerDefinitionBo = new HashMap<String, EdoReviewLayerDefinition>();
		
		edoReviewLayerDefinitionBuilder.setEdoReviewLayerDefinitionId("1000");
		edoReviewLayerDefinitionBuilder.setNodeName("testNodeName");
		edoReviewLayerDefinitionBuilder.setVoteType("tenure");
		edoReviewLayerDefinitionBuilder.setDescription("description");
		edoReviewLayerDefinitionBuilder.setReviewLetter(true);
		edoReviewLayerDefinitionBuilder.setReviewLevel("1");
		edoReviewLayerDefinitionBuilder.setRouteLevel("1");
		edoReviewLayerDefinitionBuilder.setWorkflowId("1000");
		edoReviewLayerDefinitionBuilder.setWorkflowQualifier("workflowQualifier");
		
		edoReviewLayerDefinitionBuilder.setUserPrincipalId("admin");
		edoReviewLayerDefinitionBuilder.setVersionNumber(1L);
		edoReviewLayerDefinitionBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		
		edoReviewLayerDefinitionBuilder.setId(edoReviewLayerDefinitionBuilder.getEdoReviewLayerDefinitionId());
		
		testReviewlayerDefinitionBo.put(edoReviewLayerDefinitionBuilder.getEdoReviewLayerDefinitionId(), edoReviewLayerDefinitionBuilder.build());
	}
	
	@Test
    public void testNotEqualsWithGroup() {
		EdoReviewLayerDefinition immutable = EdoReviewLayerDefinitionBoTest.getReviewlayerDefinition("1000");
		EdoReviewLayerDefinitionBo bo = EdoReviewLayerDefinitionBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoReviewLayerDefinitionBo.to(bo));
    }

    public static EdoReviewLayerDefinition getReviewlayerDefinition(String edoReviewLayerDefinitionId) {
        return testReviewlayerDefinitionBo.get(edoReviewLayerDefinitionId);
    }
    
}
