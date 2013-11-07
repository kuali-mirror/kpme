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
