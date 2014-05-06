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
package org.kuali.kpme.pm.appointment;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;
import org.kuali.kpme.pm.api.positionappointment.PositionAppointment;
import org.kuali.kpme.pm.positionappointment.PositionAppointmentBo;

public class PositionAppointmentBoTest {
	private static Map<String, PositionAppointment> testPositionAppointmentBos;
	public static PositionAppointment.Builder positionAppointmentBuilder = PositionAppointment.Builder.create("ISU-IA", "TST-PSTNAPPMNT");
	
	static {
		testPositionAppointmentBos = new HashMap<String, PositionAppointment>();
		positionAppointmentBuilder.setGroupKeyCode("ISU-IA");
		positionAppointmentBuilder.setDescription("Testing Immutable PositionAppointment");
		positionAppointmentBuilder.setPositionAppointment("TST-PSTNAPPMNT");
		positionAppointmentBuilder.setUserPrincipalId("admin");
		
		positionAppointmentBuilder.setPmPositionAppointmentId("KPME_TEST_0001");
		positionAppointmentBuilder.setVersionNumber(1L);
		positionAppointmentBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		positionAppointmentBuilder.setActive(true);
		positionAppointmentBuilder.setId(positionAppointmentBuilder.getPmPositionAppointmentId());
		positionAppointmentBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
		positionAppointmentBuilder.setCreateTime(DateTime.now());
		
		// Set GroupKeycode Object
		positionAppointmentBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		testPositionAppointmentBos.put(positionAppointmentBuilder.getPositionAppointment(), positionAppointmentBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	PositionAppointment immutable = PositionAppointmentBoTest.getPositionAppointment("TST-PSTNAPPMNT");
    	PositionAppointmentBo bo = PositionAppointmentBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PositionAppointmentBo.to(bo));
    }

    public static PositionAppointment getPositionAppointment(String positionAppointment) {
        return testPositionAppointmentBos.get(positionAppointment);
    }
}
