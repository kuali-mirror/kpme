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
package org.kuali.hr.lm.timeoff.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;


public interface SystemScheduledTimeOffDao {

		/**
	 * Get SystemScheduledTimeOff from id
	 * @param lmSystemScheduledTimeOffId
	 * @return SystemScheduledTimeOff
	 */
	public SystemScheduledTimeOff getSystemScheduledTimeOff(String lmSystemScheduledTimeOffId);

	public List<SystemScheduledTimeOff> getSystemScheduledTimeOffForPayPeriod(
			String leavePlan, Date startDate, Date endDate);

	public SystemScheduledTimeOff getSystemScheduledTimeOffByDate(String leavePlan, Date startDate);

    List<SystemScheduledTimeOff> getSystemScheduledTimeOffs(Date fromEffdt, Date toEffdt, String earnCode, String fromAccruedDate, String toAccruedDate,
                                                            String fromSchTimeOffDate, String toSchTimeOffDate, String active, String showHist);
}
