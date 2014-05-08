package org.kuali.kpme.core.earncode.security;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.kpme.core.api.earncode.security.EarnCodeSecurity;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.api.location.Location;
import org.kuali.kpme.core.api.salarygroup.SalaryGroup;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;

public class EarnCodeSecurityBoTest {

	    private static Map<String, EarnCodeSecurity> testEarnCodeSecurityBos;
	    public static EarnCodeSecurity.Builder earnCodeSecurityBuilder = EarnCodeSecurity.Builder.create();
	    static {
	    		    	
	        testEarnCodeSecurityBos = new HashMap<String, EarnCodeSecurity>();
	        
	        earnCodeSecurityBuilder.setApprover(true);
	        earnCodeSecurityBuilder.setDept("%");
	        earnCodeSecurityBuilder.setEarnCode("THOL");
	        earnCodeSecurityBuilder.setEarnCodeType("B");
	        earnCodeSecurityBuilder.setEmployee(true);
	        earnCodeSecurityBuilder.setHrSalGroup("NE");
	        earnCodeSecurityBuilder.setLocation("GA");
	        earnCodeSecurityBuilder.setPayrollProcessor(true);
	        earnCodeSecurityBuilder.setHrEarnCodeSecurityId("KPME_TEST_0001");
	        earnCodeSecurityBuilder.setVersionNumber(1L);
	        earnCodeSecurityBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
	        earnCodeSecurityBuilder.setActive(true);
	        earnCodeSecurityBuilder.setId(earnCodeSecurityBuilder.getHrEarnCodeSecurityId());
	        earnCodeSecurityBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
	        earnCodeSecurityBuilder.setCreateTime(DateTime.now());
	        earnCodeSecurityBuilder.setUserPrincipalId("admin");
	        
	        testEarnCodeSecurityBos.put(earnCodeSecurityBuilder.getEarnCode(), earnCodeSecurityBuilder.build());
	        
	    }

	    @Test
	    public void testNotEqualsWithGroup() {
	        EarnCodeSecurity immutable = EarnCodeSecurityBoTest.getEarnCodeSecurity("THOL");
	        EarnCodeSecurityBo bo = EarnCodeSecurityBo.from(immutable);
	        Assert.assertFalse(bo.equals(immutable));
	        Assert.assertFalse(immutable.equals(bo));
	        Assert.assertEquals(immutable, EarnCodeSecurityBo.to(bo));
	    }

	    public static EarnCodeSecurity getEarnCodeSecurity(String earnCodeSecurity) {
	        return testEarnCodeSecurityBos.get(earnCodeSecurity);
	    }
}