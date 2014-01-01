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

package org.kuali.kpme.pm.api.positionreportgroup.service;



import java.util.List;



import org.joda.time.LocalDate;

import org.kuali.kpme.pm.api.positionreportgroup.PositionReportGroupContract;



public interface PositionReportGroupService {

	/**

	 * retrieve the PositionReportGroup with given id

	 * @param pmPositionReportGroupId

	 * @return

	 */

	public PositionReportGroupContract getPositionReportGroupById(String pmPositionReportGroupId);

	

	/**

	 * Get list of PositionReportGroup with given group, institution, location and effective date

	 * wild card allowed

	 * @param positionReportGroup

	 * @param institution

	 * @param location

	 * @param asOfDate

	 * @return

	 */

	public List<? extends PositionReportGroupContract> getPositionReportGroupList(String positionReportGroup, String institution, String location, LocalDate asOfDate);

	

	/**

	 * Retrieve the latest active PositionReportGroup with given positionReportGroup and effective date

	 * @param positionReportGroup

	 * @param asOfDate

	 * @return

	 */

	public PositionReportGroupContract getPositionReportGroup(String positionReportGroup, LocalDate asOfDate);

}

