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

import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.edo.api.checklist.EdoChecklistItem;

public class EdoChecklistItemBoTest {
	private static Map<String, EdoChecklistItem> testEdoChecklistItemBos;
	public static EdoChecklistItem.Builder edoChecklistItemBuilder = EdoChecklistItem.Builder.create();
	
	static {
		testEdoChecklistItemBos = new HashMap<String, EdoChecklistItem>();
		edoChecklistItemBuilder.setChecklistItemName("Checklist Item 1");
		edoChecklistItemBuilder.setItemDescription("Testing Immutable EdoChecklistItem");
		edoChecklistItemBuilder.setChecklistItemOrdinal(1);
		edoChecklistItemBuilder.setEdoChecklistItemId("1000");
		edoChecklistItemBuilder.setEdoChecklistSectionId("1000");
		edoChecklistItemBuilder.setVersionNumber(1L);
		edoChecklistItemBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		
		testEdoChecklistItemBos.put(edoChecklistItemBuilder.getEdoChecklistItemId(), edoChecklistItemBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	EdoChecklistItem immutable = EdoChecklistItemBoTest.getEdoChecklistItem("1000");
    	EdoChecklistItemBo bo = EdoChecklistItemBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoChecklistItemBo.to(bo));
    }

    public static EdoChecklistItem getEdoChecklistItem(String edoChecklistItem) {
        return testEdoChecklistItemBos.get(edoChecklistItem);
    }
}
