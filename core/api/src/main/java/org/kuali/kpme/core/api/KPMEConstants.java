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
package org.kuali.kpme.core.api;


public final class KPMEConstants {

    public static class ConfigSettings {
        public static final String SESSION_TIMEOUT = "session.timeout";
        public static final String KPME_SYSTEM_TIMEZONE = "kpme.system.timezone";
    }

    public static final String IP_SEPARATOR = ".";
    public static final String IP_WILDCARD_PATTERN = "(%|(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))";

    public static final class CacheNamespace {
        public static final String ROOT_NAMESPACE_PREFIX = "http://kpme.kuali.org";
    }

    public static final String BATCH_USER_PRINCIPAL_NAME = "kpme.batch.user.principalName";

    public static final class Versions {
        public static final String UNSPECIFIED = "unspecifiedVersion";

        public static final String VERSION_2_1 = "v2_1";
    }

    public static final class CommonElements {
        public final static String ID = "id";
        public final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        public final static String CREATE_TIME = "createTime";
        public final static String USER_PRINCIPAL_ID = "userPrincipalId";
        public final static String GROUP_KEY_CODE = "groupKeyCode";
        public final static String GROUP_KEY = "groupKey";
    }

    private KPMEConstants() {
        throw new UnsupportedOperationException("do not call");
    }
}
