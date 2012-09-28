/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.authorization;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.rice.krad.bo.BusinessObject;

public abstract class TkAuthorizedLookupableHelperBase extends HrEffectiveDateActiveLookupableHelper {

    @Override
    /**
     * Overridden single point where the Lookup methods call to obtain Business
     * Objects. We scan this list and remove anything that the user does not
     * have access to.
     */
    protected List<? extends BusinessObject> getSearchResultsHelper(Map<String, String> fieldValues, boolean unbounded) {
        List<? extends BusinessObject> list = super.getSearchResultsHelper(fieldValues, unbounded);
        List<BusinessObject> reduced = new LinkedList<BusinessObject>();

        for (BusinessObject bo : list) {
            if (shouldShowBusinessObject(bo)) {
                reduced.add(bo);
            }
        }

        return reduced;
    }

    /**
     * Subclasses implement this method to restrict the business objects
     * that will be shown on the lookup/inquiry pages. Maintenance Authorization
     * classes will handle the determination of whether or not this object can
     * be edited.
     *
     * @param bo The business object to examine.
     * @return true if the current user can see this business object.
     */
    abstract public boolean shouldShowBusinessObject(BusinessObject bo);
}
