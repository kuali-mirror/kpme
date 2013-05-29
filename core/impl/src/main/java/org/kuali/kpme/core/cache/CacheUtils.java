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
package org.kuali.kpme.core.cache;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.impl.cache.DistributedCacheManagerDecorator;

public class CacheUtils {
    private static final Logger LOG = Logger.getLogger(CacheUtils.class);

    public static void flushCache(String cacheName) {
        //flush cache
        //DistributedCacheManagerDecorator distributedCacheManagerDecorator =
        //        HrServiceLocator.getDistributedCacheManager();

        String cacheDecoratorName = extractCacheDecoratorName(cacheName);
        if (StringUtils.isNotEmpty(cacheDecoratorName)) {
            DistributedCacheManagerDecorator distributedCacheManagerDecorator =
                    GlobalResourceLoader.getService(cacheDecoratorName);
            if (distributedCacheManagerDecorator != null) {
                distributedCacheManagerDecorator.getCache(cacheName).clear();
            } else {
                LOG.error("DistributedCacheManagerDecorator: " + cacheDecoratorName + " not found.  Cache: " + cacheName + " was not flushed.");
            }
        }
    }

    public static void flushCaches(List<String> cacheNames) {
        //flush cache
        for (String cache : cacheNames) {
            String cacheDecoratorName = extractCacheDecoratorName(cache);
            if (StringUtils.isNotEmpty(cacheDecoratorName)) {
                DistributedCacheManagerDecorator distributedCacheManagerDecorator =
                        GlobalResourceLoader.getService(cacheDecoratorName);
                if (distributedCacheManagerDecorator != null) {
                    distributedCacheManagerDecorator.getCache(cache).clear();
                } else {
                    LOG.error("DistributedCacheManagerDecorator: " + cacheDecoratorName + " not found.  Cache: " + cache + " was not flushed.");
                }
            }
        }
    }

    private static String extractCacheDecoratorName(String cacheName) {
        String[] splitName = cacheName.split("/");
        if (splitName.length >= 2) {
            return "kpme" + StringUtils.capitalize(splitName[1]) + "DistributedCacheManager";
        }
        LOG.warn("Unable to extract cache decorator bean name from " + cacheName + ". Cache will not be flushed");
        return StringUtils.EMPTY;
    }
}
