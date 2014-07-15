package org.kuali.kpme.edo.web;

import org.junit.Test;
import org.kuali.kpme.edo.EdoIntegrationTestCaseBase;

public class EdoIndexIT extends EdoIntegrationTestCaseBase {

    @Test
    public void testStartSelenium() throws Exception {
    	getSelenium().open("http://localhost:4444/edo-dev/EdoIndex.do?");
    }
}
