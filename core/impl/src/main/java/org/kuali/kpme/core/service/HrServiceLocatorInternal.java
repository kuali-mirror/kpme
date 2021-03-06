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
package org.kuali.kpme.core.service;

import org.kuali.kpme.core.department.service.DepartmentInternalService;
import org.kuali.kpme.core.location.service.LocationInternalService;
import org.kuali.kpme.core.workarea.service.WorkAreaInternalService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class HrServiceLocatorInternal implements ApplicationContextAware {

    public static ApplicationContext CONTEXT;
    public static final String HR_DEPARTMENT_SERVICE_INTERNAL = "departmentInternalService";
    public static final String HR_LOCATION_SERVICE_INTERNAL = "locationInternalService";
    public static final String HR_WORKAREA_SERVICE_INTERNAL = "workAreaInternalService";

    public static Object getBean(String beanName) {
        return CONTEXT.getBean(beanName);
    }

    public static DepartmentInternalService getDepartmentInternalService() {
        return (DepartmentInternalService) CONTEXT.getBean(HR_DEPARTMENT_SERVICE_INTERNAL);
    }

    public static LocationInternalService getLocationInternalService() {
        return (LocationInternalService) CONTEXT.getBean(HR_LOCATION_SERVICE_INTERNAL);
    }

    public static WorkAreaInternalService getWorkAreaInternalService() {
        return (WorkAreaInternalService) CONTEXT.getBean(HR_WORKAREA_SERVICE_INTERNAL);
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        CONTEXT = arg0;
    }
}
