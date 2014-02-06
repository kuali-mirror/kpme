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
package org.kuali.kpme.tklm.common;

import org.kuali.kpme.core.web.KPMEForm;

public class DeleteDocumentForm extends KPMEForm {

	private static final long serialVersionUID = 3121271193055358638L;
	
	private String deleteDocumentId;
	private String message;

	public String getDeleteDocumentId() {
		return deleteDocumentId;
	}

	public void setDeleteDocumentId(String deleteDocumentId) {
		this.deleteDocumentId = deleteDocumentId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
