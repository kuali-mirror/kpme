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

import java.util.*;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.pm.api.position.Position;
import org.kuali.kpme.pm.api.position.PstnFlag;

public class PstnFlagBoTest {
	private static Map<String, PstnFlag> testPstnFlagBos;
	public static PstnFlag.Builder pstnFlagBuilder = PstnFlag.Builder.create();
	static LocalDate currentTime = new LocalDate();

	static {

		testPstnFlagBos = new HashMap<String, PstnFlag>();
		
		List<String> names = new ArrayList<String>();
		names.add("Name1");
		pstnFlagBuilder.setCategory("CAT");
		pstnFlagBuilder.setHrPositionId("");
		pstnFlagBuilder.setNames(names);
		pstnFlagBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		pstnFlagBuilder.setEffectiveLocalDateOfOwner(currentTime);
//		pstnFlagBuilder.setOwner(Position.Builder.create(PositionDataBoTest.getPosition("TST-PSTN")));
		pstnFlagBuilder.setPmFlagId("TST-PSTNFLAG");
		pstnFlagBuilder.setVersionNumber(1L);
		
		
		testPstnFlagBos.put(pstnFlagBuilder.getPmFlagId(),pstnFlagBuilder.build());
		System.out.println(testPstnFlagBos);
	}

	@Test
    public void testNotEqualsWithGroup() {
    	PstnFlag immutable = PstnFlagBoTest.getPstnFlag("TST-PSTNFLAG");
    	PstnFlagBo bo = PstnFlagBo.from(immutable);
    	
    	PositionBo positionBo = new PositionBo();
		positionBo.setEffectiveLocalDate(currentTime);
		bo.setOwner(positionBo);
    	
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PstnFlagBo.to(bo));
    }

    public static PstnFlag getPstnFlag(String pstnFlag) {
         PstnFlag pstnFlag1 = testPstnFlagBos.get(pstnFlag);
         return pstnFlag1;
    }

}