package org.kuali.kpme.edo.api.group;

import org.kuali.kpme.core.api.mo.KpmeEffectiveKeyedDataTransferObject;

/**
 * <p>EdoGroupTrackingContract interface</p>
 *
 */
public interface EdoGroupTrackingContract extends KpmeEffectiveKeyedDataTransferObject {

	/**
	 * The identifier of the EdoGroupTracking
	 * 
	 * <p>
	 * edoGroupTrackingId of the EdoGroupTracking
	 * <p>
	 * 
	 * @return edoGroupTrackingId for EdoGroupTracking
	 */
    public String getEdoGroupTrackingId();

    /**
	 * The department id that the EdoGroupTracking is associated with
	 * 
	 * <p>
	 * departmentId of the EdoGroupTracking
	 * <p>
	 * 
	 * @return departmentId for EdoGroupTracking
	 */
    public String getDepartmentId();

    /**
	 * The organization code (school id) that the EdoGroupTracking is associated with
	 * 
	 * <p>
	 * organizationCode of the EdoGroupTracking
	 * <p>
	 * 
	 * @return organizationCode for EdoGroupTracking
	 */
    public String getOrganizationCode();

    /**
	 * The review level name that the EdoGroupTracking is associated with
	 * 
	 * <p>
	 * reviewLevelName of the EdoGroupTracking
	 * <p>
	 * 
	 * @return reviewLevelName for EdoGroupTracking
	 */
    public String getReviewLevelName();

    /**
	 * The group name that the EdoGroupTracking is associated with
	 * 
	 * <p>
	 * groupName of the EdoGroupTracking
	 * <p>
	 * 
	 * @return groupName for EdoGroupTracking
	 */
    public String getGroupName();
    
    /**
	 * The workflow id that the EdoGroupTracking is associated with
	 * 
	 * <p>
	 * edoWorkflowId of the EdoGroupTracking
	 * <p>
	 * 
	 * @return edoWorkflowId for EdoGroupTracking
	 */
    public String getEdoWorkflowId();
}
