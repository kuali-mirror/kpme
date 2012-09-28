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
