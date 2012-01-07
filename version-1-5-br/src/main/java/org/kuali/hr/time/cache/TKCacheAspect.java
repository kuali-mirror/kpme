package org.kuali.hr.time.cache;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.core.config.ConfigContext;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

@Aspect
public class TKCacheAspect {

	public static final String DATE_FORMAT = new String("MM\\dd\\yyyy");

	@Around(value="@annotation(org.kuali.hr.time.cache.CacheResult) && @annotation(cacheResultAnnotation)", argNames="cacheResultAnnotation")
	public Object accessCache(ProceedingJoinPoint pjp, CacheResult cacheResultAnnotation) throws Throwable {
		if(StringUtils.isBlank(ConfigContext.getCurrentContextConfig().getProperty("cache.on")) ||
			!Boolean.parseBoolean(ConfigContext.getCurrentContextConfig().getProperty("cache.on"))){
			return pjp.proceed();
		}

		GeneralCacheAdministrator cache = TkServiceLocator.getCacheManagerService().getCacheAdministrator();
		String key = getKey(pjp.getArgs(), pjp.getTarget().getClass(), pjp.getSignature().getName());
		try {
			Object cachedObject = cache.getFromCache(key, cacheResultAnnotation.secondsRefreshPeriod());
			synchronized(cachedObject) {
				return SerializationUtils.clone((Serializable) cachedObject);
			}
		} catch (NeedsRefreshException nre) {
			try {
				Object entry = pjp.proceed(pjp.getArgs());
				if (entry != null) {
					cache.putInCache(key, entry, getGroups(pjp));
				} else {
					cache.cancelUpdate(key);
				}
				return entry;
			} catch (Throwable t) {
				cache.cancelUpdate(key);
				throw t;
			}
		}
	}

	protected String getKey(Object[] args, Class<? extends Object> source, String methodName) {
		String key = source.getName() + "." + methodName;
		for (int i = 0; i < args.length; i++) {
			Object arg = args[i];
			if (arg instanceof Date) {
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				key += "_" + sdf.format(arg);
				continue;
			}
			key += "_" + args[i];
		}
		return key;
	}

	protected String[] getGroups(ProceedingJoinPoint pjp) {
		return new String[] { pjp.getTarget().getClass().getName() };
	}

}