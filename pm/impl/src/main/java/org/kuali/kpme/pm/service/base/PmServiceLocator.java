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
package org.kuali.kpme.pm.service.base;

import org.kuali.kpme.pm.classification.duty.service.ClassificationDutyService;
import org.kuali.kpme.pm.classification.flag.service.ClassificationFlagService;
import org.kuali.kpme.pm.classification.service.ClassificationService;
import org.kuali.kpme.pm.positionResponsibilityOption.service.PositionResponsibilityOptionService;
import org.kuali.kpme.pm.positionappointment.service.PositionAppointmentService;
import org.kuali.kpme.pm.positiondepartment.service.PositionDepartmentService;
import org.kuali.kpme.pm.positiondepartmentaffiliation.service.PositionDepartmentAffiliationService;
import org.kuali.kpme.pm.positionflag.service.PositionFlagService;
import org.kuali.kpme.pm.positionreportcat.service.PositionReportCatService;
import org.kuali.kpme.pm.positionreportgroup.service.PositionReportGroupService;
import org.kuali.kpme.pm.positionreportsubcat.service.PositionReportSubCatService;
import org.kuali.kpme.pm.positionreporttype.service.PositionReportTypeService;
import org.kuali.kpme.pm.positiontype.service.PositionTypeService;
import org.kuali.kpme.pm.pstncontracttype.service.PstnContractTypeService;
import org.kuali.kpme.pm.pstnqlfctnvl.service.PositionQualificationValueService;
import org.kuali.kpme.pm.pstnqlfrtype.service.PstnQlfrTypeService;
import org.kuali.kpme.pm.pstnrptgrpsubcat.service.PstnRptGrpSubCatService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class PmServiceLocator implements ApplicationContextAware {
	public static String SPRING_BEANS = "classpath:SpringBeans.xml";
	public static ApplicationContext CONTEXT;

    public static final String PM_POSITION_REPORT_TYPE_SERVICE = "positionReportTypeService";
    public static final String PM_POSITION_REPORT_GROUP_SERVICE = "positionReportGroupService";
    public static final String PM_POSITION_REPORT_CAT_SERVICE = "positionReportCatService";
    public static final String PM_POSITION_REPORT_SUB_CAT_SERVICE = "positionReportSubCatService";
    public static final String PM_PSTN_RPT_GRP_SUB_CAT_SERVICE = "pstnRptGrpSubCatService";
    public static final String PM_POSITION_TYPE_SERVICE = "positionTypeService";
    public static final String PM_PSTN_CONTRACT_TYPE_SERVICE = "pstnContractTypeService";
    public static final String PM_POSITION_FLAG_SERVICE = "positionFlagService";
    public static final String PM_POSITION_QUALIFIER_TYPE_SERVICE = "pstnQlfrTypeService";
    public static final String PM_POSITION_QUALIFICATION_VALUE_SERVICE = "pstnQlfctnVlService";
    public static final String PM_POSITION_APPOINTMENT_SERVICE = "positionAppointmentService";
    public static final String PM_POSITION_DEPT_AFFL_SERVICE = "positionDepartmentAffiliationService";
    public static final String PM_POSITION_DEPT_SERVICE = "positionDepartmentService";
    public static final String PM_CLASSIFICATION_SERVICE = "classificationService";
    public static final String PM_CLASSIFICATION_DUTY_SERVICE = "classificationDutyService";
    public static final String PM_CLASSIFICATION_FLAG_SERVICE = "classificationFlagService";
    public static final String PM_POSITION_RESPONSIBILITY_OPTION_SERVICE = "positionResponsibilityOptionService";
    
    public static PositionReportTypeService getPositionReportTypeService() {
    	return (PositionReportTypeService) CONTEXT.getBean(PM_POSITION_REPORT_TYPE_SERVICE);
    }
	
    public static PositionReportGroupService getPositionReportGroupService() {
    	return (PositionReportGroupService) CONTEXT.getBean(PM_POSITION_REPORT_GROUP_SERVICE);
    }
    
    public static PositionReportCatService getPositionReportCatService() {
    	return (PositionReportCatService) CONTEXT.getBean(PM_POSITION_REPORT_CAT_SERVICE);
    }
    
    public static PositionReportSubCatService getPositionReportSubCatService() {
    	return (PositionReportSubCatService) CONTEXT.getBean(PM_POSITION_REPORT_SUB_CAT_SERVICE);
    }
    
    public static PstnRptGrpSubCatService getPstnRptGrpSubCatService() {
    	return (PstnRptGrpSubCatService) CONTEXT.getBean(PM_PSTN_RPT_GRP_SUB_CAT_SERVICE);
    }
    
	public static PositionTypeService getPositionTypeService() {
		return (PositionTypeService) CONTEXT.getBean(PM_POSITION_TYPE_SERVICE);
	}
	
	public static PositionFlagService getPositionFlagService() {
		return (PositionFlagService) CONTEXT.getBean(PM_POSITION_FLAG_SERVICE);
	}
	
	public static PstnQlfrTypeService getPstnQlfrTypeService() {
		return (PstnQlfrTypeService) CONTEXT.getBean(PM_POSITION_QUALIFIER_TYPE_SERVICE);
	}
	
	public static PositionQualificationValueService getPositionQualificationValueService() {
		return (PositionQualificationValueService) CONTEXT.getBean(PM_POSITION_QUALIFICATION_VALUE_SERVICE);
	}
	
	public static PstnContractTypeService getPstnContractTypeService() {
		return (PstnContractTypeService) CONTEXT.getBean(PM_PSTN_CONTRACT_TYPE_SERVICE);
	}

	public static PositionDepartmentAffiliationService getPositionDepartmentAffiliationService() {
		return (PositionDepartmentAffiliationService) CONTEXT.getBean(PM_POSITION_DEPT_AFFL_SERVICE);
	}
	
	public static PositionAppointmentService getPositionAppointmentService() {
		return (PositionAppointmentService) CONTEXT.getBean(PM_POSITION_APPOINTMENT_SERVICE);
	}
	
	public static PositionDepartmentService getPositionDepartmentService() {
		return (PositionDepartmentService) CONTEXT.getBean(PM_POSITION_DEPT_SERVICE);
	}
	
	public static ClassificationService getClassificationService() {
		return (ClassificationService) CONTEXT.getBean(PM_CLASSIFICATION_SERVICE);
	}
	
	public static ClassificationDutyService getClassificationDutyService() {
		return (ClassificationDutyService) CONTEXT.getBean(PM_CLASSIFICATION_DUTY_SERVICE);
	}
	
	public static ClassificationFlagService getClassificationFlagService() {
		return (ClassificationFlagService) CONTEXT.getBean(PM_CLASSIFICATION_FLAG_SERVICE);
	}
	
	public static PositionResponsibilityOptionService getPositionResponsibilityOptionService() {
		return (PositionResponsibilityOptionService) CONTEXT.getBean(PM_POSITION_RESPONSIBILITY_OPTION_SERVICE);
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		CONTEXT = arg0;
	}
}
