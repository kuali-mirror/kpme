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
package org.kuali.kpme.pm.classification.duty;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.pm.api.classification.Classification;
import org.kuali.kpme.pm.api.classification.duty.ClassificationDuty;
import org.kuali.kpme.pm.classification.ClassificationBo;
import org.kuali.kpme.pm.classification.ClassificationBoTest;

public class ClassificationDutyBoTest {

	private static Map<String, ClassificationDuty> testClassificationDutyBos;
	public static ClassificationDuty.Builder classificationDutyBuilder = ClassificationDuty.Builder.create();
	public static LocalDate currentTime = LocalDate.now();
	static{
		testClassificationDutyBos = new HashMap<String, ClassificationDuty>();
		
		classificationDutyBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		classificationDutyBuilder.setVersionNumber(1l);
		classificationDutyBuilder.setName("name");
		classificationDutyBuilder.setDescription("descr");
		classificationDutyBuilder.setPercentage(new BigDecimal(100.0));
		classificationDutyBuilder.setPmDutyId("TST-CLSFCTNDUTY");
		classificationDutyBuilder.setPmPositionClassId("TST-PMCLASSID");
		classificationDutyBuilder.setEffectiveLocalDateOfOwner(currentTime);
//		classificationDutyBuilder.setOwner(Classification.Builder.create(ClassificationBoTest.getClassification("TST-PMCLASSID")));
		
		testClassificationDutyBos.put(classificationDutyBuilder.getPmDutyId(), classificationDutyBuilder.build());
		
	}
		
	@Test
    public void testNotEqualsWithGroup() {
    	ClassificationDuty immutable = ClassificationDutyBoTest.getClassificationDuty("TST-CLSFCTNDUTY");
    	ClassificationDutyBo bo = ClassificationDutyBo.from(immutable);
    	
    	ClassificationBo classificationBo = new ClassificationBo();
    	classificationBo.setEffectiveLocalDate(currentTime);
    	bo.setOwner(classificationBo);
    	
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, ClassificationDutyBo.to(bo));
    }

    public static ClassificationDuty getClassificationDuty(String classificationDuty) {
        return testClassificationDutyBos.get(classificationDuty);
    }
}