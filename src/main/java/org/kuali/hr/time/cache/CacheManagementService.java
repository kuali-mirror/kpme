package org.kuali.hr.time.cache;

import java.util.Map;
import java.util.Set;

import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public interface CacheManagementService {
	public void putItemInCache(String key, Object item, String... groups);
	public String generateKeys(Object[] args, Class<? extends Object> source, String methodName) throws NoSuchMethodException ;
	public Object getCacheEntry(String key);
	public GeneralCacheAdministrator getCacheAdministrator();
	public Map<String, Set<String>> getCacheMap();
	public void putItemInCacheNoClone(String key, Object item, String... groups);
	public Object getCacheEntryNoClone(String key);
	public void cancelUpdate(String key);
	public void flushGroup(String id);
}
