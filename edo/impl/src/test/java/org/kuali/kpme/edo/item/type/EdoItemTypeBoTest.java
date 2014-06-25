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
package org.kuali.kpme.edo.item.type;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.edo.api.item.type.EdoItemType;

public class EdoItemTypeBoTest {
	private static Map<String, EdoItemType> testEdoItemTypeBos;
	public static EdoItemType.Builder edoItemTypeBoBuilder = EdoItemType.Builder.create();
	
	static {
		testEdoItemTypeBos = new HashMap<String, EdoItemType>();
		edoItemTypeBoBuilder.setEdoItemTypeID("EDO_ITEMTYPE_ID_0001");
		edoItemTypeBoBuilder.setItemTypeName("Testing Immutable EdoItemType");
		edoItemTypeBoBuilder.setItemTypeDescription("Testing Immutable EdoItemType Description");
		edoItemTypeBoBuilder.setItemTypeInstructions("");
		edoItemTypeBoBuilder.setItemTypeExtAvailable(false);
		edoItemTypeBoBuilder.setUserPrincipalId("admin");
		edoItemTypeBoBuilder.setVersionNumber(1L);
		edoItemTypeBoBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		edoItemTypeBoBuilder.setActive(true);
		edoItemTypeBoBuilder.setId(edoItemTypeBoBuilder.getEdoItemTypeID());
		edoItemTypeBoBuilder.setEffectiveLocalDate(new LocalDate(2014, 3, 1));
		edoItemTypeBoBuilder.setCreateTime(DateTime.now());

		testEdoItemTypeBos.put(edoItemTypeBoBuilder.getEdoItemTypeID(), edoItemTypeBoBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	EdoItemType immutable = EdoItemTypeBoTest.getEdoItemType("EDO_ITEMTYPE_ID_0001");
    	EdoItemTypeBo bo = EdoItemTypeBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoItemTypeBo.to(bo));
    }

    public static EdoItemType getEdoItemType(String edoItemType) {
        return testEdoItemTypeBos.get(edoItemType);
    }
   
}
