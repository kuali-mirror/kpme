/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.core.cache;


import org.kuali.hr.core.KPMEConstants;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.impl.cache.DistributedCacheManagerDecorator;

import java.util.List;

public class CacheUtils {
    public static void flushCache(String cacheName) {
        //flush cache
        DistributedCacheManagerDecorator distributedCacheManagerDecorator =
                TkServiceLocator.getDistributedCacheManager();
        distributedCacheManagerDecorator.getCache(cacheName).clear();
    }

    public static void flushCaches(List<String> cacheNames) {
        //flush cache
        DistributedCacheManagerDecorator distributedCacheManagerDecorator =
                TkServiceLocator.getDistributedCacheManager();
        for (String cache : cacheNames) {
            distributedCacheManagerDecorator.getCache(cache).clear();
        }
    }
}
