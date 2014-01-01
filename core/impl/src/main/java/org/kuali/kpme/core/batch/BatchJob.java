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
package org.kuali.kpme.core.batch;

import org.apache.log4j.Logger;
import org.kuali.kpme.core.KPMEConstants;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.quartz.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;

public abstract class BatchJob implements StatefulJob {
    private static final Logger LOG = Logger.getLogger(BatchJob.class);
    private transient Thread workerThread;


    protected String jobDataMapToString(JobDataMap jobDataMap) {
        StringBuilder buf = new StringBuilder();
        buf.append("{");
        Iterator keys = jobDataMap.keySet().iterator();
        boolean hasNext = keys.hasNext();
        while (hasNext) {
            String key = (String) keys.next();
            Object value = jobDataMap.get(key);
            buf.append(key).append("=");
            if (value == jobDataMap) {
                buf.append("(this map)");
            }
            else {
                buf.append(value);
            }
            hasNext = keys.hasNext();
            if (hasNext) {
                buf.append(", ");
            }
        }
        buf.append("}");
        return buf.toString();
    }

    protected String getMachineName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e) {
            return "Unknown";
        }
    }

    protected String getBatchUserPrincipalId() {
        return BatchJobUtil.getBatchUserPrincipalId();
    }

    protected String getBatchUserPrincipalName() {
        return BatchJobUtil.getBatchUserPrincipalName();
    }

}
