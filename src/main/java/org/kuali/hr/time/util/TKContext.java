package org.kuali.hr.time.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class TKContext {

	private static final String USER_KEY = "_USER_KEY";
	private static final String BACKDOOR_USER_KEY = "_BACKDOOR_USER";

	private static final ThreadLocal<Map<String, Object>> STORAGE_MAP = new ThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return Collections.synchronizedMap(new HashMap<String, Object>());
		}
	};

	/**
	 * Provides access to the current 'User'.  This is firstly the backdoored user, if available, otherwise the normal user.
	 * @return
	 */
	public static TKUser getUser() {
		if (getBackdoorUser() != null) {
			return getBackdoorUser();
		}
		return (TKUser) getStorageMap().get(USER_KEY);
	}

	public static void setUser(TKUser user) {
		TKContext.getStorageMap().put(USER_KEY, user);
	}

	public static void clearFormsFromSession() {
		if (getHttpServletRequest() != null) {

		}
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

	public static TKUser getBackdoorUser() {
		if (getHttpServletRequest() != null) {
			TKSessionState tkSessionState = new TKSessionState(TKContext.getHttpServletRequest().getSession());
			return tkSessionState.getBackdoorUser();
		}
		return null;
	}

	public static void setBackdoorUser(TKUser backdoorUser) {
		if (getHttpServletRequest() != null) {
			TKSessionState tkSessionState = new TKSessionState(TKContext.getHttpServletRequest().getSession());
			tkSessionState.setBackdoorUser(backdoorUser);
		}
	}

	public static void clearBackdoorUser() {
		if (getHttpServletRequest() != null) {
			TKUser tkUser = (TKUser) TKContext.getHttpServletRequest().getSession().getAttribute(BACKDOOR_USER_KEY);
			if (tkUser != null && tkUser.getBackdoorPerson() != null) {
				setBackdoorUser(null);
			}
		}
	}

	public static TKUser getTargetEmployee() {
		TKSessionState tkSessionState = new TKSessionState(getHttpServletRequest().getSession());
		if (tkSessionState.getTargetEmployee() == null) {
			tkSessionState.setTargetEmployee(getUser());
		}
		return tkSessionState.getTargetEmployee();
	}
	
	public static void setTargetEmployee(TKUser employee) {
		TKSessionState tkSessionState = new TKSessionState(getHttpServletRequest().getSession());
		tkSessionState.setTargetEmployee(employee);
	}

}
