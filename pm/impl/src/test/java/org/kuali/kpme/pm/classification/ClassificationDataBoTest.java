package org.kuali.kpme.pm.classification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.api.classification.Classification;
import org.kuali.kpme.pm.api.classification.duty.ClassificationDuty;
import org.kuali.kpme.pm.api.classification.duty.ClassificationDutyContract;
import org.kuali.kpme.pm.api.classification.flag.ClassificationFlag;
import org.kuali.kpme.pm.api.classification.qual.ClassificationQualification;

public class ClassificationDataBoTest {
	private static Map<String, ClassificationQualification> testClassificationQualificationBos;
	public static ClassificationQualification.Builder classificationQualificationBoBuilder = ClassificationQualification.Builder
			.create();
	
	private static Map<String, ClassificationFlag> testClassificationFlagBos;
	public static ClassificationFlag.Builder classificationFlagBuilder = ClassificationFlag.Builder.create();
	
	private static Map<String, ClassificationDuty> testClassificationDutyBos;
	public static ClassificationDuty.Builder classificationDutyBuilder = ClassificationDuty.Builder.create();
	
	private static Map<String, Classification> testClassificationBos;
	public static Classification.Builder classificationBuilder = Classification.Builder.create();
	
	static LocalDate currentTime = new LocalDate();

	
	static{
		testClassificationBos = new HashMap<String, Classification>();
		
		classificationBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		classificationBuilder.setVersionNumber(1l);
		
		classificationBuilder.setPmPositionClassId("TST-PMCLASSID");
		classificationBuilder.setId(classificationBuilder.getPmPositionClassId());
		classificationBuilder.setPoolEligible("");
		classificationBuilder.setPositionType("");
		classificationBuilder.setPositionReportGroup("");
		classificationBuilder.setLeaveEligible("");
		classificationBuilder.setBenefitsEligible("");
		classificationBuilder.setClassificationTitle("");
		classificationBuilder.setPositionClass("");
		classificationBuilder.setPercentTime(new BigDecimal(100.0));
		classificationBuilder.setSalaryGroup("");
		classificationBuilder.setTenureEligible("");
		classificationBuilder.setExternalReference("");
		
		classificationBuilder.setLeavePlan("");
		classificationBuilder.setPayGrade("");
		classificationBuilder.setActive(true);
		classificationBuilder.setEffectiveLocalDate(currentTime);
		
		testClassificationBos.put(classificationBuilder.getPmPositionClassId(), classificationBuilder.build());
		
		testClassificationQualificationBos = new HashMap<String, ClassificationQualification>();
		
		classificationQualificationBoBuilder.setQualificationValue("QLFCTN-VALUE");
        classificationQualificationBoBuilder.setQualificationType("");
        classificationQualificationBoBuilder.setDisplayOrder("DISPLAYORDER");
        classificationQualificationBoBuilder.setTypeValue("");
        classificationQualificationBoBuilder.setQualifier("QUALIFIER");
        classificationQualificationBoBuilder.setPmClassificationQualificationId("TST-CLASSFCTNQLFCTN");
        classificationQualificationBoBuilder.setPmPositionClassId("TST-PMCLASSID");
        classificationQualificationBoBuilder.setEffectiveLocalDateOfOwner(currentTime);
//        classificationQualificationBoBuilder.setOwner(Classification.Builder.create(getClassification("TST-PMCLASSID")));
        classificationQualificationBoBuilder.setVersionNumber(1L);
        classificationQualificationBoBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		
		testClassificationQualificationBos.put(classificationQualificationBoBuilder.getPmClassificationQualificationId(), classificationQualificationBoBuilder.build());
		
		testClassificationFlagBos = new HashMap<String, ClassificationFlag>();
		
		classificationFlagBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		classificationFlagBuilder.setVersionNumber(1l);
		List<String> names = new ArrayList<String>();
		names.add("Name1");
		classificationFlagBuilder.setNames(names);
		classificationFlagBuilder.setCategory("CAT");
		classificationFlagBuilder.setPmFlagId("TST-CLSFCTNFLAG");
		classificationFlagBuilder.setPmPositionClassId("TST-PMCLASSID");
//		classificationFlagBuilder.setOwner(Classification.Builder.create(getClassification("TST-PMCLASSID")));
		classificationFlagBuilder.setEffectiveLocalDateOfOwner(currentTime);
		testClassificationFlagBos.put(classificationFlagBuilder.getPmFlagId(), classificationFlagBuilder.build());
		
	
		testClassificationDutyBos = new HashMap<String, ClassificationDuty>();
		
		classificationDutyBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		classificationDutyBuilder.setVersionNumber(1l);
		classificationDutyBuilder.setName("name");
		classificationDutyBuilder.setDescription("descr");
		classificationDutyBuilder.setPercentage(new BigDecimal(100.0));
		classificationDutyBuilder.setPmDutyId("TST-CLSFCTNDUTY");
		classificationDutyBuilder.setPmPositionClassId("TST-PMCLASSID");
//		classificationDutyBuilder.setOwner(Classification.Builder.create(getClassification("TST-PMCLASSID")));
		classificationDutyBuilder.setEffectiveLocalDateOfOwner(currentTime);
		testClassificationDutyBos.put(classificationDutyBuilder.getPmDutyId(), classificationDutyBuilder.build());
	}
	
	public static Classification getClassification(String classification) {
        return testClassificationBos.get(classification);
    }
	
    public static ClassificationQualification getClassificationQualificationBo(String classificationQualification) {
        return testClassificationQualificationBos.get(classificationQualification);
    }
    
    public static ClassificationFlag getClassificationFlag(String classificationFlag) {
        return testClassificationFlagBos.get(classificationFlag);
    }

	public static ClassificationDutyContract getClassificationDuty(String classificationDuty) {
		return testClassificationDutyBos.get(classificationDuty);
	}

}
