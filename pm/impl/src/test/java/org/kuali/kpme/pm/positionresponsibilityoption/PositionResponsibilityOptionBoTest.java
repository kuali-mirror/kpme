package org.kuali.kpme.pm.positionresponsibilityoption;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.pm.api.positionresponsibilityoption.PositionResponsibilityOption;
import org.kuali.kpme.pm.positionResponsibilityOption.PositionResponsibilityOptionBo;

public class PositionResponsibilityOptionBoTest {


	private static Map<String, PositionResponsibilityOption> testPositionResponsibilityOptionBos;
	public static PositionResponsibilityOption.Builder prOptionBuilder = PositionResponsibilityOption.Builder.create();
	
	static {
		testPositionResponsibilityOptionBos = new HashMap<String, PositionResponsibilityOption>();
		
		prOptionBuilder.setPrDescription("Testing Immutable PositionResponsibilityOption");
		prOptionBuilder.setPrOptionName("TST-PSTNRESPOPT");
		prOptionBuilder.setPrOptionId("KPME_TEST_0001");
		prOptionBuilder.setUserPrincipalId("admin");
		prOptionBuilder.setVersionNumber(1L);
		prOptionBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		prOptionBuilder.setActive(true);
		prOptionBuilder.setId(prOptionBuilder.getPrOptionId());
		prOptionBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
		prOptionBuilder.setCreateTime(DateTime.now());
		
		testPositionResponsibilityOptionBos.put(prOptionBuilder.getPrOptionName(), prOptionBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	PositionResponsibilityOption immutable = PositionResponsibilityOptionBoTest.getPositionResponsibilityOption("TST-PSTNRESPOPT");
    	PositionResponsibilityOptionBo bo = PositionResponsibilityOptionBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PositionResponsibilityOptionBo.to(bo));
    }

    public static PositionResponsibilityOption getPositionResponsibilityOption(String positionAppointment) {
        return testPositionResponsibilityOptionBos.get(positionAppointment);
    }
}
