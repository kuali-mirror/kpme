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
package org.kuali.kpme.pm.classification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.pm.api.classification.Classification;
import org.kuali.kpme.pm.api.classification.duty.ClassificationDuty;
import org.kuali.kpme.pm.api.classification.flag.ClassificationFlag;
import org.kuali.kpme.pm.api.classification.qual.ClassificationQualification;
import org.kuali.kpme.pm.classification.flag.ClassificationFlagBoTest;

public class ClassificationBoTest {


	private static Map<String, Classification> testClassificationTestBos;
	public static Classification.Builder classificationBuilder = Classification.Builder.create();

	static{
		testClassificationTestBos = new HashMap<String, Classification>();
		
		classificationBuilder = Classification.Builder.create(ClassificationDataBoTest.getClassification("TST-PMCLASSID"));
		
		List<ClassificationQualification.Builder> classificationQualificationList = new ArrayList<ClassificationQualification.Builder>();
		classificationQualificationList.add(ClassificationQualification.Builder.create(ClassificationDataBoTest.getClassificationQualificationBo("TST-CLASSFCTNQLFCTN")));
		
		classificationBuilder.setQualificationList(classificationQualificationList);
				
		List<ClassificationFlag.Builder> flagList = new ArrayList<ClassificationFlag.Builder>();
		flagList.add(ClassificationFlag.Builder.create(ClassificationDataBoTest.getClassificationFlag("TST-CLSFCTNFLAG")));
		classificationBuilder.setFlagList(flagList);
		
		List<ClassificationDuty.Builder> dutyList = new ArrayList<ClassificationDuty.Builder>();
		dutyList.add(ClassificationDuty.Builder.create(ClassificationDataBoTest.getClassificationDuty("TST-CLSFCTNDUTY")));
		classificationBuilder.setDutyList(dutyList);
		
		testClassificationTestBos.put(classificationBuilder.getPmPositionClassId(), classificationBuilder.build());
		
	}
		
	@Test
    public void testNotEqualsWithGroup() {
    	Classification immutable = ClassificationBoTest.getClassification("TST-PMCLASSID");
    	
    	ClassificationBo bo = ClassificationBo.from(immutable);
    	bo.getQualificationList().get(0).setOwner(bo);
    	bo.getFlagList().get(0).setOwner(bo);
    	bo.getDutyList().get(0).setOwner(bo);
    	Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, ClassificationBo.to(bo));
    }

    public static Classification getClassification(String classification) {
        return testClassificationTestBos.get(classification);
    }
}