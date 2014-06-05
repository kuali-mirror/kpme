package org.kuali.kpme.core.paytype;

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
import org.kuali.kpme.core.api.paytype.PayType;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;

public class PayTypeBoTest {

	private static Map<String, PayType> testPayTypeBos;
	public static PayType.Builder payTypeBuilder = PayType.Builder.create("TST-PAYTYPE");
	public static EffectiveKey.Builder payTypeKeyBuilder = EffectiveKey.Builder.create();
	
	static {
		testPayTypeBos = new HashMap<String, PayType>();
		
		payTypeBuilder.setDescr("Testing Immutable SalaryGroup");
		payTypeBuilder.setPayType("TST-PAYTYPE");
		payTypeBuilder.setUserPrincipalId("admin");
		
		payTypeBuilder.setHrPayTypeId("KPME_TEST_0001");
		payTypeBuilder.setVersionNumber(1L);
		payTypeBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		payTypeBuilder.setActive(true);
		payTypeBuilder.setId(payTypeBuilder.getHrPayTypeId());
		payTypeBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
		payTypeBuilder.setCreateTime(DateTime.now());
		
		// now populate the derived key object builder
		payTypeKeyBuilder.setGroupKeyCode("ISU-IA");
		payTypeKeyBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		payTypeKeyBuilder.setOwnerId(payTypeBuilder.getHrPayTypeId());
		payTypeKeyBuilder.setId("derived key object 01");
		payTypeKeyBuilder.setEffectiveLocalDateOfOwner(payTypeBuilder.getEffectiveLocalDate());
		
		Set<EffectiveKey.Builder> keyBuilders = new HashSet<EffectiveKey.Builder>();
		keyBuilders.add(payTypeKeyBuilder);
		payTypeBuilder.setEffectiveKeySet(keyBuilders);
		
		testPayTypeBos.put(payTypeBuilder.getPayType(), payTypeBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	PayType im1 = PayTypeBoTest.getPayType("TST-PAYTYPE");
    	PayTypeBo bo1 = PayTypeBo.from(im1);
        Assert.assertFalse(bo1.equals(im1));
        Assert.assertFalse(im1.equals(bo1));
        PayType im2 = PayTypeBo.to(bo1);
        PayTypeBo bo2 = PayTypeBo.from(im2);
        PayType im3 = PayTypeBo.to(bo2);
        Assert.assertEquals(im2, im3);
    }

    public static PayType getPayType(String payType) {
        return testPayTypeBos.get(payType);
    }

}