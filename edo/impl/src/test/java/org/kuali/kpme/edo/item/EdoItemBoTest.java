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
package org.kuali.kpme.edo.item;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.edo.api.checklist.EdoChecklistItem;
import org.kuali.kpme.edo.api.item.EdoItem;
import org.kuali.kpme.edo.checklist.EdoChecklistItemBo;
import org.kuali.kpme.edo.checklist.EdoChecklistItemBoTest;

public class EdoItemBoTest {
	private static Map<String, EdoItem> testEdoItemBos;
	public static EdoItem.Builder edoEdoItemBuilder = EdoItem.Builder.create();
	
	static {
		testEdoItemBos = new HashMap<String, EdoItem>();
		edoEdoItemBuilder.setEdoItemId("EDO_ITEM_ID_0001");
		edoEdoItemBuilder.setEdoItemTypeId("EDO_ITEM_TYPE_ID_0001");
		edoEdoItemBuilder.setEdoChecklistItemId("EDO_CHECKLIST_ID_0001");
		edoEdoItemBuilder.setEdoDossierId("EDO_DOSSIER_ID_0001");
		edoEdoItemBuilder.setFileName("myResearch");
		edoEdoItemBuilder.setFileLocation("");
		edoEdoItemBuilder.setNotes("");
		edoEdoItemBuilder.setRouted(true);
		edoEdoItemBuilder.setContentType("");
		edoEdoItemBuilder.setRowIndex(1);
		edoEdoItemBuilder.setEdoReviewLayerDefId("");
		edoEdoItemBuilder.setFileDescription("");
		edoEdoItemBuilder.setAction("Delete File");
		edoEdoItemBuilder.setUserPrincipalId("admin");
		edoEdoItemBuilder.setVersionNumber(1L);
		edoEdoItemBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		edoEdoItemBuilder.setActive(true);
		edoEdoItemBuilder.setActionFullDateTime(new DateTime(2014, 3, 1, 0, 0));
		
		testEdoItemBos.put(edoEdoItemBuilder.getEdoItemId(), edoEdoItemBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	EdoItem immutable = EdoItemBoTest.getEdoItem("EDO_ITEM_ID_0001");
    	EdoItemBo bo = EdoItemBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoItemBo.to(bo));
    }

    public static EdoItem getEdoItem(String edoItemID) {
        return testEdoItemBos.get(edoItemID);
    }
}
