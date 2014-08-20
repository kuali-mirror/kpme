package org.kuali.kpme.edo.group;

import org.kuali.kpme.edo.api.group.EdoGroupDefinition;
import org.kuali.kpme.edo.api.group.EdoRoleResponsibility;
import org.kuali.kpme.edo.api.group.EdoRoleResponsibilityContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 1/27/14
 * Time: 2:21 PM
 */
public class EdoRoleResponsibilityBo extends PersistableBusinessObjectBase implements EdoRoleResponsibilityContract {

	private static final long serialVersionUID = -2760119398952490275L;

	static class KeyFields {
		private static final String KIM_ROLE_NAME = "kimRoleName";
		private static final String KIM_RESPONSIBILITY_NAME = "kimResponsibilityName";
		private static final String KIM_ACTION_TYPE_CODE = "kimActionTypeCode";
	}
	
    private String edoKimRoleResponsibilityId;
    private String kimRoleName;
    private String kimResponsibilityName;
    private String kimActionTypeCode;
    private String kimActionPolicyCode;
    private int kimPriority;
    private boolean kimForceAction;
    
    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
			.add(KeyFields.KIM_ROLE_NAME)
			.add(KeyFields.KIM_RESPONSIBILITY_NAME)
			.add(KeyFields.KIM_ACTION_TYPE_CODE)
			.build();
    
    public String getId() {
		return  getEdoKimRoleResponsibilityId();
	}

	public void setId(String edoKimRoleResponsibilityId) {
		setEdoKimRoleResponsibilityId(edoKimRoleResponsibilityId);
	}

	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return  new ImmutableMap.Builder<String, Object>()
				.put(KeyFields.KIM_ROLE_NAME, this.getKimRoleName())
				.put(KeyFields.KIM_RESPONSIBILITY_NAME, this.getKimResponsibilityName())
				.put(KeyFields.KIM_ACTION_TYPE_CODE, this.getKimActionTypeCode())
				.build();
	}

    public String getEdoKimRoleResponsibilityId() {
        return edoKimRoleResponsibilityId;
    }

    public void setEdoKimRoleResponsibilityId(String edoKimRoleResponsibilityId) {
        this.edoKimRoleResponsibilityId = edoKimRoleResponsibilityId;
    }

    public String getKimRoleName() {
        return kimRoleName;
    }

    public void setKimRoleName(String kimRoleName) {
        this.kimRoleName = kimRoleName;
    }

    public String getKimResponsibilityName() {
        return kimResponsibilityName;
    }

    public void setKimResponsibilityName(String kimResponsibilityName) {
        this.kimResponsibilityName = kimResponsibilityName;
    }

    public String getKimActionTypeCode() {
        return kimActionTypeCode;
    }

    public void setKimActionTypeCode(String kimActionTypeCode) {
        this.kimActionTypeCode = kimActionTypeCode;
    }

    public String getKimActionPolicyCode() {
        return kimActionPolicyCode;
    }

    public void setKimActionPolicyCode(String kimActionPolicyCode) {
        this.kimActionPolicyCode = kimActionPolicyCode;
    }

    public int getKimPriority() {
        return kimPriority;
    }

    public void setKimPriority(int kimPriority) {
        this.kimPriority = kimPriority;
    }

    public boolean isKimForceAction() {
        return kimForceAction;
    }

    public void setKimForceAction(boolean kimForceAction) {
        this.kimForceAction = kimForceAction;
    }
    
    public static EdoRoleResponsibilityBo from(EdoRoleResponsibility im) {
        if (im == null) {
            return null;
        }
        EdoRoleResponsibilityBo err = new EdoRoleResponsibilityBo();
        err.setEdoKimRoleResponsibilityId(im.getEdoKimRoleResponsibilityId());
        err.setKimRoleName(im.getKimRoleName());
        err.setKimResponsibilityName(im.getKimResponsibilityName());
        err.setKimActionTypeCode(im.getKimActionTypeCode());
        err.setKimActionPolicyCode(im.getKimActionPolicyCode());
        err.setKimPriority(im.getKimPriority());        
        err.setKimForceAction(im.isKimForceAction());
        err.setVersionNumber(im.getVersionNumber());
        err.setObjectId(im.getObjectId()); 
        
        return err;
    } 
    
    public static EdoRoleResponsibility to(EdoRoleResponsibilityBo bo) {
        if (bo == null) {
            return null;
        }
        return EdoRoleResponsibility.Builder.create(bo).build();
    }
    
    public static final ModelObjectUtils.Transformer<EdoRoleResponsibilityBo, EdoRoleResponsibility> toImmutable = new ModelObjectUtils.Transformer<EdoRoleResponsibilityBo, EdoRoleResponsibility>() {
        public EdoRoleResponsibility transform(EdoRoleResponsibilityBo input) {
            return EdoRoleResponsibilityBo.to(input);
        };
    };
            
    public static final ModelObjectUtils.Transformer<EdoRoleResponsibility, EdoRoleResponsibilityBo> toBo = new ModelObjectUtils.Transformer<EdoRoleResponsibility, EdoRoleResponsibilityBo>() {
        public EdoRoleResponsibilityBo transform(EdoRoleResponsibility input) {
            return EdoRoleResponsibilityBo.from(input);
        };
    };
}
