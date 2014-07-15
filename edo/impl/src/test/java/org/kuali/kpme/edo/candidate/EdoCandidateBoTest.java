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
package org.kuali.kpme.edo.candidate;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;
import org.kuali.kpme.edo.api.candidate.EdoCandidate;

public class EdoCandidateBoTest {
	private static Map<String, EdoCandidate> testEdoCandidateBos;
	public static EdoCandidate.Builder edoCandidateBuilder = EdoCandidate.Builder.create( "test1");
	
	static {
		testEdoCandidateBos = new HashMap<String, EdoCandidate>();
		edoCandidateBuilder.setEdoCandidateId("1000");
		edoCandidateBuilder.setPrincipalName("TST-PrincipalName");
		edoCandidateBuilder.setUserPrincipalId("admin");
		edoCandidateBuilder.setFirstName("firstName");
		edoCandidateBuilder.setLastName("lastName");
		edoCandidateBuilder.setCandidacySchool("test-candidacy-school");
		edoCandidateBuilder.setPrimaryDeptID("PHY");
		edoCandidateBuilder.setTnpDeptID("PHY");
		edoCandidateBuilder.setVersionNumber(1L);
		edoCandidateBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		edoCandidateBuilder.setActive(true);
		edoCandidateBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
		edoCandidateBuilder.setCreateTime(DateTime.now());
		edoCandidateBuilder.setId(edoCandidateBuilder.getEdoCandidateId());
		
		// Set GroupKeycode Object
		edoCandidateBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		testEdoCandidateBos.put(edoCandidateBuilder.getPrincipalName(), edoCandidateBuilder.build());
	}
	
	@Test
    public void testNotEqualsWithGroup() {
		EdoCandidate immutable = EdoCandidateBoTest.getEdoCandidate("TST-PrincipalName");
		EdoCandidateBo bo = EdoCandidateBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoCandidateBo.to(bo));
    }

    public static EdoCandidate getEdoCandidate(String principalName) {
        return testEdoCandidateBos.get(principalName);
    }
}
