package org.kuali.kpme.core.position;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.position.PositionBase;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;

public class PositionBaseBoTest {
	
	private static Map<String, PositionBase> testPositionBaseBos;
	public static PositionBase.Builder PositionBaseBuilder = PositionBase.Builder.create("TST-PSTNNUM");
	private static final LocalDate currentTime = new LocalDate();
	
	static {
		testPositionBaseBos = new HashMap<String, PositionBase>();
		PositionBaseBuilder.setActive(true);
		PositionBaseBuilder.setPositionNumber("TST-PSTNNUM");
		PositionBaseBuilder.setHrPositionId("TST-PSTNID");
		PositionBaseBuilder.setDescription("Testing");
		PositionBaseBuilder.setGroupKeyCode("ISU-IA");
		PositionBaseBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		PositionBaseBuilder.setId(PositionBaseBuilder.getHrPositionId());
		PositionBaseBuilder.setVersionNumber(1L);
		PositionBaseBuilder.setEffectiveLocalDate(currentTime);
		PositionBaseBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		testPositionBaseBos.put(PositionBaseBuilder.getPositionNumber(), PositionBaseBuilder.build());
	}
	
	@Test
    public void testNotEqualsWithGroup() {
		PositionBase immutable = PositionBaseBoTest.getPositionBase("TST-PSTNNUM");
		PositionBaseBo bo = PositionBaseBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PositionBaseBo.to(bo));
    }
	
    public static PositionBase getPositionBase(String positionNumber) {
        return testPositionBaseBos.get(positionNumber);
    }

}
