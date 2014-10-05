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
package org.kuali.kpme.edo.dossier.web;

import org.kuali.kpme.edo.dossier.EdoDossierBo;
import org.kuali.rice.krad.web.form.TransactionalDocumentFormBase;

public class EdoDossierForm extends TransactionalDocumentFormBase {

	private static final long serialVersionUID = -5511083730204963887L;
	
	private EdoDossierBo edoDossier = new EdoDossierBo();
	private String documentTypeName;
	
	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentType) {
		this.documentTypeName = documentType;
	}

	@Override
	public String getDocTypeName() {
		return "EdoDossierDocumentType";
	}

	public EdoDossierBo getEdoDossier() {
		return edoDossier;
	}

	public void setEdoDossier(EdoDossierBo edoDossier) {
		this.edoDossier = edoDossier;
	}


}
