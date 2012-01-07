package org.kuali.hr.time.cache;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.SerializationUtils;
import org.apache.log4j.Logger;

import com.opensymphony.oscache.base.CacheEntry;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.base.algorithm.AbstractConcurrentReadCache;
import com.opensymphony.oscache.base.algorithm.LRUCache;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public class CacheManagementServiceImpl implements CacheManagementService {

	private static final Logger LOG = Logger.getLogger(CacheManagementServiceImpl.class);

	private GeneralCacheAdministrator cacheAdministrator;
	private static int SECONDS_TO_EXPIRE = 60 * 60 * 24;

	public String generateKeys(Object[] args, Class<? extends Object> source, String methodName) throws NoSuchMethodException {

		String key = source.getName() + "." + methodName;
		for (int i = 0; i < args.length; i++) {
			key += "_" + args[i];
		}
		return key;
	}

	protected String getKey(Object[] args, Class<? extends Object> source, String methodName) {
		String key = source.getName() + "." + methodName;
		for (int i = 0; i < args.length; i++) {
			key += "_" + args[i];
		}
		return key;
	}

	public void putItemInCache(String key, Object item, String... groups) {
		this.getCacheAdministrator().putInCache(key, SerializationUtils.clone((Serializable) item), groups);
	}
	
	public Object getCacheEntry(String key) {
		try {
			return SerializationUtils.clone((Serializable) this.getCacheAdministrator().getFromCache(key, SECONDS_TO_EXPIRE));
		} catch (NeedsRefreshException nre) {
			LOG.info("key " + key + " not found or expired.");
			this.getCacheAdministrator().cancelUpdate(key);
			return null;
		}
	}
	
	public void putItemInCacheNoClone(String key, Object item, String... groups) {
		this.getCacheAdministrator().putInCache(key, item, groups);
	}
	
	public Object getCacheEntryNoClone(String key) {
		try {
			return this.getCacheAdministrator().getFromCache(key, SECONDS_TO_EXPIRE);
		} catch (NeedsRefreshException nre) {
			LOG.info("key " + key + " not found or expired.");
			return null;
		}
	}

	public GeneralCacheAdministrator getCacheAdministrator() {
		return cacheAdministrator;
	}

	public void setCacheAdministrator(GeneralCacheAdministrator cacheAdministrator) {
		this.cacheAdministrator = cacheAdministrator;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Set<String>> getCacheMap() {
		Map groups = null;
		AbstractConcurrentReadCache cache = null;
		try {
			cache = getCache();
			Field groupField = cache.getClass().getSuperclass().getDeclaredField("groups");
			groupField.setAccessible(true);
			groups = (Map) groupField.get(cache);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		// clone (and sync?)
		Map<String, Set<String>> displayGroups = (Map<String, Set<String>>)((HashMap) groups).clone();
		
		// do not show flushed entries
		Set<String> groupKeySet = displayGroups.keySet();
		for (Iterator<String> groupKeySetIter = groupKeySet.iterator(); groupKeySetIter.hasNext();) {
			String groupKey = groupKeySetIter.next();
			Set<String> itemKeySet = (Set<String>) groups.get(groupKey);
			for (Iterator<String> itemKeyIter = itemKeySet.iterator(); itemKeyIter.hasNext(); ) {
				String itemKey = itemKeyIter.next();
				CacheEntry cacheEntry = (CacheEntry)cache.get(itemKey);
				if (cacheEntry.needsRefresh(CacheEntry.INDEFINITE_EXPIRY)) {
					itemKeyIter.remove();
				}
			}
		}
		return displayGroups;
	}
	
	private AbstractConcurrentReadCache getCache() {
		AbstractConcurrentReadCache cache = null;
		try {
			// need to reflect through the cache admin to grab the map that contains group and item keys
			GeneralCacheAdministrator admin = getCacheAdministrator();
			Field field = admin.getCache().getClass().getDeclaredField("cacheMap");
			field.setAccessible(true);
			cache = (LRUCache) field.get(admin.getCache());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cache;
	}

	public void cancelUpdate(String key) {
		getCacheAdministrator().cancelUpdate(key);
	}
	
	public void flushGroup(String id) {
		getCacheAdministrator().flushGroup(id);
	}
	
}