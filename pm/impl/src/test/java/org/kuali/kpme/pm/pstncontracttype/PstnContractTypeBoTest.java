package org.kuali.kpme.pm.pstncontracttype;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;
import org.kuali.kpme.pm.api.pstncontracttype.PstnContractType;

public class PstnContractTypeBoTest {
	private static Map<String, PstnContractType> testPstnContractTypeBos;
	public static PstnContractType.Builder PstnContractTypeBuilder = PstnContractType.Builder.create("ISU-IA", "TST-PSTNCNTCTTYP");
	
	static {
		testPstnContractTypeBos = new HashMap<String, PstnContractType>();
		PstnContractTypeBuilder.setGroupKeyCode("ISU-IA");
		PstnContractTypeBuilder.setDescription("Testing Immutable PstnContractType");
		PstnContractTypeBuilder.setName("TST-PSTNCNTCTTYP");
		PstnContractTypeBuilder.setUserPrincipalId("admin");
		
		PstnContractTypeBuilder.setPmCntrctTypeId("KPME_TEST_0001");
		PstnContractTypeBuilder.setVersionNumber(1L);
		PstnContractTypeBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		PstnContractTypeBuilder.setActive(true);
		PstnContractTypeBuilder.setId(PstnContractTypeBuilder.getPmCntrctTypeId());
		PstnContractTypeBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
		PstnContractTypeBuilder.setCreateTime(DateTime.now());
		
		// Set GroupKeycode Object
		PstnContractTypeBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		testPstnContractTypeBos.put(PstnContractTypeBuilder.getName(), PstnContractTypeBuilder.build());
	}
	
	@Test
    public void testNotEqualsWithGroup() {
		PstnContractType immutable = PstnContractTypeBoTest.getPstnContractType("TST-PSTNCNTCTTYP");
		PstnContractTypeBo bo = PstnContractTypeBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PstnContractTypeBo.to(bo));
    }

    public static PstnContractType getPstnContractType(String pstnContractType) {
        return testPstnContractTypeBos.get(pstnContractType);
    }
}
