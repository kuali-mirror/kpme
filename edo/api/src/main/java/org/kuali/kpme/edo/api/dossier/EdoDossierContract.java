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
package org.kuali.kpme.edo.api.dossier;

import java.util.Date;

import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;
import org.kuali.kpme.core.api.util.HrApiConstants;


/**
 * <p>EdoCandidateContract interface</p>
 *
 */
public interface EdoDossierContract extends KpmeEffectiveDataTransferObject {
	
	public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "EdoDossierTypeContract";

	public String getEdoDossierID();

	public String getEdoDossierTypeID();

	public String getEdoChecklistID();

	public String getCandidatePrincipalname();

	public String getOrganizationCode();

	/*public void setEdoDossierType(EdoDossierType edoDossierType) {
		this.edoDossierType = edoDossierType;
	}

	public EdoDossierType getEdoDossierType() {
        if (ObjectUtils.isNull(edoDossierType) && ObjectUtils.isNotNull(edoDossierTypeID)) {
            this.edoDossierType = EdoServiceLocator.getEdoDossierTypeService().getEdoDossierTypeById(edoDossierTypeID.toString());
            return edoDossierType;
        } else {
            return this.edoDossierType;
        }
    }*/

    public String getAoeCode();

    public String getDepartmentID();
    
    public String getCurrentRank();

    public String getRankSought();

    public Date getDueDate();

    public String getDossierStatus();
  
    public String getSecondaryUnit();

	public String getWorkflowId();
    
}