package org.kuali.hr.lm.leaveplan;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.kuali.hr.time.test.TkTestCase;

public class LeavePlanInquirableImplTest extends TkTestCase {
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetBusinessObject() throws Exception {
		Map fieldValues = new HashMap();
		fieldValues.put("leavePlan", "TestLeavePlan");
		fieldValues.put("effectiveDate", "02/01/2012");
		LeavePlan lp = (LeavePlan) new LeavePlanInquirableImpl().getBusinessObject(fieldValues);
		assertNotNull("No leave plan found", lp);
		String dateString =  new SimpleDateFormat("MM/dd/yyyy").format(lp.getEffectiveDate());
		// in LeavePlanInquirableImplTest, 3 leave plans are added to database with 3 effectiveDate 12/01/2011, 01/01/2012
		// and 03/01/2012, both have "TestLeavePlan" as leavePlan
		assertTrue("Wrong leave plan found", StringUtils.equals(dateString, "01/01/2012"));
				
		
	}

}
