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
package org.kuali.kpme.tklm.api.common;

/**
 * <p>WorkflowTagSupportContract interface</p>
 *
 */
public interface WorkflowTagSupportContract {
	
	/**
	 * The route action "R" associated with the TagSupportContract
	 * 
	 * <p>
	 * HrConstants.DOCUMENT_ACTIONS.ROUTE of a TagSupportContract
	 * <p>
	 * 
	 * @return R (HrConstants.DOCUMENT_ACTIONS.ROUTE) for TagSupportContract
	 */
    public String getRouteAction();
    
    /**
	 * The approve action "A" associated with the TagSupportContract
	 * 
	 * <p>
	 * HrConstants.DOCUMENT_ACTIONS.APPROVE of a TagSupportContract
	 * <p>
	 * 
	 * @return A (HrConstants.DOCUMENT_ACTIONS.APPROVE) for TagSupportContract
	 */
    public String getApproveAction();
    
    /**
   	 * The disapprove action "D" associated with the TagSupportContract
   	 * 
   	 * <p>
   	 * HrConstants.DOCUMENT_ACTIONS.DISAPPROVE of a TagSupportContract
   	 * <p>
   	 * 
   	 * @return D (HrConstants.DOCUMENT_ACTIONS.DISAPPROVE) for TagSupportContract
   	 */
    public String getDisapproveAction();

}
