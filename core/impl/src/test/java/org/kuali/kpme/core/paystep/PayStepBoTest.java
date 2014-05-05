package org.kuali.kpme.core.paystep;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.paystep.PayStep;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;

public class PayStepBoTest {

	    private static Map<String, PayStep> testPayStepBos;
	    public static PayStep.Builder payStepBuilder = PayStep.Builder.create("ISU-IA","TST-PAYSTEP");
	    static {
	    	
	    	testPayStepBos = new HashMap<String, PayStep>();
	        payStepBuilder.setGroupKeyCode("ISU-IA");
	        payStepBuilder.setPmPayStepId("KPME-TEST-0001");
	        payStepBuilder.setPayStep("TST-PAYSTEP");
	        payStepBuilder.setPayGrade("PS5");
	        payStepBuilder.setCompRate(new BigDecimal(500));
	        payStepBuilder.setSalaryGroup("HR");
	        payStepBuilder.setStepNumber(2);
	        payStepBuilder.setServiceAmount(100);
	        payStepBuilder.setServiceUnit("M");
	        payStepBuilder.setActive(true);
	        
	        payStepBuilder.setVersionNumber(1L);
	        payStepBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
	        payStepBuilder.setUserPrincipalId("admin");
	        payStepBuilder.setId(payStepBuilder.getPmPayStepId());
			payStepBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
			payStepBuilder.setCreateTime(DateTime.now());
	        
	        payStepBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
	        testPayStepBos.put(payStepBuilder.getPayStep(), payStepBuilder.build());
	        
	    }

	    @Test
	    public void testNotEqualsWithGroup() {
	        PayStep immutable = PayStepBoTest.getPayStep("TST-PAYSTEP");
	        PayStepBo bo = PayStepBo.from(immutable);
	        Assert.assertFalse(bo.equals(immutable));
	        Assert.assertFalse(immutable.equals(bo));
	        Assert.assertEquals(immutable, PayStepBo.to(bo));
	    }

	    public static PayStep getPayStep(String payStep) {
	        return testPayStepBos.get(payStep);
	    }
}