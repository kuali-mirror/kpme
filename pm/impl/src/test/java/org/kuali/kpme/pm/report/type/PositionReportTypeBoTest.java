package org.kuali.kpme.pm.report.type;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;
import org.kuali.kpme.pm.api.positionreporttype.PositionReportType;
import org.kuali.kpme.pm.positionreporttype.PositionReportTypeBo;

public class PositionReportTypeBoTest {
	private static Map<String, PositionReportType> testPositionReportTypeBos;
	public static PositionReportType.Builder PositionReportTypeBuilder = PositionReportType.Builder.create("ISU-IA", "test1");
	
	static {
		testPositionReportTypeBos = new HashMap<String, PositionReportType>();
		PositionReportTypeBuilder.setGroupKeyCode("ISU-IA");
		PositionReportTypeBuilder.setDescription("Testing Immutable PositionReportType");
		PositionReportTypeBuilder.setPositionReportType("TST-PSTNRPTTYP");
		PositionReportTypeBuilder.setUserPrincipalId("admin");
		
		PositionReportTypeBuilder.setPmPositionReportTypeId("KPME_TEST_0001");
		PositionReportTypeBuilder.setVersionNumber(1L);
		PositionReportTypeBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		PositionReportTypeBuilder.setActive(true);
		PositionReportTypeBuilder.setId(PositionReportTypeBuilder.getPmPositionReportTypeId());
		PositionReportTypeBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
		PositionReportTypeBuilder.setCreateTime(DateTime.now());
		
		// Set GroupKeycode Object
		PositionReportTypeBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		testPositionReportTypeBos.put(PositionReportTypeBuilder.getPositionReportType(), PositionReportTypeBuilder.build());
	}
	
	@Test
    public void testNotEqualsWithGroup() {
		PositionReportType immutable = PositionReportTypeBoTest.getPositionReportType("TST-PSTNRPTTYP");
		PositionReportTypeBo bo = PositionReportTypeBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PositionReportTypeBo.to(bo));
    }

    public static PositionReportType getPositionReportType(String positionReportType) {
        return testPositionReportTypeBos.get(positionReportType);
    }
}
