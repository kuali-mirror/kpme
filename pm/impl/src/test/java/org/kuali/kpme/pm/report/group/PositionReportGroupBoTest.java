/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.pm.report.group;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;
import org.kuali.kpme.pm.api.positionreportgroup.PositionReportGroup;
import org.kuali.kpme.pm.positionreportgroup.PositionReportGroupBo;

public class PositionReportGroupBoTest {

	private static Map<String, PositionReportGroup> testPositionReportGroupBos;
	public static PositionReportGroup.Builder positionReportGroupBuilder = PositionReportGroup.Builder.create("ISU-IA", "TST-PSTNRPTGRP");
	
	static {
		testPositionReportGroupBos = new HashMap<String, PositionReportGroup>();
		positionReportGroupBuilder.setGroupKeyCode("ISU-IA");
		positionReportGroupBuilder.setDescription("Testing Immutable PositionReportGroup");
		positionReportGroupBuilder.setPositionReportGroup("TST-PSTNRPTGRP");
		positionReportGroupBuilder.setUserPrincipalId("admin");
		
		positionReportGroupBuilder.setPmPositionReportGroupId("KPME_TEST_0001");
		positionReportGroupBuilder.setVersionNumber(1L);
		positionReportGroupBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		positionReportGroupBuilder.setActive(true);
		positionReportGroupBuilder.setId(positionReportGroupBuilder.getPmPositionReportGroupId());
		positionReportGroupBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
		positionReportGroupBuilder.setCreateTime(DateTime.now());
		
		// Set GroupKeycode Object
		positionReportGroupBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		testPositionReportGroupBos.put(positionReportGroupBuilder.getPositionReportGroup(), positionReportGroupBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	PositionReportGroup immutable = PositionReportGroupBoTest.getPositionReportGroup("TST-PSTNRPTGRP");
    	PositionReportGroupBo bo = PositionReportGroupBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PositionReportGroupBo.to(bo));
    }

    public static PositionReportGroup getPositionReportGroup(String positionReportGroup) {
        return testPositionReportGroupBos.get(positionReportGroup);
    }
	
}
