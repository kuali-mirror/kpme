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
package org.kuali.kpme.pm.report.subcategory;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;
import org.kuali.kpme.pm.api.positionreportsubcat.PositionReportSubCategory;
import org.kuali.kpme.pm.positionreportsubcat.PositionReportSubCategoryBo;

public class PositionReportSubCategoryBoTest {
	private static Map<String, PositionReportSubCategory> testPositionReportSubCategoryBos;
	public static PositionReportSubCategory.Builder PositionReportSubCategoryBuilder = PositionReportSubCategory.Builder.create( "PSTNRPTCAT", "PSTNRPTTYP");
	
	static {
		testPositionReportSubCategoryBos = new HashMap<String, PositionReportSubCategory>();
		PositionReportSubCategoryBuilder.setDescription("Testing Immutable PositionReportSubCategory");
		PositionReportSubCategoryBuilder.setPositionReportCat("TST-PSTNRPTCAT");
		PositionReportSubCategoryBuilder.setUserPrincipalId("admin");
		
		PositionReportSubCategoryBuilder.setPmPositionReportSubCatId("KPME_TEST_0001");
		PositionReportSubCategoryBuilder.setVersionNumber(1L);
		PositionReportSubCategoryBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		PositionReportSubCategoryBuilder.setActive(true);
		PositionReportSubCategoryBuilder.setId(PositionReportSubCategoryBuilder.getPmPositionReportSubCatId());
		PositionReportSubCategoryBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
		PositionReportSubCategoryBuilder.setCreateTime(DateTime.now());
		
		testPositionReportSubCategoryBos.put(PositionReportSubCategoryBuilder.getPositionReportCat(), PositionReportSubCategoryBuilder.build());
	}
	
	@Test
    public void testNotEqualsWithGroup() {
		PositionReportSubCategory immutable = PositionReportSubCategoryBoTest.getPositionReportSubCategory("TST-PSTNRPTCAT");
		PositionReportSubCategoryBo bo = PositionReportSubCategoryBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PositionReportSubCategoryBo.to(bo));
    }

    public static PositionReportSubCategory getPositionReportSubCategory(String positionReportSubCategory) {
        return testPositionReportSubCategoryBos.get(positionReportSubCategory);
    }
}
