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
package org.kuali.kpme.pm.classification.flag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.pm.api.classification.Classification;
import org.kuali.kpme.pm.api.classification.flag.ClassificationFlag;
import org.kuali.kpme.pm.classification.ClassificationBo;
import org.kuali.kpme.pm.classification.ClassificationBoTest;

public class ClassificationFlagBoTest {


	private static Map<String, ClassificationFlag> testClassificationFlagBos;
	public static ClassificationFlag.Builder classificationFlagBuilder = ClassificationFlag.Builder.create();
	public static LocalDate currentTime = LocalDate.now();
	
	static{
		testClassificationFlagBos = new HashMap<String, ClassificationFlag>();
		
		classificationFlagBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		classificationFlagBuilder.setVersionNumber(1l);
		List<String> names = new ArrayList<String>();
		names.add("Name1");
		classificationFlagBuilder.setNames(names);
		classificationFlagBuilder.setCategory("CAT");
		classificationFlagBuilder.setPmFlagId("TST-CLSFCTNFLAG");
		classificationFlagBuilder.setPmPositionClassId("TST-PMCLASSID");
		classificationFlagBuilder.setEffectiveLocalDateOfOwner(currentTime);
		
		testClassificationFlagBos.put(classificationFlagBuilder.getPmFlagId(), classificationFlagBuilder.build());
		
	}
		
	@Test
    public void testNotEqualsWithGroup() {
    	ClassificationFlag immutable = ClassificationFlagBoTest.getClassificationFlag("TST-CLSFCTNFLAG");
    	ClassificationFlagBo bo = ClassificationFlagBo.from(immutable);
    	
    	ClassificationBo classificationBo = new ClassificationBo();
    	classificationBo.setEffectiveLocalDate(currentTime);
    	bo.setOwner(classificationBo);

        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, ClassificationFlagBo.to(bo));
    }

    public static ClassificationFlag getClassificationFlag(String classificationFlag) {
        return testClassificationFlagBos.get(classificationFlag);
    }
}