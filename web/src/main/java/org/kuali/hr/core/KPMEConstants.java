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
package org.kuali.hr.core;


public class KPMEConstants {
    public static final String APPLICATION_NAMESPACE_CODE = "KPME";
    public static final String KPME_DISTRIBUTED_CACHE_MANAGER = "kpmeDistributedCacheManager";
    public static final String KPME_GLOBAL_CACHE_NAME = APPLICATION_NAMESPACE_CODE + "/Global";
    

    public static class ConfigSettings {
        public static final String SESSION_TIMEOUT = "session.timeout";
        public static final String KPME_SYSTEM_TIMEZONE = "kpme.system.timezone";
    }

    public static final String IP_SEPARATOR = ".";
    public static final String IP_WILDCARD_PATTERN = "(%|(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))";
    
}
