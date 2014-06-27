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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;
import org.kuali.kpme.core.kfs.coa.businessobject.Account;
import org.kuali.kpme.pm.api.classification.qual.ClassificationQualification;
import org.kuali.kpme.pm.api.position.Position;
import org.kuali.kpme.pm.api.position.PositionDuty;
import org.kuali.kpme.pm.api.position.PositionQualification;
import org.kuali.kpme.pm.api.position.PstnFlag;
import org.kuali.kpme.pm.api.position.funding.PositionFunding;
import org.kuali.kpme.pm.api.positiondepartment.PositionDepartment;
import org.kuali.kpme.pm.api.positionresponsibility.PositionResponsibility;
import org.kuali.kpme.pm.classification.ClassificationBo;
import org.kuali.kpme.pm.classification.qual.ClassificationQualificationBoTest;
import org.kuali.kpme.pm.position.funding.PositionFundingBoTest;
import org.kuali.kpme.pm.positionresponsibility.PositionResponsibilityBoTest;
import org.kuali.rice.krad.service.BusinessObjectService;

public class PositionBoTest {
	private static Map<String, Position> testPositionBos;
	public static Position.Builder builder = Position.Builder.create("1", "ISU-IA");
	
	private BusinessObjectService mockBusinessObjectService;

	static {
		testPositionBos = new HashMap<String, Position>();

		builder.setActive(true);
		builder.setBenefitsEligible("N");
		builder.setCategory("category");
		builder.setClassificationTitle("classTitle");
		builder.setContract("contract");
		builder.setContractType("contractType");
		builder.setCreateTime(DateTime.now());
		builder.setDescription("desc");
		builder.setGroupKeyCode("ISU-IA");
		builder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		builder.setHrPositionId("KPME_TEST_00001");
		builder.setId(builder.getHrPositionId());
		builder.setLeaveEligible("leaveEligible");
		builder.setMaxPoolHeadCount(0);
		builder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		builder.setPayGrade("XH");
		builder.setPayStep("YY");
		builder.setPositionNumber("1");
        builder.setPositionClass("positionClass");
        builder.setAppointmentType("appt");
        builder.setReportsToWorkingTitle("rptToWorkTitle");
        builder.setPrimaryDepartment("dept1");
        builder.setProcess("process");


		builder.setPmPositionClassId("KPME_TEST_2000");
		
		List<PositionResponsibility.Builder> positionResponsilityList = new ArrayList<PositionResponsibility.Builder>();
		PositionResponsibility.Builder responsibilityBuilder = PositionResponsibility.Builder.create(PositionResponsibilityBoTest.getPositionResponsibility("TST-PSTNRESPOPT"));
		responsibilityBuilder.setHrPositionId(builder.getHrPositionId());
		positionResponsilityList.add(responsibilityBuilder);
		builder.setPositionResponsibilityList(positionResponsilityList);
		
		
		List<PositionDepartment.Builder> positionDeptList = new ArrayList<PositionDepartment.Builder>();
		PositionDepartment.Builder deptBuilder = PositionDepartment.Builder.create(PositionDataBoTest.getPositionDepartment("TST-PSTNDEPT"));
		deptBuilder.setHrPositionId(builder.getHrPositionId());
		positionDeptList.add(deptBuilder);
		builder.setDepartmentList(positionDeptList);
		
		List<PstnFlag.Builder> pstnFlagList = new ArrayList<PstnFlag.Builder>();
		PstnFlag.Builder flagBuilder = PstnFlag.Builder.create(PstnFlagBoTest.getPstnFlag("TST-PSTNFLAG"));
		flagBuilder.setHrPositionId(builder.getHrPositionId());
		pstnFlagList.add(flagBuilder);
		builder.setFlagList(pstnFlagList);
		
		List<PositionDuty.Builder> positionDutyList = new ArrayList<PositionDuty.Builder>();
		PositionDuty.Builder dutyBuilder = PositionDuty.Builder.create(PositionDutyBoTest.getPositionDutyBo("TST-PSTNDUTY"));
		dutyBuilder.setHrPositionId(builder.getHrPositionId());
		positionDutyList.add(dutyBuilder);
		builder.setDutyList(positionDutyList);
		
		List<PositionFunding.Builder> positionFundingList = new ArrayList<PositionFunding.Builder>();
		PositionFunding.Builder fundingBuilder = PositionFunding.Builder.create(PositionFundingBoTest.getPositionFunding("9999"));
		fundingBuilder.setHrPositionId(builder.getHrPositionId());
		fundingBuilder.setAccount("KPME_TEST_ACCOUNT");
		positionFundingList.add(fundingBuilder);
		builder.setFundingList(positionFundingList);

		List<PositionQualification.Builder> positionQualificationList = new ArrayList<PositionQualification.Builder>();
		PositionQualification.Builder qualificationBuilder = PositionQualification.Builder.create(PositionQualificationBoTest.getPositionQualificationBo("TST-PSTNQLFCTN"));
		qualificationBuilder.setHrPositionId(builder.getHrPositionId());
		positionQualificationList.add(qualificationBuilder);
		builder.setQualificationList(positionQualificationList);
		
		List<ClassificationQualification.Builder> classificationQualificationList = new ArrayList<ClassificationQualification.Builder>();
		ClassificationQualification.Builder classQualBuilder = ClassificationQualification.Builder.create(ClassificationQualificationBoTest.getClassificationQualificationBo("TST-CLASSFCTNQLFCTN"));
		classQualBuilder.setPmPositionClassId("KPME_TEST_2000");
		classificationQualificationList.add(classQualBuilder);
		builder.setRequiredQualList(classificationQualificationList);
						
		testPositionBos.put("TST-PSTN", builder.build());
	}
	
	@Test
    public void testNotEqualsWithGroup() {
    	Position immutable = PositionBoTest.getPosition("TST-PSTN");
    	PositionBo bo = PositionBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
       
        // this is simply to prevent invocations of refresh reference 
    	ClassificationBo classificationBo = new ClassificationBo();
    	bo.getRequiredQualList().get(0).setOwner(classificationBo);
    	
    	//bo.getFundingList().get(0).setBusinessObjectService(mockBusinessObjectService);
        Position im2 = PositionBo.to(bo);
        PositionBo bo2 = PositionBo.from(im2);
        
        // this is simply to prevent invocations of refresh reference 
        bo2.getRequiredQualList().get(0).setOwner(classificationBo);
        
        //bo2.getFundingList().get(0).setBusinessObjectService(mockBusinessObjectService);
        Position im3 = PositionBo.to(bo2);
        
        Assert.assertEquals(im2, im3);
    }

    public static Position getPosition(String Position) {
        Position position = testPositionBos.get(Position);
        return position;
    }
    
    @Before
    public void setup() throws Exception {
        
        Account account = new Account();
    	account.setAccountNumber("KPME_TEST_ACCOUNT");
    	account.setChartOfAccountsCode("MC");
    	account.setActive(true);
    	
    	Map<String, String> fields = new HashMap<String, String>();
		fields.put("accountNumber", "KPME_TEST_ACCOUNT");
		fields.put("active", "true");
    	
    	mockBusinessObjectService = mock(BusinessObjectService.class);
        {
            when(mockBusinessObjectService.findByPrimaryKey(Account.class, fields)).thenReturn(account);
        }
    }

}