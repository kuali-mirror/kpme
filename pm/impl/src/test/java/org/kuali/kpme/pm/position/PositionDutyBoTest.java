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
package org.kuali.kpme.pm.position;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.pm.api.position.Position;
import org.kuali.kpme.pm.api.position.PositionDuty;

public class PositionDutyBoTest {
	private static Map<String, PositionDuty> testPositionDutyBos;
	public static PositionDuty.Builder positionDutyBuilder = PositionDuty.Builder
			.create();
	static LocalDate currentTime = new LocalDate();

	
	static{
		
		testPositionDutyBos = new HashMap<String, PositionDuty>();
		
		positionDutyBuilder.setName("");
        positionDutyBuilder.setDescription("");
        positionDutyBuilder.setPercentage(new BigDecimal(100.0));
        positionDutyBuilder.setPmDutyId("TST-PSTNDUTY");
        positionDutyBuilder.setHrPositionId("");
        positionDutyBuilder.setEffectiveLocalDateOfOwner(currentTime);
//        positionDutyBuilder.setOwner(Position.Builder.create(PositionDataBoTest.getPosition("TST-PSTN")));
        positionDutyBuilder.setVersionNumber(1l);
        positionDutyBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		
		testPositionDutyBos.put(positionDutyBuilder.getPmDutyId(), positionDutyBuilder.build());
		
	}
	

	@Test
    public void testNotEqualsWithGroup() {
    	PositionDuty immutable = PositionDutyBoTest.getPositionDutyBo("TST-PSTNDUTY");
    	PositionDutyBo bo = PositionDutyBo.from(immutable);
        
    	PositionBo positionBo = new PositionBo();
		positionBo.setEffectiveLocalDate(currentTime);
		bo.setOwner(positionBo);
    	
    	Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PositionDutyBo.to(bo));
    }

    public static PositionDuty getPositionDutyBo(String positionType) {
        return testPositionDutyBos.get(positionType);
    }
}
