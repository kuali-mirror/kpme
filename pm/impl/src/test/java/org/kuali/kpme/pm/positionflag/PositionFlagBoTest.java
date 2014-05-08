package org.kuali.kpme.pm.positionflag;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.pm.api.positionflag.PositionFlag;

public class PositionFlagBoTest {

	private static Map<String, PositionFlag> testPositionFlagBos;
	public static PositionFlag.Builder positionFlagBuilder = PositionFlag.Builder.create();
	
	static{
		
		testPositionFlagBos = new HashMap<String, PositionFlag>();
		
		positionFlagBuilder.setActive(true);
		positionFlagBuilder.setCategory("Primary Test");
		positionFlagBuilder.setCreateTime(DateTime.now());
		positionFlagBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
		positionFlagBuilder.setPmPositionFlagId("KPME_TEST_0001");
		positionFlagBuilder.setId(positionFlagBuilder.getPmPositionFlagId());
		positionFlagBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		positionFlagBuilder.setPositionFlagName("TST-PSTNFLG");
		positionFlagBuilder.setUserPrincipalId("admin");
		positionFlagBuilder.setVersionNumber(1l);

		testPositionFlagBos.put(positionFlagBuilder.getPositionFlagName(), positionFlagBuilder.build());
	}
	
	
	@Test
    public void testNotEqualsWithGroup() {
    	PositionFlag immutable = PositionFlagBoTest.getPositionFlag("TST-PSTNFLG");
    	PositionFlagBo bo = PositionFlagBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PositionFlagBo.to(bo));
    }

    public static PositionFlag getPositionFlag(String PositionFlag) {
        return testPositionFlagBos.get(PositionFlag);
    }
	
}
