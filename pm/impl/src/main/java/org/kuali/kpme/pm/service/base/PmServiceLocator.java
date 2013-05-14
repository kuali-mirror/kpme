package org.kuali.kpme.pm.service.base;

import org.kuali.kpme.pm.classification.service.ClassificationService;
import org.kuali.kpme.core.bo.institution.service.InstitutionService;
import org.kuali.kpme.core.bo.paystep.service.PayStepService;
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

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		CONTEXT = arg0;
	}
}
