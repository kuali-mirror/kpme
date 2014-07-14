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
package org.kuali.kpme.edo.dossier;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;
import org.kuali.kpme.edo.api.dossier.EdoDossier;

public class EdoDossierBoTest {
	private static Map<String, EdoDossier> testEdoDossierBos;
	public static EdoDossier.Builder edoDossierBuilder = EdoDossier.Builder.create( "admin");
	
	static {
		testEdoDossierBos = new HashMap<String, EdoDossier>();
		
		edoDossierBuilder.setEdoDossierId("001");
		edoDossierBuilder.setCandidatePrincipalName("admin");
		edoDossierBuilder.setEdoDossierTypeId("1003");
		edoDossierBuilder.setEdoChecklistId("1000");
		
		edoDossierBuilder.setAoeCode("P");
		edoDossierBuilder.setDepartmentID("deptID");
		edoDossierBuilder.setSecondaryUnit("chem");
		edoDossierBuilder.setOrganizationCode("Science");
		edoDossierBuilder.setCurrentRank("Assi");
		edoDossierBuilder.setRankSought("Asso");
		
		edoDossierBuilder.setUserPrincipalId("admin");
		edoDossierBuilder.setVersionNumber(1L);
		edoDossierBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		edoDossierBuilder.setActive(true);
		edoDossierBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
		edoDossierBuilder.setCreateTime(DateTime.now());
		edoDossierBuilder.setId(edoDossierBuilder.getEdoDossierId());
		// Set GroupKeycode Object
		edoDossierBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		
		testEdoDossierBos.put(edoDossierBuilder.getCandidatePrincipalName(), edoDossierBuilder.build());
	}
	
	@Test
    public void testNotEqualsWithGroup() {
		EdoDossier immutable = EdoDossierBoTest.getEdoDossier("admin");
		EdoDossierBo bo = EdoDossierBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoDossierBo.to(bo));
    }

    public static EdoDossier getEdoDossier(String candidatePrincipalName) {
        return testEdoDossierBos.get(candidatePrincipalName);
    }
}
