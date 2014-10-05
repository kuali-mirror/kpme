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

import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.edo.api.dossier.EdoDossierDocumentInfo;
import org.kuali.kpme.edo.workflow.EdoDossierDocumentInfoBo;

public class EdoDossierDocumentInfoBoTest {
	private static Map<String, EdoDossierDocumentInfo> testEdoDossierDocumentInfoBos;
	public static EdoDossierDocumentInfo.Builder edoEdoDossierDocumentInfoBuilder = EdoDossierDocumentInfo.Builder.create();
	
	static {
		testEdoDossierDocumentInfoBos = new HashMap<String, EdoDossierDocumentInfo>();
		
		edoEdoDossierDocumentInfoBuilder.setEdoDocumentId("1000");
		edoEdoDossierDocumentInfoBuilder.setEdoDossierId("1001");
		edoEdoDossierDocumentInfoBuilder.setPrincipalId("testPrincipalId");
		edoEdoDossierDocumentInfoBuilder.setDocumentStatus("P");
		edoEdoDossierDocumentInfoBuilder.setDocumentTypeName("documentTypeName");
		
		testEdoDossierDocumentInfoBos.put(edoEdoDossierDocumentInfoBuilder.getEdoDocumentId(), edoEdoDossierDocumentInfoBuilder.build());
	}
	
	@Test
    public void testNotEqualsWithGroup() {
		EdoDossierDocumentInfo immutable = EdoDossierDocumentInfoBoTest.getEdoDossierDocumentInfo("1000");
		EdoDossierDocumentInfoBo bo = EdoDossierDocumentInfoBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoDossierDocumentInfoBo.to(bo));
    }

    public static EdoDossierDocumentInfo getEdoDossierDocumentInfo(String documentId) {
        return testEdoDossierDocumentInfoBos.get(documentId);
    }
}
