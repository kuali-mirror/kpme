package org.kuali.hr.time.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TKContext {

    private static final String TDOC_KEY = "_TDOC_ID_KEY"; // Timesheet Document ID Key
	private static final String USER_KEY = "_USER_KEY";

	private static final ThreadLocal<Map<String, Object>> STORAGE_MAP = new ThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return Collections.synchronizedMap(new HashMap<String, Object>());
		}
	};

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

	/**
	 * TKUser has the internal concept of Backdoor User vs.Actual User.
	 * @return
	 */
	public static TKUser getUser() {
		return (TKUser) getStorageMap().get(USER_KEY);
	}

	public static void setUser(TKUser user) {
		TKContext.getStorageMap().put(USER_KEY, user);
	}

	public static void clearFormsFromSession() {
		if (getHttpServletRequest() != null) {

		}
	}

	public static String getPrincipalId(){
		if(getUser()!= null){
			return getUser().getPrincipalId();
		}
		return null;
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
}
