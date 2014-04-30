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
package org.kuali.khr.hub.util;


public class KhrTestConstants {

	public static String BASE_URL = "http://localhost:8080/kpme-dev";

	public static class Urls {
		public static final String CLOCK_PAGE_URL = BASE_URL + "/Clock.do";
		public static final String TIME_DETAIL_PAGE_URL = BASE_URL + "/TimeDetail.do";
		public static final String LOGOUT_URL = BASE_URL + "/logout.do";
		public static final String POSITION_LOOKUP_URL = BASE_URL
				+ "/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.position.PositionBo";
		public static final String POSITION_REPORT_GROUP_SUB_CATEGORY_LOOKUP_URL = BASE_URL
				+ "/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.pstnrptgrpsubcat.PositionReportGroupSubCategoryBo&showMaintenanceLinks=true";
		public static final String LEAVE_CALENDAR_URL = BASE_URL + "/LeaveCalendar.do";

		

	}	


}