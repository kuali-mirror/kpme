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
package org.kuali.kpme.edo.vote;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.edo.api.vote.EdoVoteRecord;

public class EdoVoteRecordBoTest {
	private static Map<String, EdoVoteRecord> testEdoVoteRecordBo;
	
	public static EdoVoteRecord.Builder edoVoteRecordBuilder = EdoVoteRecord.Builder.create();
	
	static {
		testEdoVoteRecordBo = new HashMap<String, EdoVoteRecord>();
		
		edoVoteRecordBuilder.setEdoVoteRecordId("1000");
		edoVoteRecordBuilder.setEdoDossierId("1000");
		edoVoteRecordBuilder.setEdoReviewLayerDefinitionId("1000");;
		edoVoteRecordBuilder.setVoteType("tenure");
		edoVoteRecordBuilder.setAoeCode("P");
		edoVoteRecordBuilder.setYesCount(10);
		edoVoteRecordBuilder.setNoCount(1);
		edoVoteRecordBuilder.setAbsentCount(0);
		edoVoteRecordBuilder.setAbstainCount(0);
		edoVoteRecordBuilder.setVoteRound(1);
		edoVoteRecordBuilder.setVoteSubRound(1);
		edoVoteRecordBuilder.setCreatedAt(new DateTime(2014, 3, 1, 0, 0));
		edoVoteRecordBuilder.setUpdatedAt(new DateTime(2014, 3, 1, 0, 0));
		edoVoteRecordBuilder.setCreatedBy("admin");
		edoVoteRecordBuilder.setVersionNumber(1L);
		edoVoteRecordBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		
		
		testEdoVoteRecordBo.put(edoVoteRecordBuilder.getEdoVoteRecordId(), edoVoteRecordBuilder.build());
	}
	
	@Test
    public void testNotEqualsWithGroup() {
		EdoVoteRecord immutable = EdoVoteRecordBoTest.getEdoVoteRecord("1000");
		EdoVoteRecordBo bo = EdoVoteRecordBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoVoteRecordBo.to(bo));
    }

    public static EdoVoteRecord getEdoVoteRecord(String edoVoteRecordId) {
        return testEdoVoteRecordBo.get(edoVoteRecordId);
    }
    
}
