package org.kuali.hr.sys.context;

import org.kuali.hr.sys.util.constants.SpringBeans;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * helper class for spring beans stuff
 * 
 * Copied over from SpringContext.java from the LeaveManager Project
 */
public class SpringContext implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/**
	 * public constructor that throws exception if you try to instantiate it
	 * twice
	 * 
	 * @throws Exception
	 */
	public SpringContext() throws Exception {
		if (applicationContext != null)
			throw new Exception(
					"Cannot instantiate SpringContext twice. Use the static methods");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContext.applicationContext = applicationContext;
	}

	/**
	 * return a spring bean by its name
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBean(SpringBeans bean) {
		return applicationContext.getBean(bean.toString());
	}

	/**
	 * returns a spring bean by its class... this only works when the class name
	 * equals the spring bean name
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		String[] beanNames = applicationContext.getBeanNamesForType(clazz);
		// just return the first one
		return (T) applicationContext.getBean(beanNames[0]);
	}
}
