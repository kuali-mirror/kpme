package org.kuali.kpme.pm.classification.qual;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.pm.api.classification.Classification;
import org.kuali.kpme.pm.api.classification.qual.ClassificationQualification;
import org.kuali.kpme.pm.classification.ClassificationBo;
import org.kuali.kpme.pm.classification.ClassificationBoTest;
import org.kuali.kpme.pm.position.PositionBo;


public class ClassificationQualificationBoTest {
	private static Map<String, ClassificationQualification> testClassificationQualificationBos;
	public static ClassificationQualification.Builder classificationQualificationBoBuilder = ClassificationQualification.Builder
			.create();

	static LocalDate currentTime = new LocalDate();
	
	static{
		
		testClassificationQualificationBos = new HashMap<String, ClassificationQualification>();
		
		classificationQualificationBoBuilder.setQualificationValue("QLFCTN-VALUE");
        classificationQualificationBoBuilder.setQualificationType("");
        classificationQualificationBoBuilder.setDisplayOrder("DISPLAYORDER");
        classificationQualificationBoBuilder.setTypeValue("");
        classificationQualificationBoBuilder.setQualifier("QUALIFIER");
        classificationQualificationBoBuilder.setPmClassificationQualificationId("TST-CLASSFCTNQLFCTN");
        classificationQualificationBoBuilder.setPmPositionClassId("TST-PMCLASSID");
        classificationQualificationBoBuilder.setEffectiveLocalDateOfOwner(currentTime);
//        classificationQualificationBoBuilder.setOwner(Classification.Builder.create(ClassificationBoTest.getClassification("TST-PMCLASSID")));
        classificationQualificationBoBuilder.setVersionNumber(1L);
        classificationQualificationBoBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		
		testClassificationQualificationBos.put(classificationQualificationBoBuilder.getPmClassificationQualificationId(), classificationQualificationBoBuilder.build());
		
	}
	

	@Test
    public void testNotEqualsWithGroup() {
    	ClassificationQualification immutable = ClassificationQualificationBoTest.getClassificationQualificationBo("TST-CLASSFCTNQLFCTN");
    	ClassificationQualificationBo bo = ClassificationQualificationBo.from(immutable);
    	
    	ClassificationBo classificationBo = new ClassificationBo();
		classificationBo.setEffectiveLocalDate(currentTime);
		bo.setOwner(classificationBo);
    	
		System.out.println("Bo ... "+bo.getEffectiveDateOfOwner());
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        System.out.println("Immutable ,.. "+immutable.getEffectiveLocalDateOfOwner());
        Assert.assertEquals(immutable, ClassificationQualificationBo.to(bo));
    }

    public static ClassificationQualification getClassificationQualificationBo(String classificationQualification) {
        return testClassificationQualificationBos.get(classificationQualification);
    }

}
