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
package org.kuali.kpme.edo.reviewernote;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.edo.api.reviewernote.EdoReviewerNote;

public class EdoReviewerNoteBoTest {
	private static Map<String, EdoReviewerNote> testEdoReviewerNoteBos;
	public static EdoReviewerNote.Builder edoReviewerNoteBoBuilder = EdoReviewerNote.Builder.create();
	
	static {
		testEdoReviewerNoteBos = new HashMap<String, EdoReviewerNote>();
		edoReviewerNoteBoBuilder.setEdoReviewerNoteId("1000");
		edoReviewerNoteBoBuilder.setEdoDossierId("1000");
		edoReviewerNoteBoBuilder.setNote("Testing Immutable EdoReviewerNote Note");
		edoReviewerNoteBoBuilder.setUserPrincipalId("admin");
		edoReviewerNoteBoBuilder.setCreatedAtVal(new Timestamp(2014, 3, 1, 0, 0, 0, 0));
		edoReviewerNoteBoBuilder.setReviewDateVal(new Date(2014, 3, 1));
		edoReviewerNoteBoBuilder.setVersionNumber(1L);
		edoReviewerNoteBoBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		
		testEdoReviewerNoteBos.put(edoReviewerNoteBoBuilder.getEdoReviewerNoteId(), edoReviewerNoteBoBuilder.build());
	}
	
    @Test
    public void testNotEqualsWithGroup() {
    	EdoReviewerNote immutable = EdoReviewerNoteBoTest.getEdoReviewerNote("1000");
    	EdoReviewerNoteBo bo = EdoReviewerNoteBo.from(immutable);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, EdoReviewerNoteBo.to(bo));
    }

    public static EdoReviewerNote getEdoReviewerNote(String edoReviewerNote) {
        return testEdoReviewerNoteBos.get(edoReviewerNote);
    }
   
}
