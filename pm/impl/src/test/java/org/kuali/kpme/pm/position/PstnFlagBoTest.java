package org.kuali.kpme.pm.position;

import java.util.*;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.pm.api.position.Position;
import org.kuali.kpme.pm.api.position.PstnFlag;

public class PstnFlagBoTest {
	private static Map<String, PstnFlag> testPstnFlagBos;
	public static PstnFlag.Builder pstnFlagBuilder = PstnFlag.Builder.create();
	static LocalDate currentTime = new LocalDate();

	static {

		testPstnFlagBos = new HashMap<String, PstnFlag>();
		
		List<String> names = new ArrayList<String>();
		names.add("Name1");
		pstnFlagBuilder.setCategory("CAT");
		pstnFlagBuilder.setHrPositionId("");
		pstnFlagBuilder.setNames(names);
		pstnFlagBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		pstnFlagBuilder.setEffectiveLocalDateOfOwner(currentTime);
//		pstnFlagBuilder.setOwner(Position.Builder.create(PositionDataBoTest.getPosition("TST-PSTN")));
		pstnFlagBuilder.setPmFlagId("TST-PSTNFLAG");
		pstnFlagBuilder.setVersionNumber(1L);
		
		
		testPstnFlagBos.put(pstnFlagBuilder.getPmFlagId(),pstnFlagBuilder.build());
		System.out.println(testPstnFlagBos);
	}

	@Test
    public void testNotEqualsWithGroup() {
    	PstnFlag immutable = PstnFlagBoTest.getPstnFlag("TST-PSTNFLAG");
    	PstnFlagBo bo = PstnFlagBo.from(immutable);
    	
    	PositionBo positionBo = new PositionBo();
		positionBo.setEffectiveLocalDate(currentTime);
		bo.setOwner(positionBo);
    	
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PstnFlagBo.to(bo));
    }

    public static PstnFlag getPstnFlag(String pstnFlag) {
         PstnFlag pstnFlag1 = testPstnFlagBos.get(pstnFlag);
         return pstnFlag1;
    }

}