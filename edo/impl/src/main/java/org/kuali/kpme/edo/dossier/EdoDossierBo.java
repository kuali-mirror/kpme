package org.kuali.kpme.edo.dossier;

import java.util.Date;

import org.joda.time.DateTime;
import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.edo.api.dossier.EdoDossier;
import org.kuali.kpme.edo.api.dossier.EdoDossierContract;
import org.kuali.kpme.edo.api.dossier.type.EdoDossierType;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;


public class EdoDossierBo extends HrKeyedBusinessObject implements EdoDossierContract {

	static class KeyFields {
		private static final String CANDIDATE_PRINCIPAL_NAME = "candidatePrincipalName";
	}
	
	private static final long serialVersionUID = 6843318899816055301L;
	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(KeyFields.CANDIDATE_PRINCIPAL_NAME)
            .build();
	
	//public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "PrincipalHRAttributes";

	public static final ModelObjectUtils.Transformer<EdoDossierBo, EdoDossier> toImmutable = new ModelObjectUtils.Transformer<EdoDossierBo, EdoDossier>() {
		public EdoDossier transform(EdoDossierBo input) {
			return EdoDossierBo.to(input);
		};
	};

	public static final ModelObjectUtils.Transformer<EdoDossier, EdoDossierBo> toBo = new ModelObjectUtils.Transformer<EdoDossier, EdoDossierBo>() {
		public EdoDossierBo transform(EdoDossier input) {
			return EdoDossierBo.from(input);
		};
	};
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
			.put(KeyFields.CANDIDATE_PRINCIPAL_NAME, this.getCandidatePrincipalName())
			.build();
	}
	
	@Override
	public String getId() {
		return this.getEdoDossierId();
	}
	@Override
	public void setId(String id) {
		setEdoDossierId(id);
	}
		
	@Override
	public String getUniqueKey() {
		
		return getCandidatePrincipalName();
	}
	
    private String edoDossierId;
    private String edoDossierTypeId;
    private String edoChecklistId;
    private String candidatePrincipalName;
    private String aoeCode;
    private String departmentID;
    private String organizationCode;
    private String currentRank;
    private String rankSought;
    private Date dueDateVal;
    private String dossierStatus;
    private String secondaryUnit;
    private String workflowId;

    private EdoDossierType edoDossierType;
   
	

	public String getEdoDossierId() {
		return edoDossierId;
	}

	public void setEdoDossierId(String edoDossierId) {
		this.edoDossierId = edoDossierId;
	}

	public String getEdoDossierTypeId() {
		return edoDossierTypeId;
	}

	public void setEdoDossierTypeId(String edoDossierTypeId) {
		this.edoDossierTypeId = edoDossierTypeId;
	}

	public String getEdoChecklistId() {
		return edoChecklistId;
	}

	public void setEdoChecklistId(String edoChecklistId) {
		this.edoChecklistId = edoChecklistId;
	}

	public String getCandidatePrincipalName() {
		return candidatePrincipalName;
	}

	public void setCandidatePrincipalName(String candidatePrincipalName) {
		this.candidatePrincipalName = candidatePrincipalName;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public void setEdoDossierType(EdoDossierType edoDossierType) {
		this.edoDossierType = edoDossierType;
	}

	public EdoDossierType getEdoDossierType() {
        if (ObjectUtils.isNull(edoDossierType) && ObjectUtils.isNotNull(edoDossierTypeId)) {
            this.edoDossierType = EdoServiceLocator.getEdoDossierTypeService().getEdoDossierTypeById(edoDossierTypeId.toString());
            return edoDossierType;
        } else {
            return this.edoDossierType;
        }
    }

    public String getAoeCode() {
        return aoeCode;
    }

    public String getAoeString() {
        return EdoConstants.AREA_OF_EXCELLENCE.get(this.aoeCode);
    }
    
    public void setAoeCode(String aoeCode) {
        this.aoeCode = aoeCode;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getCurrentRank() {
        return currentRank;
    }

    public void setCurrentRank(String currentRank) {
        this.currentRank = currentRank;
    }

    public String getRankSought() {
        return rankSought;
    }

    public void setRankSought(String rankSought) {
        this.rankSought = rankSought;
    }

    public DateTime getDueDate() {
        return dueDateVal == null ? null : new DateTime(dueDateVal);
    }

    public void setDueDateVal(Date dueDateVal) {
        this.dueDateVal = dueDateVal;
    }

    public String getDossierStatus() {
        return dossierStatus;
    }

    public void setDossierStatus(String dossierStatus) {
        this.dossierStatus = dossierStatus;
    }
  
    public String getSecondaryUnit() {
		return secondaryUnit;
	}

	public void setSecondaryUnit(String secondaryUnit) {
		this.secondaryUnit = secondaryUnit;
	}

	public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getCandidatePrincipalId() {
    	Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(this.getCandidatePrincipalName());
    	if(principal == null) {
    		throw new IllegalArgumentException("Principal not found in KIM");
    	}
    	return principal.getPrincipalId();
    }

    public static EdoDossierBo from(EdoDossier edoDossier) {
        if (edoDossier == null) {
            return null;
        }
        EdoDossierBo edoDossierBo = new EdoDossierBo();
        
        edoDossierBo.setEdoDossierId(edoDossier.getEdoDossierId());
        edoDossierBo.setEdoDossierTypeId(edoDossier.getEdoDossierTypeId());
        edoDossierBo.setEdoChecklistId(edoDossier.getEdoChecklistId());
        edoDossierBo.setCandidatePrincipalName(edoDossier.getCandidatePrincipalName());
        edoDossierBo.setAoeCode(edoDossier.getAoeCode());
        edoDossierBo.setDepartmentID(edoDossier.getDepartmentID());
        edoDossierBo.setSecondaryUnit(edoDossier.getSecondaryUnit());
        edoDossierBo.setOrganizationCode(edoDossier.getOrganizationCode());
        edoDossierBo.setCurrentRank(edoDossier.getCurrentRank());
        edoDossierBo.setRankSought(edoDossier.getRankSought());
        edoDossierBo.setDueDateVal(edoDossier.getDueDate() == null ? null : edoDossier.getDueDate().toDate());
        edoDossierBo.setDossierStatus(edoDossier.getDossierStatus());
        edoDossierBo.setWorkflowId(edoDossier.getWorkflowId());
        
        edoDossierBo.setGroupKeyCode(edoDossier.getGroupKeyCode());
        edoDossierBo.setGroupKey(HrGroupKeyBo.from(edoDossier.getGroupKey()));
        
        // finally copy over the common fields into edoDossierBo from im
        copyCommonFields(edoDossierBo, edoDossier);

        return edoDossierBo;
    }

    public static EdoDossier to(EdoDossierBo bo) {
        if (bo == null) {
            return null;
        }

        return EdoDossier.Builder.create(bo).build();
    }
    
}
