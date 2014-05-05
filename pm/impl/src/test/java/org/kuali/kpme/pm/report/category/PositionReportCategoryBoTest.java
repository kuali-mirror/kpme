package org.kuali.kpme.pm.report.category;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;
import org.kuali.kpme.pm.api.positionreportcat.PositionReportCategory;
import org.kuali.kpme.pm.positionreportcat.PositionReportCategoryBo;

public class PositionReportCategoryBoTest {
	private static Map<String, PositionReportCategory> testPositionReportCategoryBos;
	public static PositionReportCategory.Builder PositionReportCategoryBuilder = PositionReportCategory.Builder.create("ISU-IA", "PSTNRPTCAT", "PSTNRPTTYP");
	
	static {
		testPositionReportCategoryBos = new HashMap<String, PositionReportCategory>();
		PositionReportCategoryBuilder.setGroupKeyCode("ISU-IA");
		PositionReportCategoryBuilder.setDescription("Testing Immutable PositionReportCategory");
		PositionReportCategoryBuilder.setPositionReportCat("TST-PSTNRPTCAT");
		PositionReportCategoryBuilder.setUserPrincipalId("admin");
		
		PositionReportCategoryBuilder.setPmPositionReportCatId("KPME_TEST_0001");
		PositionReportCategoryBuilder.setVersionNumber(1L);
		PositionReportCategoryBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		PositionReportCategoryBuilder.setActive(true);
		PositionReportCategoryBuilder.setId(PositionReportCategoryBuilder.getPmPositionReportCatId());
		PositionReportCategoryBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
		PositionReportCategoryBuilder.setCreateTime(DateTime.now());
		
		// Set GroupKeycode Object
		PositionReportCategoryBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		testPositionReportCategoryBos.put(PositionReportCategoryBuilder.getPositionReportCat(), PositionReportCategoryBuilder.build());
	}
	
	@Test
    public void testNotEqualsWithGroup() {
		PositionReportCategory immutable = PositionReportCategoryBoTest.getPositionReportCategory("TST-PSTNRPTCAT");
		PositionReportCategoryBo bo = PositionReportCategoryBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PositionReportCategoryBo.to(bo));
    }

    public static PositionReportCategory getPositionReportCategory(String PositionReportCategory) {
        return testPositionReportCategoryBos.get(PositionReportCategory);
    }
}
