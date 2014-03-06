package org.kuali.kpme.core.service;

import org.kuali.kpme.core.api.department.DepartmentService;
import org.kuali.kpme.core.department.service.DepartmentInternalService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class HrServiceLocatorInternal implements ApplicationContextAware {

    public static ApplicationContext CONTEXT;
    public static final String HR_DEPARTMENT_SERVICE_INTERNAL = "departmentInternalService";

    public static Object getBean(String beanName) {
        return CONTEXT.getBean(beanName);
    }

    public static DepartmentInternalService getDepartmentInternalService() {
        return (DepartmentInternalService) CONTEXT.getBean(HR_DEPARTMENT_SERVICE_INTERNAL);
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        CONTEXT = arg0;
    }
}
