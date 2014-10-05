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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.edo.EdoUnitTestBase;
import org.kuali.kpme.edo.api.reviewernote.EdoReviewerNote;
import org.kuali.kpme.edo.service.EdoServiceLocator;

@IntegrationTest
public class EdoReviewerNoteServiceTest extends EdoUnitTestBase {

	private final String edoReviewerNoteId = "1000"; 
	LocalDate asOfDate = new LocalDate(2012,1,1);

	@Before
	public void setUp() throws Exception {
	super.setUp();
	
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testGetReviewerNoteById() throws Exception {

		EdoReviewerNote edoReviewerNote = EdoServiceLocator.getEdoReviewerNoteService().getReviewerNoteById(edoReviewerNoteId);
		assertEquals("1000", edoReviewerNote.getEdoReviewerNoteId());
	}
	

	@Test
	public void testGetReviewerNotesByDossierId() throws Exception {

		List<EdoReviewerNote> edoReviewerNotes = EdoServiceLocator.getEdoReviewerNoteService().getReviewerNotesByDossierId("1000");
		assertEquals("returned the correct number of results", 4, edoReviewerNotes.size());
	}

	@Test
	public void testGetReviewerNotesByUserPrincipalId() throws Exception {

		List<EdoReviewerNote> edoReviewerNotes = EdoServiceLocator.getEdoReviewerNoteService().getReviewerNotesByUserPrincipalId("reviewChair");
		assertEquals("returned the correct number of results", 2, edoReviewerNotes.size());
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		// get original bo
		EdoReviewerNote edoReviewerNote = EdoServiceLocator.getEdoReviewerNoteService().getReviewerNoteById(edoReviewerNoteId);
		assertEquals("reviewer note1", edoReviewerNote.getNote());
		// modify the retrieved bo
		EdoReviewerNoteBo edoReviewerNoteBo = EdoReviewerNoteBo.from(edoReviewerNote);
		edoReviewerNoteBo.setNote("Revised Reviewer note1");
		EdoServiceLocator.getEdoReviewerNoteService().saveOrUpdate(EdoReviewerNoteBo.to(edoReviewerNoteBo));
		// check if the bo has been updated
		edoReviewerNote = EdoServiceLocator.getEdoReviewerNoteService().getReviewerNoteById(edoReviewerNoteId);
		assertEquals("Revised Reviewer note1", edoReviewerNote.getNote());
	}
	
}