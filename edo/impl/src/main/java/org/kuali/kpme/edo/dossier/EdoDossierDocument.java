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
package org.kuali.kpme.edo.dossier;

import java.sql.Timestamp;
import java.util.Date;

import org.kuali.kpme.edo.api.dossier.EdoDossierDocumentContract;
import org.kuali.rice.krad.document.TransactionalDocumentBase;

public class EdoDossierDocument extends TransactionalDocumentBase implements EdoDossierDocumentContract {

	private static final long serialVersionUID = -8759488155644037099L;
	
	private String edoDossierId;
	private String documentTypeName;
	private EdoDossierBo edoDossier = new EdoDossierBo();

    //private transient HrGroupKeyBo groupKey;
    //private transient DepartmentBo departmentObj;
	
	public EdoDossierBo getEdoDossier() {
		return edoDossier;
	}

	public void setEdoDossier(EdoDossierBo edoDossier) {
		this.edoDossier = edoDossier;
	}
	
	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}

	public String getEdoDossierId() {
		return edoDossierId;
	}

	public void setEdoDossierId(String edoDossierId) {
		this.edoDossierId = edoDossierId;
	}

    //helper methods!!!!
    public String getCandidatePrincipalName() {
        return edoDossier.getCandidatePrincipalName();
    }

    public String getCandidatePrincipalId() {
        return edoDossier.getCandidatePrincipalId();
    }
    
    public String getEdoDossierTypeId() {
		return edoDossier.getEdoDossierTypeId();
	}
    
    public String getEdoChecklistId() {
		return edoDossier.getEdoChecklistId();
	}
    
    public String getAoeCode() {
        return edoDossier.getAoeCode();
    }
    
    public String getDepartmentID() {
        return edoDossier.getDepartmentID();
    }
    
    public String getSecondaryUnit() {
		return edoDossier.getSecondaryUnit();
	}

    public String getOrganizationCode() {
		return edoDossier.getOrganizationCode();
	}
    
    public String getCurrentRank() {
        return edoDossier.getCurrentRank();
    }
    
    public String getRankSought() {
        return edoDossier.getRankSought();
    }
    
    public String getDossierStatus() {
        return edoDossier.getDossierStatus();
    }
    
    public String getWorkflowId() {
        return edoDossier.getWorkflowId();
    }

    
    
    
    public String getGroupKeyCode() {
        return  edoDossier.getGroupKeyCode();
    }

    public Date getActionDateTime() {
        return null;
    }

    public Timestamp getTimestamp() {
        return edoDossier.getTimestamp();
    }

    /*public String getDepartment() {
        return missedPunch.getDepartment();
    }*/

    /*public Date getRelativeEffectiveDate() {
        return missedPunch.getRelativeEffectiveDate();
    }*/
    /*
    public HrGroupKeyBo getGroupKey() {
        return groupKey;
    }
   
    
    public DepartmentBo getDepartmentObj() {
        return departmentObj;
    }*/
    
    
}
