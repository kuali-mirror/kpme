package org.kuali.kpme.pm.position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;
import org.kuali.kpme.pm.api.classification.qual.ClassificationQualification;
import org.kuali.kpme.pm.api.position.Position;
import org.kuali.kpme.pm.api.position.PositionDuty;
import org.kuali.kpme.pm.api.position.PositionQualification;
import org.kuali.kpme.pm.api.position.PstnFlag;
import org.kuali.kpme.pm.api.positiondepartment.PositionDepartment;
import org.kuali.kpme.pm.classification.ClassificationBo;
import org.kuali.kpme.pm.classification.qual.ClassificationQualificationBoTest;

public class PositionBoTest {
	private static Map<String, Position> testPositionBos;
	public static Position.Builder positionBuilder = Position.Builder.create("1", "ISU-IA");

	static {
		testPositionBos = new HashMap<String, Position>();

		positionBuilder.setActive(true);
		positionBuilder.setBenefitsEligible("N");
		positionBuilder.setCategory("");
		positionBuilder.setClassificationTitle("");
		positionBuilder.setContract("");
		positionBuilder.setContractType("");
		positionBuilder.setCreateTime(DateTime.now());
		positionBuilder.setDescription("");
		positionBuilder.setGroupKeyCode("ISU-IA");
		positionBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		positionBuilder.setHrPositionId("");
		positionBuilder.setId("");
		positionBuilder.setLeaveEligible("");
		positionBuilder.setMaxPoolHeadCount(0);
		positionBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		positionBuilder.setPayGrade("");
		positionBuilder.setPayStep("");
		positionBuilder.setPositionNumber("1");
		
		List<PositionDepartment.Builder> positionDeptList = new ArrayList<PositionDepartment.Builder>();
		positionDeptList.add(PositionDepartment.Builder.create(PositionDataBoTest.getPositionDepartment("TST-PSTNDEPT")));
		positionBuilder.setDepartmentList(positionDeptList);
		
		List<PstnFlag.Builder> pstnFlagList = new ArrayList<PstnFlag.Builder>();
		pstnFlagList.add(PstnFlag.Builder.create(PstnFlagBoTest.getPstnFlag("TST-PSTNFLAG")));
		positionBuilder.setFlagList(pstnFlagList);
		
		List<PositionDuty.Builder> positionDutyList = new ArrayList<PositionDuty.Builder>();
		positionDutyList.add(PositionDuty.Builder.create(PositionDutyBoTest.getPositionDutyBo("TST-PSTNDUTY")));
		positionBuilder.setDutyList(positionDutyList);

		List<PositionQualification.Builder> positionQualificationList = new ArrayList<PositionQualification.Builder>();
		positionQualificationList.add(PositionQualification.Builder.create(PositionQualificationBoTest.getPositionQualificationBo("TST-PSTNQLFCTN")));
		positionBuilder.setQualificationList(positionQualificationList);
		
		List<ClassificationQualification.Builder> classificationQualificationList = new ArrayList<ClassificationQualification.Builder>();
		classificationQualificationList.add(ClassificationQualification.Builder.create(ClassificationQualificationBoTest.getClassificationQualificationBo("TST-CLASSFCTNQLFCTN")));
		positionBuilder.setRequiredQualList(classificationQualificationList);
						
		testPositionBos.put("TST-PSTN", positionBuilder.build());
	}
	
	@Test
    public void testNotEqualsWithGroup() {
    	Position immutable = PositionBoTest.getPosition("TST-PSTN");
    	PositionBo bo = PositionBo.from(immutable);
    	
    	PositionBo positionBo = new PositionBo();
    	positionBo.setEffectiveLocalDate(new LocalDate());
    	bo.getDepartmentList().get(0).setOwner(positionBo);
    	bo.getDutyList().get(0).setOwner(positionBo);
    	bo.getFlagList().get(0).setOwner(positionBo);
//    	bo.getFundingList().get(0).setOwner(positionBo);
    	bo.getQualificationList().get(0).setOwner(positionBo);
    	
    	ClassificationBo classificationBo = new ClassificationBo();
    	classificationBo.setEffectiveLocalDate(new LocalDate());
    	bo.getRequiredQualList().get(0).setOwner(classificationBo);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PositionBo.to(bo));
    }

    public static Position getPosition(String Position) {
        Position position = testPositionBos.get(Position);
        return position;
    }

}