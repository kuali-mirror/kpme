package org.kuali.hr.location.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.kpme.core.bo.location.Location;
import org.kuali.kpme.core.service.HrServiceLocator;

public class LocationServiceImplTest extends KPMETestCase {

	@Test
	public void testSearchLocations() throws Exception {
		List<Location> allResults = HrServiceLocator.getLocationService().searchLocations("admin", null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 2, allResults.size());
		
		List<Location> restrictedResults = HrServiceLocator.getLocationService().searchLocations("testuser6", null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 0, restrictedResults.size());
	}
	
}
