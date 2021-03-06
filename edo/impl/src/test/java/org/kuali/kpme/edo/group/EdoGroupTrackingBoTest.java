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
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;
import org.kuali.kpme.edo.api.group.EdoGroupTracking;

public class EdoGroupTrackingBoTest {
	private static Map<String, EdoGroupTracking> testEdoGroupTrackingBos;
	public static EdoGroupTracking.Builder edoGroupTrackingBoBuilder = EdoGroupTracking.Builder.create();
	
	static {
		testEdoGroupTrackingBos = new HashMap<String, EdoGroupTracking>();
		edoGroupTrackingBoBuilder.setEdoGroupTrackingId("0001");
		edoGroupTrackingBoBuilder.setEdoWorkflowId("0001");
		edoGroupTrackingBoBuilder.setDepartmentId("DEFAULT");
		edoGroupTrackingBoBuilder.setOrganizationCode("DEFAULT");
		edoGroupTrackingBoBuilder.setReviewLevelName("CHAIR");
		edoGroupTrackingBoBuilder.setGroupName("CHAIR");
		edoGroupTrackingBoBuilder.setGroupKeyCode("ISU-IA");
		edoGroupTrackingBoBuilder.setUserPrincipalId("admin");
		edoGroupTrackingBoBuilder.setVersionNumber(1L);
		edoGroupTrackingBoBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		edoGroupTrackingBoBuilder.setActive(true);
		edoGroupTrackingBoBuilder.setId(edoGroupTrackingBoBuilder.getEdoGroupTrackingId());
		edoGroupTrackingBoBuilder.setEffectiveLocalDate(new LocalDate(2014, 3, 1));
		
		edoGroupTrackingBoBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		testEdoGroupTrackingBos.put(edoGroupTrackingBoBuilder.getEdoGroupTrackingId(), edoGroupTrackingBoBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	EdoGroupTracking immutable = EdoGroupTrackingBoTest.getEdoGroupTracking("0001");
    	EdoGroupTrackingBo bo = EdoGroupTrackingBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoGroupTrackingBo.to(bo));
    }

    public static EdoGroupTracking getEdoGroupTracking(String edoGroupTrackingId) {
        return testEdoGroupTrackingBos.get(edoGroupTrackingId);
    }
   
}
