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

package org.kuali.kpme.pm.api.positionreporttype.service;



import java.util.List;



import org.joda.time.LocalDate;

import org.kuali.kpme.pm.api.positionreporttype.PositionReportType;



public interface PositionReportTypeService {

	

	/**

	 * Retrieve the PositionReportType with given id

	 * @param pmPositionReportTypeId

	 * @return

	 */

	public PositionReportType getPositionReportTypeById(String pmPositionReportTypeId);



	/**

	 * Retrieve the latest active Prosition Report Type with given positionReportType and effective date

	 * @param positionReportType

	 * @param asOfDate

	 * @return

	 */

	public PositionReportType getPositionReportType(String positionReportType, LocalDate asOfDate);

	

	/**

	 * Get list of PositionReportType with given type, groupKeyCode and effective date

	 * wild card allowed

	 * @param positionReportType

	 * @param groupKeyCode

	 * @param asOfDate

	 * @return

	 */

	public List<PositionReportType> getPositionReportTypeList(String positionReportType, String groupKeyCode, LocalDate asOfDate);

	

	/**

	 * Retrieve list of position report type by given positionReportType 

	 * @param positionReportType

	 * @return

	 */

	public List<PositionReportType> getPositionReportTypeListByType(String positionReportType);

	

	/**

	 * Retrieve list of Position Report Type with given institutionCode and effectiveDate

	 * @param institutionCode

	 * @param asOfDate

	 * @return

	 */

	public List<PositionReportType> getPrtListWithInstitutionCodeAndDate(String institutionCode, LocalDate asOfDate);

	

	/**

	 * Retrieve list of Position Report Type with given location and effectiveDate

	 * @param location

	 * @param asOfDate

	 * @return

	 */

	public List<PositionReportType> getPrtListWithLocationAndDate(String location, LocalDate asOfDate);

	

}

