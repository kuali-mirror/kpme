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
package org.kuali.kpme.core.principal;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.leaveplan.LeavePlan;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.calendar.CalendarBoTest;
import org.kuali.kpme.core.leaveplan.LeavePlanBoTest;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.name.EntityName;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PrincipalHRAttributesBoTest {
    private static IdentityService mockIdentityService;

    private static Map<String, PrincipalHRAttributes> testPrincipalHRAttributeBos;
    public static PrincipalHRAttributes.Builder principalHrAttributesBuilder = PrincipalHRAttributes.Builder.create("testuser1");
    static {
        testPrincipalHRAttributeBos = new HashMap<String, PrincipalHRAttributes>();
        principalHrAttributesBuilder.setName("user, test1");
        principalHrAttributesBuilder.setUserPrincipalId("admin");
        principalHrAttributesBuilder.setLeavePlan("LEAVE");
        principalHrAttributesBuilder.setServiceLocalDate(new LocalDate(2010, 1, 1));
        principalHrAttributesBuilder.setFmlaEligible(true);
        principalHrAttributesBuilder.setWorkersCompEligible(true);
        principalHrAttributesBuilder.setTimezone("America/Indiana/Indianapolis");
        principalHrAttributesBuilder.setPayCalendar("BWS-CAL");
        principalHrAttributesBuilder.setLeaveCalendar("LM");

        principalHrAttributesBuilder.setHrPrincipalAttributeId("KPME_TEST_0001");
        principalHrAttributesBuilder.setVersionNumber(1L);
        principalHrAttributesBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
        principalHrAttributesBuilder.setActive(true);
        principalHrAttributesBuilder.setId(principalHrAttributesBuilder.getHrPrincipalAttributeId());
        principalHrAttributesBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
        principalHrAttributesBuilder.setCreateTime(DateTime.now());

        principalHrAttributesBuilder.setLeaveCalObj(Calendar.Builder.create(CalendarBoTest.getTestCalendar("LM")));
        principalHrAttributesBuilder.setCalendar(Calendar.Builder.create(CalendarBoTest.getTestCalendar("BWS-CAL")));
        principalHrAttributesBuilder.setLeavePlanObj(LeavePlan.Builder.create(LeavePlanBoTest.getLeavePlan("LEAVE")));

        testPrincipalHRAttributeBos.put(principalHrAttributesBuilder.getPrincipalId(), principalHrAttributesBuilder.build());
    }

    @Before
    public void setup() throws Exception {
        EntityName.Builder entityName = EntityName.Builder.create("testuser1", "entityId", "test1", "user", false);
        EntityNamePrincipalName.Builder enpn = EntityNamePrincipalName.Builder.create();
        enpn.setPrincipalName("testUser1");
        enpn.setDefaultName(entityName);
        mockIdentityService = mock(IdentityService.class);
        {
            when( mockIdentityService.getDefaultNamesForPrincipalId(anyString())).thenReturn(enpn.build());
        }
    }

    @Test
    public void testNotEqualsWithGroup() {
        PrincipalHRAttributes immutable = PrincipalHRAttributesBoTest.getPrincipalHRAttributes("testuser1");
        PrincipalHRAttributesBo bo = PrincipalHRAttributesBo.from(immutable);
        //mockIdentityService
        bo.setIdentityService(mockIdentityService);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PrincipalHRAttributesBo.to(bo));
    }

    public static PrincipalHRAttributes getPrincipalHRAttributes(String principalId) {
        return testPrincipalHRAttributeBos.get(principalId);
    }
}
