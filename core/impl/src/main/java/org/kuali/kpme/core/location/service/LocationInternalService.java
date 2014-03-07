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
package org.kuali.kpme.core.location.service;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.location.LocationContract;
import org.kuali.kpme.core.location.LocationBo;

public interface LocationInternalService {

    /**
     * Get location by unique id
     * @param hrLocationId
     * @return
     */
    public LocationBo getLocationWithRoleData(String hrLocationId);


    /**
     * Get location as of a particular date
     * @param location
     * @param asOfDate
     * @return {@link LocationContract}
     */
    public LocationBo getLocationWithRoleData(String location, LocalDate asOfDate);
}
