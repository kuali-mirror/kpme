package org.kuali.hr.time.principal.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;

public class PrincipalHRAttributeServiceTest extends KPMETestCase {
	@Test
	public void testGetPrincipalHrAtributes() {
		List<PrincipalHRAttributes> phraList = new ArrayList<PrincipalHRAttributes>();
		Date fromEffDate = TKUtils.formatDateString("");
		Date toEffDate = TKUtils.formatDateString("");
		
		// show both active and inactive, show history
		phraList = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalHrAtributes("testUser", fromEffDate, toEffDate, "B", "Y");
		Assert.assertEquals("Incorrect number of PrincipalHRAttributes", 3, phraList.size());
		// active="Y", show history
		phraList = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalHrAtributes("testUser", fromEffDate, toEffDate, "Y", "Y");
		Assert.assertEquals("Incorrect number of PrincipalHRAttributes", 2, phraList.size());
		// active="N", show history
		phraList = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalHrAtributes("testUser", fromEffDate, toEffDate, "N", "Y");
		Assert.assertEquals("Incorrect number of PrincipalHRAttributes", 1, phraList.size());
		// active = "Y", do not show history
		phraList = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalHrAtributes("testUser", fromEffDate, toEffDate, "N", "N");
		Assert.assertEquals("Incorrect number of PrincipalHRAttributes", 1, phraList.size());
	}
}
