package org.kuali.hr.time.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.KRADConstants;

public class TKContext {

    private static final String TDOC_OBJ_KEY = "_TDOC_O_KEY";
    private static final String TDOC_KEY = "_TDOC_ID_KEY"; // Timesheet Document ID Key
	private static final String USER_KEY = "_USER_KEY";
    private static final String LDOC_OBJ_KEY = "_LDOC_O_KEY";
    private static final String LDOC_KEY = "_LDOC_ID_KEY";

	private static final ThreadLocal<Map<String, Object>> STORAGE_MAP = new ThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return Collections.synchronizedMap(new HashMap<String, Object>());
		}
	};

    public static TimesheetDocument getCurrentTimesheetDoucment() {
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

	public static UserSession getUserSession(){
		return (UserSession) getHttpServletRequest().getSession().getAttribute(KRADConstants.USER_SESSION_KEY);
	}

	public static String getPrincipalId(){
		if(getUser()!= null){
			return getUser().getPrincipalId();
		}
		return null;
	}

    public static String getTargetPrincipalId() {
        if(getUser()!= null){
            return getUser().getTargetPrincipalId();
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
