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
package org.kuali.kpme.pm.positiontype;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.PSource;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;
import org.kuali.kpme.pm.api.positiontype.PositionType;

public class PositionTypeBoTest {
	private static Map<String, PositionType> testPositionTypeBos;
	public static PositionType.Builder positionTypeBuilder = PositionType.Builder.create("ISU-IA", "TST-PSTNTYP");
	
	static{
		testPositionTypeBos = new HashMap<String, PositionType>();
		
		positionTypeBuilder.setAcademicFlag(true);
		positionTypeBuilder.setActive(true);
		positionTypeBuilder.setCreateTime(DateTime.now());
		positionTypeBuilder.setDescription("Testing Immutable PositionType");
		positionTypeBuilder.setEffectiveLocalDate(new LocalDate(2014, 5, 6));
		positionTypeBuilder.setGroupKeyCode("ISU-IA");
		positionTypeBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		positionTypeBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		positionTypeBuilder.setPmPositionTypeId("KPME-TEST-0001");
		positionTypeBuilder.setId(positionTypeBuilder.getPmPositionTypeId());
		positionTypeBuilder.setPositionType("TST-PSTNTYP");
		positionTypeBuilder.setUserPrincipalId("admin");
		positionTypeBuilder.setVersionNumber(1l);
		
		testPositionTypeBos.put(positionTypeBuilder.getPositionType(), positionTypeBuilder.build());
	}
	
	@Test
    public void testNotEqualsWithGroup() {
    	PositionType immutable = PositionTypeBoTest.getPositionType("TST-PSTNTYP");
    	PositionTypeBo bo = PositionTypeBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PositionTypeBo.to(bo));
    }

    public static PositionType getPositionType(String positionType) {
        return testPositionTypeBos.get(positionType);
    }

}