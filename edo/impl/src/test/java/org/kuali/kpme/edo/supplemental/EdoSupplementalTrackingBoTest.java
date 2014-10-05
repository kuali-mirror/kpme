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
package org.kuali.kpme.edo.supplemental;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.edo.api.supplemental.EdoSupplementalTracking;

public class EdoSupplementalTrackingBoTest {
	private static Map<String, EdoSupplementalTracking> testEdoSupplementalTrackingBos;
	public static EdoSupplementalTracking.Builder edoSupplementalTrackingBuilder = EdoSupplementalTracking.Builder.create();
	
	static {
		testEdoSupplementalTrackingBos = new HashMap<String, EdoSupplementalTracking>();
		edoSupplementalTrackingBuilder.setEdoSupplementalTrackingId("0001");
		edoSupplementalTrackingBuilder.setEdoDossierId("0001");
		edoSupplementalTrackingBuilder.setReviewLevel(1);
		edoSupplementalTrackingBuilder.setAcknowledged(true);
		edoSupplementalTrackingBuilder.setUserPrincipalId("admin");
		edoSupplementalTrackingBuilder.setVersionNumber(1L);
		edoSupplementalTrackingBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		edoSupplementalTrackingBuilder.setActionFullDateTime(new DateTime(2014, 3, 1, 0, 0));
		
		testEdoSupplementalTrackingBos.put(edoSupplementalTrackingBuilder.getEdoSupplementalTrackingId(), edoSupplementalTrackingBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	EdoSupplementalTracking immutable = EdoSupplementalTrackingBoTest.getEdoSupplementalTracking("0001");
    	EdoSupplementalTrackingBo bo = EdoSupplementalTrackingBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoSupplementalTrackingBo.to(bo));
    }

    public static EdoSupplementalTracking getEdoSupplementalTracking(String edoSupplementalTrackingId) {
        return testEdoSupplementalTrackingBos.get(edoSupplementalTrackingId);
    }
}
