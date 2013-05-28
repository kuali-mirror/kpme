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
package org.kuali.hr.time.workarea.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.kpme.core.service.HrServiceLocator;


public class WorkAreaServiceImplTest extends KPMETestCase {
	
	@Test
	public void testSearchWorkAreas() throws Exception {
		List<WorkArea> allResults = HrServiceLocator.getWorkAreaService().getWorkAreas("admin", null, null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 8, allResults.size());
		
		List<WorkArea> restrictedResults = HrServiceLocator.getWorkAreaService().getWorkAreas("testuser6", null, null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 2, restrictedResults.size());
	}

}
