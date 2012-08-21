package org.kuali.hr.core.cache;


import org.kuali.hr.core.KPMEConstants;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.impl.cache.DistributedCacheManagerDecorator;

public class CacheUtils {
    public static void flushCache(String cacheName) {
        //flush cache
        DistributedCacheManagerDecorator distributedCacheManagerDecorator =
                TkServiceLocator.getDistributedCacheManager();
        distributedCacheManagerDecorator.getCache(cacheName).clear();
    }
}
