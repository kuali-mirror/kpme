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
package org.kuali.kpme.core.api.util;


public class HrApiConstants {

	 public static final class CacheNamespace {
	        public static final String MODULE_NAME = "core";
	        public static final String ROOT_NAMESPACE_PREFIX = "http://kpme.kuali.org";
	        public static final String NAMESPACE_PREFIX = CacheNamespace.ROOT_NAMESPACE_PREFIX + "/"
	                + MODULE_NAME + "/";
	        public static final String KPME_GLOBAL_CACHE_NAME = NAMESPACE_PREFIX + "Global";
	 }
	 
	 public static final String ASSIGNMENT_KEY_DELIMITER = "_";
}
