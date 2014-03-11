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
package org.kuali.kpme.tklm.api.common.krms;


import org.kuali.kpme.core.api.namespace.KPMENamespace;

public class TklmKrmsConstants {
	
	public static final class MissedPunch {
        public static final String KPME_MISSED_PUNCH_CONTEXT_NAME = "KPME Missed Punch Context";
    }
	
    public static final class Calendar {
        public static final String KPME_TIMESHEET_CONTEXT_NAME = "KPME Timesheet Context";
        public static final String KPME_LEAVE_CALENDAR_CONTEXT_NAME = "KPME Leave Calendar Context";
    }

    public static final class Context {
        public static final String LM_CONTEXT_NAME = "KPME Leave Context";
        public static final String LEAVE_REQUEST_CONTEXT_NAME = "KPME Leave Request Context";
    }

    public static final class LMDocument {
        public static final String CONTEXT_NAME = Context.LM_CONTEXT_NAME;
        public static final String NAMESPACE = KPMENamespace.KPME_LM.getNamespaceCode();
        public static final String ASSIGNMENTS = "Assignments";
    }
}
