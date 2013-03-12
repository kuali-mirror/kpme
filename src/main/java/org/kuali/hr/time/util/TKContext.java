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
package org.kuali.hr.time.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.rice.krad.util.GlobalVariables;

public class TKContext {

    private static final String TDOC_OBJ_KEY = "_TDOC_O_KEY";
    private static final String TDOC_KEY = "_TDOC_ID_KEY"; // Timesheet Document ID Key
	//private static final String USER_KEY = "_USER_KEY";
    private static final String LDOC_OBJ_KEY = "_LDOC_O_KEY";
    private static final String LDOC_KEY = "_LDOC_ID_KEY";

	private static final ThreadLocal<Map<String, Object>> STORAGE_MAP = new ThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return Collections.synchronizedMap(new HashMap<String, Object>());
		}
	};

    public static TimesheetDocument getCurrentTimesheetDocument() {
        return (TimesheetDocument)TKContext.getStorageMap().get(TDOC_OBJ_KEY);
    }

    public static void setCurrentTimesheetDocument(TimesheetDocument tdoc) {
        TKContext.getStorageMap().put(TDOC_OBJ_KEY, tdoc);
    }

    /**
     * @return The current timesheet document id, as set by the detail/clock
     * pages.
     */
    public static String getCurrentTimesheetDocumentId() {
        return (String)TKContext.getStorageMap().get(TDOC_KEY);
    }

    /**
     * Set the current timesheet document id.
     * @param timesheetDocumentId The ID we are setting this value to.
     */
    public static void setCurrentTimesheetDocumentId(String timesheetDocumentId) {
        TKContext.getStorageMap().put(TDOC_KEY, timesheetDocumentId);
    }

	public static String getPrincipalId(){
		return GlobalVariables.getUserSession().getPrincipalId();
	}

    public static String getTargetPrincipalId() {
        return TKUser.getCurrentTargetPersonId();
    }

	public static HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) getStorageMap().get("REQUEST");
	}

	public static void setHttpServletRequest(HttpServletRequest request) {
		getStorageMap().put("REQUEST", request);
	}

	public static Map<String, Object> getStorageMap() {
		return STORAGE_MAP.get();
	}

	public static void resetStorageMap() {
		STORAGE_MAP.remove();
	}

	public static void clear() {
		resetStorageMap();
	}
	
    /**
     * @return The current leave calendar document
     */
    public static LeaveCalendarDocument getCurrentLeaveCalendarDocument() {
        return  (LeaveCalendarDocument)TKContext.getStorageMap().get(LDOC_OBJ_KEY);
    }

    /**
     *
     * @param ldoc The leave calendar document
     */
    public static void setCurrentLeaveCalendarDocument(LeaveCalendarDocument ldoc) {
        TKContext.getStorageMap().put(LDOC_OBJ_KEY, ldoc);
    }

    /**
     *
     * @return The current leave calendar document Id
     */
    public static String getCurrentLeaveCalendarDocumentId() {
        return (String)TKContext.getStorageMap().get(LDOC_KEY);
    }

    /**
     *
     * @param leaveCalendarDocumentId The leave calendar document Id
     */
    public static void setCurrentLeaveCalendarDocumentId(String leaveCalendarDocumentId) {
        TKContext.getStorageMap().put(LDOC_KEY, leaveCalendarDocumentId);
    }	
}
