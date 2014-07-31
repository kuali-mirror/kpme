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
import org.kuali.kpme.edo.api.reviewlayerdef.EdoSuppReviewLayerDefinition;

public class EdoSuppReviewLayerDefinitionBoTest {
							 
	private static Map<String, EdoSuppReviewLayerDefinition> testSuppReviewlayerDefinitionBo;
	
	public static EdoSuppReviewLayerDefinition.Builder EdoSuppReviewLayerDefinitionBuilder = EdoSuppReviewLayerDefinition.Builder.create();
	
	static {
		testSuppReviewlayerDefinitionBo = new HashMap<String, EdoSuppReviewLayerDefinition>();    
		EdoSuppReviewLayerDefinitionBuilder.setEdoSuppReviewLayerDefinitionId("1000");
		EdoSuppReviewLayerDefinitionBuilder.setEdoReviewLayerDefinitionId("1000");
		EdoSuppReviewLayerDefinitionBuilder.setSuppNodeName("testNodeName");
		EdoSuppReviewLayerDefinitionBuilder.setAcknowledgeFlag(true);
		EdoSuppReviewLayerDefinitionBuilder.setVersionNumber(1L);
		EdoSuppReviewLayerDefinitionBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		
		testSuppReviewlayerDefinitionBo.put(EdoSuppReviewLayerDefinitionBuilder.getEdoSuppReviewLayerDefinitionId(), EdoSuppReviewLayerDefinitionBuilder.build());
	}
	
	@Test
    public void testNotEqualsWithGroup() {
		EdoSuppReviewLayerDefinition immutable = EdoSuppReviewLayerDefinitionBoTest.getSuppReviewlayerDefinition("1000");
		EdoSuppReviewLayerDefinitionBo bo = EdoSuppReviewLayerDefinitionBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoSuppReviewLayerDefinitionBo.to(bo));
    }

    public static EdoSuppReviewLayerDefinition getSuppReviewlayerDefinition(String EdoSuppReviewLayerDefinitionId) {
        return testSuppReviewlayerDefinitionBo.get(EdoSuppReviewLayerDefinitionId);
    }
    
}
