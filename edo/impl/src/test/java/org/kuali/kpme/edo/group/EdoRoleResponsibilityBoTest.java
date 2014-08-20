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
package org.kuali.kpme.edo.group;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.edo.api.group.EdoRoleResponsibility;

public class EdoRoleResponsibilityBoTest {
	private static Map<String, EdoRoleResponsibility> testEdoRoleResponsibilityBos;
	public static EdoRoleResponsibility.Builder edoRoleResponsibility = EdoRoleResponsibility.Builder.create();
	
	static {
		testEdoRoleResponsibilityBos = new HashMap<String, EdoRoleResponsibility>();
		edoRoleResponsibility.setEdoKimRoleResponsibilityId("1000");
		edoRoleResponsibility.setKimRoleName("KHR Institution Admin");
		edoRoleResponsibility.setKimResponsibilityName("Approve");
		edoRoleResponsibility.setKimActionTypeCode("A");
		edoRoleResponsibility.setKimActionPolicyCode("");
		edoRoleResponsibility.setKimPriority(1);
		edoRoleResponsibility.setKimForceAction(true);
		edoRoleResponsibility.setVersionNumber(1L);
		edoRoleResponsibility.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		
		testEdoRoleResponsibilityBos.put(edoRoleResponsibility.getEdoKimRoleResponsibilityId(), edoRoleResponsibility.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	EdoRoleResponsibility immutable = EdoRoleResponsibilityBoTest.getEdoKimRoleResponsibility("1000");
    	EdoRoleResponsibilityBo bo = EdoRoleResponsibilityBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoRoleResponsibilityBo.to(bo));
    }

    public static EdoRoleResponsibility getEdoKimRoleResponsibility(String edoKimRoleResponsibilityId) {
        return testEdoRoleResponsibilityBos.get(edoKimRoleResponsibilityId);
    }
}
