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
package org.kuali.kpme.edo.committeesready;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.groupkey.HrGroupKeyBoTest;
import org.kuali.kpme.edo.api.committeesready.EdoCommitteesReady;

public class EdoCommitteesReadyBoTest {
	private static Map<String, EdoCommitteesReady> testEdoCommitteesReadyBos;
	public static EdoCommitteesReady.Builder edoEdoCommitteesReadyBuilder = EdoCommitteesReady.Builder.create();
	
	static {
		testEdoCommitteesReadyBos = new HashMap<String, EdoCommitteesReady>();
		edoEdoCommitteesReadyBuilder.setEdoCommitteesReadyId("1000");
		edoEdoCommitteesReadyBuilder.setReady(true);

		edoEdoCommitteesReadyBuilder.setGroupKeyCode("ISU-IA");
		edoEdoCommitteesReadyBuilder.setUserPrincipalId("admin");
		edoEdoCommitteesReadyBuilder.setVersionNumber(1L);
		edoEdoCommitteesReadyBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		edoEdoCommitteesReadyBuilder.setCreateTime(DateTime.now());
		
		// Set GroupKey Object
		edoEdoCommitteesReadyBuilder.setGroupKey(HrGroupKey.Builder.create(HrGroupKeyBoTest.getTestHrGroupKey("ISU-IA")));
		testEdoCommitteesReadyBos.put(edoEdoCommitteesReadyBuilder.getEdoCommitteesReadyId(), edoEdoCommitteesReadyBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	EdoCommitteesReady immutable = EdoCommitteesReadyBoTest.getEdoCommitteesReady("1000");
    	EdoCommitteesReadyBo bo = EdoCommitteesReadyBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoCommitteesReadyBo.to(bo));
    }

    public static EdoCommitteesReady getEdoCommitteesReady(String edoCommitteesReady) {
        return testEdoCommitteesReadyBos.get(edoCommitteesReady);
    }
   
}
