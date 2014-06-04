package org.kuali.kpme.core.salarygroup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.mo.EffectiveKey;
import org.kuali.kpme.core.api.salarygroup.SalaryGroup;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;

public class SalaryGroupBoTest {
	private static Map<String, SalaryGroup> testSalaryGroupBos;
	public static SalaryGroup.Builder salaryGroupBuilder = SalaryGroup.Builder.create("TST-SALGROUP");
	public static EffectiveKey.Builder salaryGroupKeyBuilder = EffectiveKey.Builder.create();
	
	static {
		testSalaryGroupBos = new HashMap<String, SalaryGroup>();
		
		salaryGroupBuilder.setDescr("Testing Immutable SalaryGroup");
		salaryGroupBuilder.setHrSalGroup("TST-SALGROUP");
		salaryGroupBuilder.setUserPrincipalId("admin");
		
		salaryGroupBuilder.setHrSalGroupId("KPME_TEST_0001");
		salaryGroupBuilder.setVersionNumber(1L);
		salaryGroupBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		salaryGroupBuilder.setActive(true);
		salaryGroupBuilder.setId(salaryGroupBuilder.getHrSalGroupId());
		salaryGroupBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
		salaryGroupBuilder.setCreateTime(DateTime.now());
		
		// now populate the derived key object builder
		salaryGroupKeyBuilder.setGroupKeyCode("ISU-IA");
		salaryGroupKeyBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		salaryGroupKeyBuilder.setOwnerId(salaryGroupBuilder.getHrSalGroupId());
		salaryGroupKeyBuilder.setId("derived key object 01");
		salaryGroupKeyBuilder.setEffectiveLocalDateOfOwner(salaryGroupBuilder.getEffectiveLocalDate());
		
		Set<EffectiveKey.Builder> keyBuilders = new HashSet<EffectiveKey.Builder>();
		keyBuilders.add(salaryGroupKeyBuilder);
		salaryGroupBuilder.setEffectiveKeySet(keyBuilders);
		
		testSalaryGroupBos.put(salaryGroupBuilder.getHrSalGroup(), salaryGroupBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	SalaryGroup im1 = SalaryGroupBoTest.getSalaryGroup("TST-SALGROUP");
    	SalaryGroupBo bo1 = SalaryGroupBo.from(im1);
        Assert.assertFalse(bo1.equals(im1));
        Assert.assertFalse(im1.equals(bo1));
        SalaryGroup im2 = SalaryGroupBo.to(bo1);
        SalaryGroupBo bo2 = SalaryGroupBo.from(im2);
        SalaryGroup im3 = SalaryGroupBo.to(bo2);
        Assert.assertEquals(im2, im3);
    }

    public static SalaryGroup getSalaryGroup(String salaryGroup) {
        return testSalaryGroupBos.get(salaryGroup);
    }

}
