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
package org.kuali.kpme.core.departmentAffiliation;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.departmentaffiliation.DepartmentAffiliation;
import org.kuali.kpme.core.departmentaffiliation.DepartmentAffiliationBo;
public class DepartmentAffiliationBoTest {

	    private static Map<String, DepartmentAffiliation> testDepartmentAffiliationBos;
	    public static DepartmentAffiliation.Builder deptAfflBuilder = DepartmentAffiliation.Builder.create();
	    static {
	    	
	    	testDepartmentAffiliationBos = new HashMap<String, DepartmentAffiliation>();
	        deptAfflBuilder.setDeptAfflType("TST-DEPTAFFL");
	        deptAfflBuilder.setHrDeptAfflId("KPME-TEST-0001");
	        deptAfflBuilder.setActive(true);
	        
	        deptAfflBuilder.setVersionNumber(1L);
	        deptAfflBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
	        deptAfflBuilder.setUserPrincipalId("admin");
	        deptAfflBuilder.setId(deptAfflBuilder.getHrDeptAfflId());
			deptAfflBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
			deptAfflBuilder.setCreateTime(DateTime.now());
	        
	        testDepartmentAffiliationBos.put(deptAfflBuilder.getDeptAfflType(), deptAfflBuilder.build());
	        
	    }

	    @Test
	    public void testNotEqualsWithGroup() {
	        DepartmentAffiliation immutable = DepartmentAffiliationBoTest.getDepartmentAffiliation("TST-DEPTAFFL");
	        DepartmentAffiliationBo bo = DepartmentAffiliationBo.from(immutable);
	        Assert.assertFalse(bo.equals(immutable));
	        Assert.assertFalse(immutable.equals(bo));
	        Assert.assertEquals(immutable, DepartmentAffiliationBo.to(bo));
	    }

	    public static DepartmentAffiliation getDepartmentAffiliation(String deptAffiliation) {
	        return testDepartmentAffiliationBos.get(deptAffiliation);
	    }
}