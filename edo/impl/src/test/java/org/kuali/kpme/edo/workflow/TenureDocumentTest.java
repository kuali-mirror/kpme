package org.kuali.kpme.edo.workflow;

import org.junit.Test;
import org.kuali.kpme.edo.EdoUnitTestBase;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;

public class TenureDocumentTest extends EdoUnitTestBase {
	
	@Test
	public void testSuppmentalRouteModule() throws Exception {
		
		WorkflowDocument wfd = WorkflowDocumentFactory.createDocument(testDossier.getCandidatePrincipalId(), "TenureProcessDocument");
		wfd.route("routing supp material doc");
	}
	

}
