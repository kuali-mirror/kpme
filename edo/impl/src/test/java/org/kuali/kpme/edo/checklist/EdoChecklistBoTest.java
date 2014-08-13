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
package org.kuali.kpme.edo.checklist;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;
import org.kuali.kpme.edo.api.checklist.EdoChecklist;

public class EdoChecklistBoTest {
	private static Map<String, EdoChecklist> testEdoChecklistBos;
	public static EdoChecklist.Builder edoEdoChecklistBuilder = EdoChecklist.Builder.create();
	
	static {
		testEdoChecklistBos = new HashMap<String, EdoChecklist>();
		edoEdoChecklistBuilder.setEdoChecklistId("1000");
		edoEdoChecklistBuilder.setDescription("Testing Immutable EdoChecklist");
        edoEdoChecklistBuilder.setChecklistSections(Collections.singletonList(EdoChecklistSectionBoTest.edoEdoChecklistSectionBuilder));
		edoEdoChecklistBuilder.setDepartmentId("DEFAULT");
		edoEdoChecklistBuilder.setDossierTypeCode("AB");
		edoEdoChecklistBuilder.setGroupKeyCode("ISU-IA");
		edoEdoChecklistBuilder.setUserPrincipalId("admin");
		edoEdoChecklistBuilder.setVersionNumber(1L);
		edoEdoChecklistBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		edoEdoChecklistBuilder.setActive(true);
		edoEdoChecklistBuilder.setId(edoEdoChecklistBuilder.getEdoChecklistId());
		edoEdoChecklistBuilder.setEffectiveLocalDate(new LocalDate(2014, 3, 1));
		edoEdoChecklistBuilder.setCreateTime(DateTime.now());
		
		// Set GroupKey Object
		edoEdoChecklistBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		testEdoChecklistBos.put(edoEdoChecklistBuilder.getEdoChecklistId(), edoEdoChecklistBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	EdoChecklist immutable = EdoChecklistBoTest.getEdoChecklist("1000");
    	EdoChecklistBo bo = EdoChecklistBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoChecklistBo.to(bo));
    }

    public static EdoChecklist getEdoChecklist(String edoChecklist) {
        return testEdoChecklistBos.get(edoChecklist);
    }
   
}
