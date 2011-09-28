package org.kuali.hr.time;

import org.apache.log4j.Logger;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.config.ConfigContext;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

public class SessionListener implements HttpSessionListener {

    private static final Logger LOG = Logger.getLogger(SessionListener.class);
    private static int sessionCount;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();

        session.setMaxInactiveInterval(Integer.parseInt((ConfigContext.getCurrentContextConfig().getProperty(TkConstants.ConfigSettings.SESSION_TIMEOUT))));

        synchronized (this) {
            sessionCount++;
        }
        String id = session.getId();

        Date now = new Date();

        String message = "New Session created on " + now + "\nID: " + id + "\nThere are now " + sessionCount + " live sessions in the application.\n" +
                "The default inactive interval is " + session.getMaxInactiveInterval() + " secs.";
        LOG.info(message);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
		session.invalidate();
        String id = session.getId();
        synchronized (this) {
            sessionCount--;
        }

        String message = "Session destroyed" + "\nValue of destroyed session ID is " + id + "\nThere are now " + sessionCount + " live sessions in the application.";
        LOG.info(message);
    }
}
