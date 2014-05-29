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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.departmentaffiliation.DepartmentAffiliation;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.departmentAffiliation.DepartmentAffiliationBoTest;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;
import org.kuali.kpme.pm.api.classification.qual.ClassificationQualification;
import org.kuali.kpme.pm.api.position.Position;
import org.kuali.kpme.pm.api.position.PositionDuty;
import org.kuali.kpme.pm.api.position.PositionQualification;
import org.kuali.kpme.pm.api.position.PstnFlag;
import org.kuali.kpme.pm.api.positiondepartment.PositionDepartment;
import org.kuali.kpme.pm.classification.qual.ClassificationQualificationBoTest;

public class PositionDataBoTest {

	private static Map<String, Position> testPositionBos;
	public static Position.Builder positionBuilder = Position.Builder.create("1", "ISU-IA");

	
	private static Map<String, PositionDepartment> testPositionDepartmentBos;
	public static PositionDepartment.Builder positionDeptBuilder = PositionDepartment.Builder.create();
	
	private static Map<String, PstnFlag> testPstnFlagBos;
	public static PstnFlag.Builder pstnFlagBuilder = PstnFlag.Builder.create();
	
	private static Map<String, PositionDuty> testPositionDutyBos;
	public static PositionDuty.Builder positionDutyBuilder = PositionDuty.Builder
			.create();

	private static Map<String, PositionQualification> testPositionQualificationBos;
	public static PositionQualification.Builder positionQualificationBuilder = PositionQualification.Builder
			.create();

	static LocalDate currentTime = new LocalDate();

	static{
		
		testPositionBos = new HashMap<String, Position>();

		positionBuilder.setActive(true);
		positionBuilder.setBenefitsEligible("N");
		positionBuilder.setCategory("");
		positionBuilder.setClassificationTitle("");
		positionBuilder.setContract("");
		positionBuilder.setContractType("");
		positionBuilder.setCreateTime(DateTime.now());
		positionBuilder.setDescription("");
		positionBuilder.setEffectiveLocalDate(currentTime);
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
		
		List<ClassificationQualification.Builder> classificationQualificationList = new ArrayList<ClassificationQualification.Builder>();
		classificationQualificationList.add(ClassificationQualification.Builder.create(ClassificationQualificationBoTest.getClassificationQualificationBo("TST-CLASSFCTNQLFCTN")));
		positionBuilder.setRequiredQualList(classificationQualificationList);
						
		testPositionBos.put("TST-PSTN", positionBuilder.build());
		
		testPositionDepartmentBos = new HashMap<String, PositionDepartment>();
		
		positionDeptBuilder.setHrPositionId("");
		positionDeptBuilder.setPmPositionDeptId("TST-PSTNDEPT");
		positionDeptBuilder.setVersionNumber(1L);
		positionDeptBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		positionDeptBuilder.setEffectiveLocalDateOfOwner(currentTime);
		positionDeptBuilder.setDeptAfflObj(DepartmentAffiliation.Builder.create(DepartmentAffiliationBoTest.getDepartmentAffiliation("TST-DEPTAFFL")));
		positionDeptBuilder.setGroupKeyCode("ISU-IA");
		positionDeptBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		positionDeptBuilder.setDepartment("IA-DEPT");
		positionDeptBuilder.setDeptAffl("DEPT_AFFL");
//		positionDeptBuilder.setOwner(Position.Builder.create(getPosition("TST-PSTN")));
		
		testPositionDepartmentBos.put(positionDeptBuilder.getPmPositionDeptId(), positionDeptBuilder.build());
		
		testPstnFlagBos = new HashMap<String, PstnFlag>();
		
		List<String> names = new ArrayList<String>();
		names.add("Name1");
		pstnFlagBuilder.setCategory("CAT");
		pstnFlagBuilder.setHrPositionId("");
		pstnFlagBuilder.setNames(names);
		pstnFlagBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
//		pstnFlagBuilder.setOwner(Position.Builder.create(getPosition("TST-PSTN")));
		pstnFlagBuilder.setPmFlagId("TST-PSTNFLAG");
		pstnFlagBuilder.setVersionNumber(1L);
		pstnFlagBuilder.setEffectiveLocalDateOfOwner(currentTime);
		testPstnFlagBos.put(pstnFlagBuilder.getPmFlagId(),pstnFlagBuilder.build());
				

		testPositionDutyBos = new HashMap<String, PositionDuty>();
		
		positionDutyBuilder.setName("");
        positionDutyBuilder.setDescription("");
        positionDutyBuilder.setPercentage(new BigDecimal(100.0));
        positionDutyBuilder.setPmDutyId("TST-PSTNDUTY");
        positionDutyBuilder.setHrPositionId("");
//        positionDutyBuilder.setOwner(Position.Builder.create(getPosition("TST-PSTN")));
        positionDutyBuilder.setVersionNumber(1l);
        positionDutyBuilder.setEffectiveLocalDateOfOwner(currentTime);
        positionDutyBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		
		testPositionDutyBos.put(positionDutyBuilder.getPmDutyId(), positionDutyBuilder.build());
		
		testPositionQualificationBos = new HashMap<String, PositionQualification>();
		
		positionQualificationBuilder.setQualificationValue("");
		positionQualificationBuilder.setQualificationType("");
		positionQualificationBuilder.setPmQualificationId("TST-PSTNQLFCTN");
		positionQualificationBuilder.setTypeValue("");
		positionQualificationBuilder.setQualifier("");
		positionQualificationBuilder.setHrPositionId("");
//		positionQualificationBuilder.setOwner(Position.Builder.create(getPosition("TST-PSTN")));
		positionQualificationBuilder.setVersionNumber(1l);
		positionQualificationBuilder.setEffectiveLocalDateOfOwner(currentTime);
		positionQualificationBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		
		testPositionQualificationBos.put(positionQualificationBuilder.getPmQualificationId(), positionQualificationBuilder.build());
		
	}

	public static Position getPosition(String Position) {
        Position position = testPositionBos.get(Position);
        return position;
    }
	
    public static PositionDepartment getPositionDepartment(String positionDept) {
        return testPositionDepartmentBos.get(positionDept);
    }
	
    public static PstnFlag getPstnFlag(String pstnFlag) {
        PstnFlag pstnFlag1 = testPstnFlagBos.get(pstnFlag);
        return pstnFlag1;
   }
    
    public static PositionDuty getPositionDutyBo(String positionType) {
        return testPositionDutyBos.get(positionType);
    }
    
    public static PositionQualification getPositionQualificationBo(String positionQlfctn) {
        return testPositionQualificationBos.get(positionQlfctn);
    }
}
