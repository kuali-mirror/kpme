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
package org.kuali.kpme.tklm.api.time.timehourdetail;



/**
 * <p>TimeHourDetailRendererContract interface</p>
 *
 */
public interface TimeHourDetailRendererContract {
	
	/**
	 * The TimeHourDetail object associated with the TimeHourDetailRenderer
	 * 
	 * <p>
	 * timeHourDetail of a TimeHourDetailRenderer
	 * <p>
	 * 
	 * @return timeHourDetail for TimeHourDetailRenderer
	 */
    public TimeHourDetailContract getTimeHourDetail();

    /**
	 * The id of the TimeHourDetail object associated with the TimeHourDetailRenderer
	 * 
	 * <p>
	 * timeHourDetail.getTkTimeHourDetailId() of a TimeHourDetailRenderer
	 * <p>
	 * 
	 * @return timeHourDetail.getTkTimeHourDetailId() for TimeHourDetailRenderer
	 */
    public String getTkTimeHourDetailId();

    /**
	 * The EarnCode name of the TimeHourDetail object associated with the TimeHourDetailRenderer
	 * 
	 * <p>
	 * timeHourDetail.getEarnCode() of a TimeHourDetailRenderer
	 * <p>
	 * 
	 * @return timeHourDetail.getEarnCode() for TimeHourDetailRenderer
	 */
    public String getTitle();

    /**
	 * The hours of the TimeHourDetail object associated with the TimeHourDetailRenderer
	 * 
	 * <p>
	 * timeHourDetail.getHours().toString() of a TimeHourDetailRenderer
	 * <p>
	 * 
	 * @return timeHourDetail.getHours().toString() for TimeHourDetailRenderer
	 */
    public String getHours();

    /**
	 * The amount of the TimeHourDetail object associated with the TimeHourDetailRenderer
	 * 
	 * <p>
	 * timeHourDetail.getAmount().toString() of a TimeHourDetailRenderer
	 * <p>
	 * 
	 * @return timeHourDetail.getAmount().toString() for TimeHourDetailRenderer
	 */
    public String getAmount();
    
    /**
	 * The holiday description associated with the TimeHourDetailRenderer
	 * 
	 * <p>
	 * holiday description of a TimeHourDetailRenderer
	 * <p>
	 * 
	 * @return holiday description for TimeHourDetailRenderer
	 */
    public String getHolidayName();

    /**
     * TODO: Put a better comment
	 * The overtimeEarnCode flag associated with the TimeHourDetailRenderer
	 * 
	 * <p>
	 * overtimeEarnCode flag of a TimeHourDetailRenderer
	 * <p>
	 * 
	 * @return overtimeEarnCode flag for TimeHourDetailRenderer
	 */
	public boolean isOvertimeEarnCode();

}
