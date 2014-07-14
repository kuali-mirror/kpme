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

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.edo.api.checklist.EdoChecklistSection;

public class EdoChecklistSectionBoTest {
	private static Map<String, EdoChecklistSection> testEdoChecklistSectionBos;
	public static EdoChecklistSection.Builder edoEdoChecklistSectionBuilder = EdoChecklistSection.Builder.create();
	
	static {
		testEdoChecklistSectionBos = new HashMap<String, EdoChecklistSection>();
		edoEdoChecklistSectionBuilder.setChecklistSectionName("Checklist Section 1");
		edoEdoChecklistSectionBuilder.setDescription("Testing Immutable EdoChecklistSection");
		edoEdoChecklistSectionBuilder.setChecklistSectionOrdinal(3);
		edoEdoChecklistSectionBuilder.setUserPrincipalId("admin");
		
		edoEdoChecklistSectionBuilder.setEdoChecklistSectionId("EDO_CHECKLIST_SECTION_ID_0001");
		edoEdoChecklistSectionBuilder.setEdoChecklistId("EDO_CHECKLIST_ID_0001");
		
		edoEdoChecklistSectionBuilder.setVersionNumber(1L);
		edoEdoChecklistSectionBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		edoEdoChecklistSectionBuilder.setActive(true);
		edoEdoChecklistSectionBuilder.setId(edoEdoChecklistSectionBuilder.getEdoChecklistSectionId());
		edoEdoChecklistSectionBuilder.setEffectiveLocalDate(new LocalDate(2014, 3, 1));
		edoEdoChecklistSectionBuilder.setCreateTime(DateTime.now());
		
		testEdoChecklistSectionBos.put(edoEdoChecklistSectionBuilder.getEdoChecklistSectionId(), edoEdoChecklistSectionBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	EdoChecklistSection immutable = EdoChecklistSectionBoTest.getEdoChecklistSection("EDO_CHECKLIST_SECTION_ID_0001");
    	EdoChecklistSectionBo bo = EdoChecklistSectionBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoChecklistSectionBo.to(bo));
    }

    public static EdoChecklistSection getEdoChecklistSection(String edoChecklistSection) {
        return testEdoChecklistSectionBos.get(edoChecklistSection);
    }
}
