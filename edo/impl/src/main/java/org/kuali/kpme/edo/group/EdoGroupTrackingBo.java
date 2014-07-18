package org.kuali.kpme.edo.group;

import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.edo.api.group.EdoGroupTracking;
import org.kuali.kpme.edo.api.group.EdoGroupTrackingContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 1/14/14
 * Time: 3:33 PM
 */
public class EdoGroupTrackingBo extends HrKeyedBusinessObject implements EdoGroupTrackingContract {

	private static final long serialVersionUID = 7851809535329762977L;
	
	static class KeyFields {
		private static final String GROUP_KEY_CODE = "groupKeyCode";
		private static final String DEPARTMENT_ID = "departmentId";
		private static final String ORGANIZATION_CODE = "organizationCode";
		private static final String WORKFLOW_ID = "edoWorkflowId";
	}
	
	private String edoGroupTrackingId;
    private String departmentId;
    private String organizationCode;
    private String reviewLevelName;
    private String groupName;
    private String edoWorkflowId;
    
    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
    		.add(KeyFields.GROUP_KEY_CODE)
    		.add(KeyFields.DEPARTMENT_ID)
    		.add(KeyFields.ORGANIZATION_CODE)
    		.add(KeyFields.WORKFLOW_ID)
			.build();
	
    @Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return  new ImmutableMap.Builder<String, Object>()
				.put(KeyFields.GROUP_KEY_CODE, this.getGroupKeyCode())
				.put(KeyFields.DEPARTMENT_ID, this.getDepartmentId())
				.put(KeyFields.ORGANIZATION_CODE, this.getOrganizationCode())
				.put(KeyFields.WORKFLOW_ID, this.getOrganizationCode())
				.build();
	}
    
    @Override
	public String getId() {
		return getEdoGroupTrackingId();
	}

	@Override
	public void setId(String edoGroupTrackingId) {
		setEdoGroupTrackingId(edoGroupTrackingId);
	}
	
	@Override
	protected String getUniqueKey() {
		return this.getEdoGroupTrackingId();
	}
    
    public String getEdoGroupTrackingId() {
        return edoGroupTrackingId;
    }

    public void setEdoGroupTrackingId(String edoGroupTrackingId) {
        this.edoGroupTrackingId = edoGroupTrackingId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getReviewLevelName() {
        return reviewLevelName;
    }

    public void setReviewLevelName(String reviewLevelName) {
        this.reviewLevelName = reviewLevelName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
 
    public String getEdoWorkflowId() {
		return edoWorkflowId;
	}

	public void setEdoWorkflowId(String edoWorkflowId) {
		this.edoWorkflowId = edoWorkflowId;
	}

	public static EdoGroupTrackingBo from(EdoGroupTracking im) {
        if (im == null) {
            return null;
        }
        EdoGroupTrackingBo egt = new EdoGroupTrackingBo();
        egt.setEdoGroupTrackingId(im.getEdoGroupTrackingId());
        egt.setDepartmentId(im.getDepartmentId());
        egt.setOrganizationCode(im.getOrganizationCode());
        egt.setReviewLevelName(im.getReviewLevelName());
        egt.setGroupName(im.getGroupName());
        egt.setEdoWorkflowId(im.getEdoWorkflowId());
        egt.setGroupKeyCode(im.getGroupKeyCode());        
        egt.setGroupKey(HrGroupKeyBo.from(im.getGroupKey()));

        // finally copy over the common fields into phra from im
        copyCommonFields(egt, im);
     
        return egt;
    } 
    
    public static EdoGroupTracking to(EdoGroupTrackingBo bo) {
        if (bo == null) {
            return null;
        }
        return EdoGroupTracking.Builder.create(bo).build();
    }
    
    public static final ModelObjectUtils.Transformer<EdoGroupTrackingBo, EdoGroupTracking> toImmutable = new ModelObjectUtils.Transformer<EdoGroupTrackingBo, EdoGroupTracking>() {
        public EdoGroupTracking transform(EdoGroupTrackingBo input) {
            return EdoGroupTrackingBo.to(input);
        };
    };
            
    public static final ModelObjectUtils.Transformer<EdoGroupTracking, EdoGroupTrackingBo> toBo = new ModelObjectUtils.Transformer<EdoGroupTracking, EdoGroupTrackingBo>() {
        public EdoGroupTrackingBo transform(EdoGroupTracking input) {
            return EdoGroupTrackingBo.from(input);
        };
    };
}
