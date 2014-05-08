package org.kuali.kpme.pm.report.group;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;
import org.kuali.kpme.pm.api.pstnrptgrpsubcat.PositionReportGroupSubCategory;
import org.kuali.kpme.pm.pstnrptgrpsubcat.PositionReportGroupSubCategoryBo;

public class PositionReportGroupSubCategoryBoTest {
	
	private static Map<String, PositionReportGroupSubCategory> testPositionReportGroupSubCatBos;
	public static PositionReportGroupSubCategory.Builder positionReportGroupSubCatBuilder = PositionReportGroupSubCategory.Builder.create("TST-PSTNRPTGRP","ISU-IA", "TST-PSTNRPTGRPSUBCAT");

	static {
		testPositionReportGroupSubCatBos = new HashMap<String, PositionReportGroupSubCategory>();
		positionReportGroupSubCatBuilder.setGroupKeyCode("ISU-IA");
		positionReportGroupSubCatBuilder.setDescription("Testing Immutable PositionReportGroup");
		positionReportGroupSubCatBuilder.setPositionReportGroup("TST-PSTNRPTGRP");
		positionReportGroupSubCatBuilder.setPositionReportSubCat("TST-PSTNRPTGRPSUBCAT");
		positionReportGroupSubCatBuilder.setUserPrincipalId("admin");
		
		positionReportGroupSubCatBuilder.setPmPstnRptGrpSubCatId("KPME_TEST_0001");
		positionReportGroupSubCatBuilder.setVersionNumber(1L);
		positionReportGroupSubCatBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		positionReportGroupSubCatBuilder.setActive(true);
		positionReportGroupSubCatBuilder.setId(positionReportGroupSubCatBuilder.getPmPstnRptGrpSubCatId());
		positionReportGroupSubCatBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
		positionReportGroupSubCatBuilder.setCreateTime(DateTime.now());
		
		// Set GroupKeycode Object
		positionReportGroupSubCatBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		testPositionReportGroupSubCatBos.put(positionReportGroupSubCatBuilder.getPositionReportSubCat(), positionReportGroupSubCatBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	PositionReportGroupSubCategory immutable = PositionReportGroupSubCategoryBoTest.getPositionReportGroupSubCategory("TST-PSTNRPTGRPSUBCAT");
    	PositionReportGroupSubCategoryBo bo = PositionReportGroupSubCategoryBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PositionReportGroupSubCategoryBo.to(bo));
    }

    public static PositionReportGroupSubCategory getPositionReportGroupSubCategory(String positionReportGroupSubCat) {
        return testPositionReportGroupSubCatBos.get(positionReportGroupSubCat);
    }

}
