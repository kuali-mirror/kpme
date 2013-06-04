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
package org.kuali.kpme.pm.utils;

import org.kuali.kpme.core.util.HrTestConstants;

public final class PmTestConstants {
	
	private static final String BASE_URL = HrTestConstants.BASE_URL;
	
	public static class Urls {
		
		public static final String POSITION_REPORT_TYPE_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.pm.positionreporttype.PositionReportType&methodToCall=start";
		
		public static final String POSITION_REPORT_GROUP_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.pm.positionreportgroup.PositionReportGroup&methodToCall=start";
		
		public static final String POSITION_REPORT_CAT_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.pm.positionreportcat.PositionReportCategory&methodToCall=start";
		
		public static final String POSITION_REPORT_SUB_CAT_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.pm.positionreportsubcat.PositionReportSubCategory&methodToCall=start";
		
	}

}
