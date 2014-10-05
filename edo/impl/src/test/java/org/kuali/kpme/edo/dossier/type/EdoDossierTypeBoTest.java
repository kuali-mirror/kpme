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
package org.kuali.kpme.edo.dossier.type;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.edo.api.dossier.type.EdoDossierType;

public class EdoDossierTypeBoTest {
	private static Map<String, EdoDossierType> testEdoDossierTypeBos;
	public static EdoDossierType.Builder edoDossierTypeBuilder = EdoDossierType.Builder.create( "test1");
	
	static {
		testEdoDossierTypeBos = new HashMap<String, EdoDossierType>();
		edoDossierTypeBuilder.setEdoDossierTypeId("1003");
		edoDossierTypeBuilder.setDossierTypeCode("cc");
		edoDossierTypeBuilder.setDocumentTypeName("documentName");
		edoDossierTypeBuilder.setDossierTypeName("dossierTypeName");
		
		edoDossierTypeBuilder.setUserPrincipalId("admin");
		edoDossierTypeBuilder.setVersionNumber(1L);
		edoDossierTypeBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		edoDossierTypeBuilder.setActive(true);
		edoDossierTypeBuilder.setEffectiveLocalDate(new LocalDate(2012, 3, 1));
		edoDossierTypeBuilder.setCreateTime(DateTime.now());
		edoDossierTypeBuilder.setId(edoDossierTypeBuilder.getEdoDossierTypeId());
		// Set GroupKeycode Object
		testEdoDossierTypeBos.put(edoDossierTypeBuilder.getDossierTypeCode(), edoDossierTypeBuilder.build());
	}
	
	@Test
    public void testNotEqualsWithGroup() {
		EdoDossierType immutable = EdoDossierTypeBoTest.getEdoDossierType("cc");
		EdoDossierTypeBo bo = EdoDossierTypeBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoDossierTypeBo.to(bo));
    }

    public static EdoDossierType getEdoDossierType(String dossierTypeCode) {
        return testEdoDossierTypeBos.get(dossierTypeCode);
    }
}
