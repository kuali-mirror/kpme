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

package org.kuali.kpme.pm.positiondepartment;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.departmentaffiliation.DepartmentAffiliation;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.departmentAffiliation.DepartmentAffiliationBoTest;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;
import org.kuali.kpme.pm.api.position.Position;
import org.kuali.kpme.pm.api.positiondepartment.PositionDepartment;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.kpme.pm.position.PositionBoTest;

public class PositionDepartmentBoTest {

	private static Map<String, PositionDepartment> testPositionDepartmentBos;
	public static PositionDepartment.Builder positionDeptBuilder = PositionDepartment.Builder.create();
	static LocalDate currentTime = new LocalDate();
	
	static {
		testPositionDepartmentBos = new HashMap<String, PositionDepartment>();
		
		positionDeptBuilder.setHrPositionId("");
		positionDeptBuilder.setPmPositionDeptId("TST-PSTNDEPT");
		positionDeptBuilder.setVersionNumber(1L);
		positionDeptBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		
		positionDeptBuilder.setDeptAfflObj(DepartmentAffiliation.Builder.create(DepartmentAffiliationBoTest.getDepartmentAffiliation("TST-DEPTAFFL")));
		positionDeptBuilder.setGroupKeyCode("ISU-IA");
		positionDeptBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		positionDeptBuilder.setDepartment("IA-DEPT");
		positionDeptBuilder.setDeptAffl("DEPT_AFFL");
//		positionDeptBuilder.setOwner(Position.Builder.create(PositionBoTest.getPosition("TST-PSTN")));
		positionDeptBuilder.setEffectiveLocalDateOfOwner(currentTime);
		
		
		testPositionDepartmentBos.put(positionDeptBuilder.getPmPositionDeptId(), positionDeptBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	PositionDepartment immutable = PositionDepartmentBoTest.getPositionDepartment("TST-PSTNDEPT");
    	PositionDepartmentBo bo = PositionDepartmentBo.from(immutable);
    	PositionBo positionBo = new PositionBo();
		positionBo.setEffectiveLocalDate(currentTime);
		bo.setOwner(positionBo);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PositionDepartmentBo.to(bo));
    }

    public static PositionDepartment getPositionDepartment(String positionDept) {
        return testPositionDepartmentBos.get(positionDept);
    }
	
}
