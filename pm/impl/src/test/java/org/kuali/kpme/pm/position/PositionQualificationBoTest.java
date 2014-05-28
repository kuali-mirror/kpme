package org.kuali.kpme.pm.position;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.pm.api.position.Position;
import org.kuali.kpme.pm.api.position.PositionQualification;


public class PositionQualificationBoTest {
	private static Map<String, PositionQualification> testPositionQualificationBos;
	public static PositionQualification.Builder positionQualificationBuilder = PositionQualification.Builder
			.create();
	static LocalDate currentTime = new LocalDate();

	
	static{
		
		testPositionQualificationBos = new HashMap<String, PositionQualification>();
		
		positionQualificationBuilder.setQualificationValue("");
		positionQualificationBuilder.setQualificationType("");
		positionQualificationBuilder.setPmQualificationId("TST-PSTNQLFCTN");
		positionQualificationBuilder.setTypeValue("");
		positionQualificationBuilder.setQualifier("");
		positionQualificationBuilder.setHrPositionId("");
		positionQualificationBuilder.setEffectiveLocalDateOfOwner(currentTime);
//		positionQualificationBuilder.setOwner(Position.Builder.create(PositionDataBoTest.getPosition("TST-PSTN")));
		positionQualificationBuilder.setVersionNumber(1l);
		positionQualificationBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		
		testPositionQualificationBos.put(positionQualificationBuilder.getPmQualificationId(), positionQualificationBuilder.build());
		
	}
	

	@Test
    public void testNotEqualsWithGroup() {
    	PositionQualification immutable = PositionQualificationBoTest.getPositionQualificationBo("TST-PSTNQLFCTN");
    	PositionQualificationBo bo = PositionQualificationBo.from(immutable);
        

    	PositionBo positionBo = new PositionBo();
		positionBo.setEffectiveLocalDate(currentTime);
		bo.setOwner(positionBo);
    	
    	
    	Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PositionQualificationBo.to(bo));
    }

    public static PositionQualification getPositionQualificationBo(String positionQlfctn) {
        return testPositionQualificationBos.get(positionQlfctn);
    }
}
