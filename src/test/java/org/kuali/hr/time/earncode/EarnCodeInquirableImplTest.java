package org.kuali.hr.time.earncode;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;

public class EarnCodeInquirableImplTest extends KPMETestCase {
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetBusinessObject() throws Exception {
		Map fieldValues = new HashMap();
		fieldValues.put("earnCode", "TestEarnCode");
		fieldValues.put("effectiveDate", "02/01/2012");
		EarnCode ec = (EarnCode) new EarnCodeInquirableImpl().getBusinessObject(fieldValues);
		Assert.assertNotNull("No Earn Code found", ec);
		String dateString =  new SimpleDateFormat("MM/dd/yyyy").format(ec.getEffectiveDate());
		// in this Test, 3 earn codes are added to database with 3 effectiveDate 12/01/2011, 01/01/2012
		// and 03/01/2012, both have "TestEarnCode" as earnCode
		Assert.assertTrue("Wrong Earn Code found", StringUtils.equals(dateString, "01/01/2012"));
	}
}
