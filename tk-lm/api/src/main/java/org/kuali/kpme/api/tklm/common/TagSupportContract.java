/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.api.tklm.common;

import java.util.List;
import java.util.Map;

/**
 * <p>TagSupportContract interface</p>
 *
 */
public interface TagSupportContract {
	
	/**
	 * The principalId associated with the TagSupportContract
	 * 
	 * <p>
	 * principalId of a TagSupportContract
	 * <p>
	 * 
	 * @return principalId for TagSupportContract
	 */
    public String getPrincipalId();

    /**
	 * The document route status associated with the TagSupportContract
	 * 
	 * <p>
	 * HrConstants.DOC_ROUTE_STATUS of a TagSupportContract
	 * <p>
	 * 
	 * @return HrConstants.DOC_ROUTE_STATUS for TagSupportContract
	 */
    public Map<String, String> getDocumentStatus();


    /**
	 * The list of ip addresse associated with the TagSupportContract
	 * 
	 * <p>
	 * ipAddresses of a TagSupportContract
	 * <p>
	 * 
	 * @return ipAddresses for TagSupportContract
	 */
    public List<String> getIpAddresses();

    /**
	 * The principal full name associated with the TagSupportContract
	 * 
	 * <p>
	 * If EntityNamePrincipalName based on principalId is not null, it composites full name
	 * <p>
	 * 
	 * @return the default name for principalId for TagSupportContract or null
	 */
    public String getPrincipalFullName();

}
