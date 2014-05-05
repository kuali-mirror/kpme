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
	public static PositionReportSubCategory.Builder PositionReportSubCategoryBuilder = PositionReportSubCategory.Builder.create("ISU-IA", "PSTNRPTCAT", "PSTNRPTTYP");
	
	static {
		testPositionReportSubCategoryBos = new HashMap<String, PositionReportSubCategory>();
		PositionReportSubCategoryBuilder.setGroupKeyCode("ISU-IA");
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
		
		// Set GroupKeycode Object
		PositionReportSubCategoryBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
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
