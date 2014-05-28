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

package org.kuali.kpme.pm.positionresponsibility;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.PSource;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.pm.api.position.Position;
import org.kuali.kpme.pm.api.position.Position.Builder;
import org.kuali.kpme.pm.api.positionresponsibility.PositionResponsibility;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.kpme.pm.position.PositionBoTest;
import org.kuali.kpme.pm.position.PositionDerived;
import org.kuali.rice.location.api.campus.Campus;

public class PositionResponsibilityBoTest {

	private static Map<String, PositionResponsibility> testPositionResponsibilityBos;
	public static PositionResponsibility.Builder positionResponsibilityBuilder = PositionResponsibility.Builder.create();
	public static final LocalDate currentTime = LocalDate.now();
	static {
		testPositionResponsibilityBos = new HashMap<String, PositionResponsibility>();
		
		positionResponsibilityBuilder.setPercentTime(new BigDecimal(100));
		positionResponsibilityBuilder.setPositionResponsibilityOption("TST-PSTNRESPOPT");
		positionResponsibilityBuilder.setPositionResponsibilityId("KPME_TEST_0001");
		positionResponsibilityBuilder.setHrPositionId("");
		positionResponsibilityBuilder.setVersionNumber(1L);
		positionResponsibilityBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		positionResponsibilityBuilder.setEffectiveLocalDateOfOwner(currentTime);
		
		testPositionResponsibilityBos.put(positionResponsibilityBuilder.getPositionResponsibilityOption(), positionResponsibilityBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	PositionResponsibility immutable = PositionResponsibilityBoTest.getPositionResponsibility("TST-PSTNRESPOPT");
    	PositionResponsibilityBo bo = PositionResponsibilityBo.from(immutable);
    	
    	PositionBo positionBo = new PositionBo();
    	positionBo.setEffectiveLocalDate(currentTime);
    	bo.setOwner(positionBo);
    	
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PositionResponsibilityBo.to(bo));
    }

    public static PositionResponsibility getPositionResponsibility(String positionResponsibility) {
        return testPositionResponsibilityBos.get(positionResponsibility);
    }
	
}
