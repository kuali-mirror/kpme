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
package org.kuali.kpme.pm.pstnqlfrtype;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.pm.api.pstnqlfrtype.PstnQlfrType;

public class PstnQlfrTypeBoTest {

	private static Map<String, PstnQlfrType> testPstnQlfrTypeBos;
	public static PstnQlfrType.Builder pstnQlfrTypeBuilder = PstnQlfrType.Builder.create();
	
	static{
		testPstnQlfrTypeBos = new HashMap<String, PstnQlfrType>();
		
		pstnQlfrTypeBuilder.setCode("ED");
		pstnQlfrTypeBuilder.setDescr("Testing Immutable Position Qualifier Type");
		pstnQlfrTypeBuilder.setActive(true);
		pstnQlfrTypeBuilder.setCreateTime(DateTime.now());
		pstnQlfrTypeBuilder.setType("Text");
		pstnQlfrTypeBuilder.setEffectiveLocalDate(new LocalDate(2014, 5, 6));
		pstnQlfrTypeBuilder.setTypeValue("TST-PSTNQLFRTYP");
		
		pstnQlfrTypeBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		pstnQlfrTypeBuilder.setPmPstnQlfrTypeId("KPME-TEST-0001");
		pstnQlfrTypeBuilder.setId(pstnQlfrTypeBuilder.getPmPstnQlfrTypeId());
		
		pstnQlfrTypeBuilder.setUserPrincipalId("admin");
		pstnQlfrTypeBuilder.setVersionNumber(1l);
		
		testPstnQlfrTypeBos.put(pstnQlfrTypeBuilder.getTypeValue(), pstnQlfrTypeBuilder.build());
	}
	
	@Test
    public void testNotEqualsWithGroup() {
    	PstnQlfrType immutable = PstnQlfrTypeBoTest.getPstnQlfrType("TST-PSTNQLFRTYP");
    	PstnQlfrTypeBo bo = PstnQlfrTypeBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PstnQlfrTypeBo.to(bo));
    }

    public static PstnQlfrType getPstnQlfrType(String positionType) {
        return testPstnQlfrTypeBos.get(positionType);
    }

}
