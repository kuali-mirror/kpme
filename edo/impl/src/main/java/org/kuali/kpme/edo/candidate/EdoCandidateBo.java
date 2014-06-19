package org.kuali.kpme.edo.candidate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.edo.api.candidate.EdoCandidate;
import org.kuali.kpme.edo.api.candidate.EdoCandidateContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kim.api.identity.Person;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * Date: 06/07/14
 */
	
public class EdoCandidateBo extends HrKeyedBusinessObject implements EdoCandidateContract {
	
	static class KeyFields {
		private static final String PRINCIPAL_NAME = "principalName";
	}
	
	private static final long serialVersionUID = 6843318899816055301L;
	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(KeyFields.PRINCIPAL_NAME)
            .build();
	
	//public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "PrincipalHRAttributes";

	public static final ModelObjectUtils.Transformer<EdoCandidateBo, EdoCandidate> toImmutable = new ModelObjectUtils.Transformer<EdoCandidateBo, EdoCandidate>() {
		public EdoCandidate transform(EdoCandidateBo input) {
			return EdoCandidateBo.to(input);
		};
	};

	public static final ModelObjectUtils.Transformer<EdoCandidate, EdoCandidateBo> toBo = new ModelObjectUtils.Transformer<EdoCandidate, EdoCandidateBo>() {
		public EdoCandidateBo transform(EdoCandidate input) {
			return EdoCandidateBo.from(input);
		};
	};
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
			.put(KeyFields.PRINCIPAL_NAME, this.getPrincipalName())
			.build();
	}
	
	
	@Override
	public String getUniqueKey() {
		return getPrincipalName();

	}
	     
	private String edoCandidateID;
    private String lastName ;
    private String firstName;
    private String principalName;
    private String primaryDeptID ;
    private String tnpDeptID;
    private String candidacySchool;
    private String candidacyCampus;
    
    private transient Person principal;
    @Override
	public String getId() {
		return this.getEdoCandidateID();
	}
	@Override
	public void setId(String id) {
		setEdoCandidateID(id);
	}

	public Person getPrincipal() {
		return principal;
	}

	public void setPrincipal(Person principal) {
		this.principal = principal;
	}

    public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getEdoCandidateID() {
        return edoCandidateID;
    }

    public void setEdoCandidateID(String edoCandidateID) {
        this.edoCandidateID = edoCandidateID;
    }

    public String getPrimaryDeptID() {
        return primaryDeptID;
    }

    public void setPrimaryDeptID(String primaryDeptID) {
        this.primaryDeptID = primaryDeptID;
    }

    public String getTnpDeptID() {
        return tnpDeptID;
    }

    public void setTnpDeptID(String tnpDeptID) {
        this.tnpDeptID = tnpDeptID;
    }

    public String getCandidacySchool() {
        return candidacySchool;
    }

    public void setCandidacySchool(String candidacySchool) {
        this.candidacySchool = candidacySchool;
    }

    public String getCandidacyCampus() {
        return candidacyCampus;
    }

    public void setCandidacyCampus(String candidacyCampus) {
        this.candidacyCampus = candidacyCampus;
    }

    public String getCandidateJSONString() {
        ArrayList<String> tmp = new ArrayList<String>();
        Type tmpType = new TypeToken<List<String>>() {}.getType();
        Gson gson = new Gson();

        tmp.add(this.getEdoCandidateID().toString());
        tmp.add(this.getFirstName() + " " + this.getLastName());
        tmp.add(this.getLastName());
        tmp.add(this.getFirstName());
        tmp.add(this.getPrincipalName());
        tmp.add(this.getPrimaryDeptID());
        tmp.add(this.getCandidacySchool());
        return gson.toJson(tmp, tmpType).toString();
    }
    
    public static EdoCandidateBo from(EdoCandidate edoCandidate) {
        if (edoCandidate == null) {
            return null;
        }
        EdoCandidateBo edoCandidateBo = new EdoCandidateBo();
        
        edoCandidateBo.setEdoCandidateID(edoCandidate.getEdoCandidateID());
        edoCandidateBo.setPrincipalName(edoCandidate.getPrincipalName());
        edoCandidateBo.setLastName(edoCandidate.getLastName());
        edoCandidateBo.setFirstName(edoCandidate.getFirstName());
        edoCandidateBo.setPrimaryDeptID(edoCandidate.getPrimaryDeptID());
        edoCandidateBo.setTnpDeptID(edoCandidate.getTnpDeptID());
        edoCandidateBo.setCandidacySchool(edoCandidate.getCandidacySchool());
        
        edoCandidateBo.setGroupKeyCode(edoCandidate.getGroupKeyCode());
        edoCandidateBo.setGroupKey(HrGroupKeyBo.from(edoCandidate.getGroupKey()));
        
        
        
     
        // finally copy over the common fields into edoCandidateBo from im
        copyCommonFields(edoCandidateBo, edoCandidate);

        return edoCandidateBo;
    }

    public static EdoCandidate to(EdoCandidateBo bo) {
        if (bo == null) {
            return null;
        }

        return EdoCandidate.Builder.create(bo).build();
    }

}
