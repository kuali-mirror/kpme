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
package org.kuali.kpme.core.api.calendar.web;


/**
 * <p>CalendarDayContract interface</p>
 *
 */
public interface CalendarDayContract {

	/**
	 * The dayNumberDelta the CalendarDay is associated with
	 * 
	 * <p>
	 * dayNumberDelta of a CalendarDay
	 * </p>
	 * 
	 * @return dayNumberDelta of CalendarDay
	 */
    public int getDayNumberDelta();

    /**
	 * The dayNumberString the CalendarDay is associated with
	 * 
	 * <p>
	 * dayNumberString of a CalendarDay
	 * </p>
	 * 
	 * @return dayNumberString of CalendarDay
	 */
    public String getDayNumberString();

    /**
	 * The flag to indicate the CalendarDay needs to be greyed out
	 * 
	 * <p>
	 * gray of a CalendarDay
	 * </p>
	 * 
	 * @return Y if needs to be greyed out, N if not
	 */
    public Boolean getGray();
   
    /**
	 * The dateString the CalendarDay is associated with
	 * 
	 * <p>
	 * dateString of a CalendarDay
	 * </p>
	 * 
	 * @return dateString of CalendarDay
	 */
    public String getDateString();

    /**
	 * The flag to indicate the CalendarDay is editable
	 * 
	 * <p>
	 * dayEditable of a CalendarDay
	 * </p>
	 * 
	 * @return Y if editable, N if not
	 */
	public Boolean getDayEditable();
    
}