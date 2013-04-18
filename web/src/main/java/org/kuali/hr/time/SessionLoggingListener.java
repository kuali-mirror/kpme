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
package org.kuali.hr.time;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.kuali.hr.time.util.TKUtils;

public class SessionLoggingListener implements HttpSessionListener {

    private static final Logger LOG = Logger.getLogger(SessionLoggingListener.class);
    private static int sessionCount;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();

        session.setMaxInactiveInterval(TKUtils.getSessionTimeoutTime());

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

        String id = session.getId();
        
        synchronized (this) {
            sessionCount--;
        }

        String message = "Session destroyed" + "\nValue of destroyed session ID is " + id + "\nThere are now " + sessionCount + " live sessions in the application.";
        LOG.info(message);
    }
    
}