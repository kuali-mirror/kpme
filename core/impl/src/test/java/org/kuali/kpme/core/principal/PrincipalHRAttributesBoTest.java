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


import org.kuali.kpme.core.UnitTest;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.leaveplan.LeavePlan;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.calendar.CalendarBo;
import org.kuali.kpme.core.calendar.CalendarBoTest;
import org.kuali.kpme.core.leaveplan.LeavePlanBo;
import org.kuali.kpme.core.leaveplan.LeavePlanBoTest;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.name.EntityName;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@UnitTest
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
    public void testPrincipalHRConversions() {
        PrincipalHRAttributes immutable = PrincipalHRAttributesBoTest.getPrincipalHRAttributes("testuser1");
        PrincipalHRAttributesBo bo = PrincipalHRAttributesBo.from(immutable);
        //mockIdentityService
        bo.setIdentityService(mockIdentityService);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PrincipalHRAttributesBo.to(bo));
    }

    @Test
    public void testNotEqualsWithCalendar()
    {
        PrincipalHRAttributes immutable1 = PrincipalHRAttributesBoTest.getPrincipalHRAttributes("testuser1");
        PrincipalHRAttributesBo bo1 = PrincipalHRAttributesBo.from(immutable1);

        PrincipalHRAttributes immutable2 = PrincipalHRAttributesBoTest.getPrincipalHRAttributes("testuser1");
        PrincipalHRAttributesBo bo2 = PrincipalHRAttributesBo.from(immutable2);

        bo1.setCalendar(CalendarBo.from(CalendarBoTest.getTestCalendar("LM")));
        bo2.setCalendar(CalendarBo.from(CalendarBoTest.getTestCalendar("BWS-CAL")));

        Assert.assertNotEquals(bo1.getCalendar(), bo2.getCalendar());
    }

    @Test
    public void testServiceLocalDateNotEquals()
    {
        PrincipalHRAttributes immutable1 = PrincipalHRAttributesBoTest.getPrincipalHRAttributes("testuser1");
        PrincipalHRAttributesBo bo1 = PrincipalHRAttributesBo.from(immutable1);

        PrincipalHRAttributes immutable2 = PrincipalHRAttributesBoTest.getPrincipalHRAttributes("testuser1");
        PrincipalHRAttributesBo bo2 = PrincipalHRAttributesBo.from(immutable2);

        bo1.setServiceLocalDate(new LocalDate(2014,6,4));
        bo2.setServiceLocalDate(new LocalDate(2014,6,4));

        Assert.assertEquals(bo1.getServiceLocalDate(), bo2.getServiceLocalDate());
    }

    @Test
    public void testLeavePlanNotEquals()
    {
        PrincipalHRAttributes immutable1 = PrincipalHRAttributesBoTest.getPrincipalHRAttributes("testuser1");
        PrincipalHRAttributesBo bo1 = PrincipalHRAttributesBo.from(immutable1);

        PrincipalHRAttributes immutable2 = PrincipalHRAttributesBoTest.getPrincipalHRAttributes("testuser1");
        PrincipalHRAttributesBo bo2 = PrincipalHRAttributesBo.from(immutable2);

        LeavePlanBo lpbo = LeavePlanBo.from(LeavePlan.Builder.create(LeavePlanBoTest.getLeavePlan("LEAVE")).build());
        bo1.setLeavePlanObj(lpbo);
        bo2.setLeavePlanObj(lpbo);

        Assert.assertEquals(bo1.getLeavePlanObj(), bo2.getLeavePlanObj());
    }

    @Test
    public void testXmlMarshaling() {
        JAXBContext jc = null;
        try {
            jc = JAXBContext.newInstance(PrincipalHRAttributes.class);

            Marshaller marshaller = jc.createMarshaller();
            StringWriter sw = new StringWriter();

            PrincipalHRAttributes principalHrAttributes = getPrincipalHRAttributes("testuser1");
            marshaller.marshal(principalHrAttributes, sw);
            String xml = sw.toString();
            System.out.println(xml);
            Assert.assertTrue(true);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }


    public static PrincipalHRAttributes getPrincipalHRAttributes(String principalId) {
        return testPrincipalHRAttributeBos.get(principalId);
    }
}
