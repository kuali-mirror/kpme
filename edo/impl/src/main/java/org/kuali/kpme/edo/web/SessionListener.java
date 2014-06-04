package org.kuali.kpme.edo.web;


import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.log4j.Logger;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.core.api.config.property.ConfigContext;


import java.util.Date;

public class SessionListener implements HttpSessionListener {
	private static final Logger LOG = Logger.getLogger(SessionListener.class);

	static private int sessionCount;

	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();

		session.setMaxInactiveInterval(Integer.parseInt((ConfigContext.getCurrentContextConfig().getProperty(EdoConstants.ConfigSettings.SESSION_TIMEOUT))));

		synchronized (this) {
			sessionCount++;
		}
		String id = session.getId();

		Date now = new Date();

		String message = "New Session created on " + now + "\nID: " + id + "\nThere are now " + sessionCount + " live sessions in the application.\n" +
				"The default inactive interval is " + session.getMaxInactiveInterval() + " secs.";
		LOG.warn(message);
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
//		session.invalidate();
		String id = session.getId();
		synchronized (this) {
			sessionCount--;
		}

		String message = "Session destroyed" + "\nValue of destroyed session ID is " + id + "\nThere are now " + sessionCount + " live sessions in the application.";
		LOG.warn(message);
	}
}
