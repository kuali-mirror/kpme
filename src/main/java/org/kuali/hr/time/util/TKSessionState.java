package org.kuali.hr.time.util;

import javax.servlet.http.HttpSession;

public class TKSessionState {
    private static final String TARGET_EMPLOYEE = "_TARGET_EMPLOYEE";
    private static final String BACKDOOR_USER = "_BACKDOOR_USER";

    private HttpSession session;

    public TKSessionState(HttpSession session) {
	this.session = session;
    }

    public TKUser getTargetEmployee() {
	return (TKUser) getObjectFromSession(TARGET_EMPLOYEE);
    }

    public void setTargetEmployee(TKUser employee) {
	this.setObjectOnSession(TARGET_EMPLOYEE, employee);
    }

    public TKUser getBackdoorUser() {
	return (TKUser) getObjectFromSession(BACKDOOR_USER);
    }

    public void setBackdoorUser(TKUser backDoorUser) {
	this.setObjectOnSession(BACKDOOR_USER, backDoorUser);
    }

    protected void setObjectOnSession(String key, Object object) {
	session.setAttribute(key, object);
    }

    protected Object getObjectFromSession(String key) {
	return session.getAttribute(key);
    }

    protected void removeObjectFromSession(String key) {
	session.removeAttribute(key);
    }
}
