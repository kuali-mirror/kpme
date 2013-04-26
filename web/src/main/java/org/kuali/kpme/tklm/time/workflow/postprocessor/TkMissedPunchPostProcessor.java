/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.workflow.postprocessor;

import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.framework.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.postprocessor.DefaultPostProcessor;

public class TkMissedPunchPostProcessor extends DefaultPostProcessor {

	@Override
	public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) throws Exception {
		ProcessDocReport pdr = super.doRouteStatusChange(statusChangeEvent);

		/*Long documentId = statusChangeEvent.getRouteHeaderId();

        DocumentHeader header = KNSServiceLocator.getDocumentHeaderService().getDocumentHeaderById(documentId.toString());*/
		return pdr;
	}

}
