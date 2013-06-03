/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.core.principal;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.KPMETestCase;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;

public class PrincipalHRAttributeServiceTest extends KPMETestCase {
	@Test
	public void testGetPrincipalHrAtributes() {
		List<PrincipalHRAttributes> phraList = new ArrayList<PrincipalHRAttributes>();
		LocalDate fromEffDate = TKUtils.formatDateString("");
		LocalDate toEffDate = TKUtils.formatDateString("");
        String leavePlan = "";
		
		// show both active and inactive, show history
		phraList = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalHrAtributes("admin", "testUser", leavePlan, fromEffDate, toEffDate, "B", "Y");
		Assert.assertEquals("Incorrect number of PrincipalHRAttributes", 3, phraList.size());
		// active="Y", show history
		phraList = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalHrAtributes("admin", "testUser", leavePlan, fromEffDate, toEffDate, "Y", "Y");
		Assert.assertEquals("Incorrect number of PrincipalHRAttributes", 2, phraList.size());
		// active="N", show history
		phraList = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalHrAtributes("admin", "testUser", leavePlan, fromEffDate, toEffDate, "N", "Y");
		Assert.assertEquals("Incorrect number of PrincipalHRAttributes", 1, phraList.size());
		// active = "Y", do not show history
		phraList = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalHrAtributes("admin", "testUser", leavePlan, fromEffDate, toEffDate, "N", "N");
		Assert.assertEquals("Incorrect number of PrincipalHRAttributes", 1, phraList.size());
	}
	
	@Test
	public void testSearchPrincipalHRAttributes() throws Exception {
		List<PrincipalHRAttributes> allResults = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalHrAtributes("admin", null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 2, allResults.size());
		
		List<PrincipalHRAttributes> restrictedResults = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalHrAtributes("testuser6", null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 0, restrictedResults.size());
	}
	
}