package org.kuali.hr.pm.service.base;

import org.kuali.hr.pm.institution.service.InstitutionService;
import org.kuali.hr.pm.paystep.service.PayStepService;
import org.kuali.hr.pm.positionreportcat.service.PositionReportCatService;
import org.kuali.hr.pm.positionreportgroup.service.PositionReportGroupService;
import org.kuali.hr.pm.positionreportsubcat.service.PositionReportSubCatService;
import org.kuali.hr.pm.positionreporttype.service.PositionReportTypeService;
import org.kuali.hr.pm.positiontype.service.PositionTypeService;
import org.kuali.hr.pm.pstnrptgrpsubcat.service.PstnRptGrpSubCatService;
import org.kuali.hr.pm.salarygroup.service.SalaryGroupService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class PmServiceLocator implements ApplicationContextAware {
	public static String SPRING_BEANS = "classpath:SpringBeans.xml";
	public static ApplicationContext CONTEXT;
	public static final String PM_PAY_STEP_SERVICE = "payStepService";
	public static final String PM_INSTITUTION_SERVICE = "institutionService";
    public static final String PM_POSITION_REPORT_TYPE_SERVICE = "positionReportTypeService";
    public static final String PM_POSITION_REPORT_GROUP_SERVICE = "positionReportGroupService";
    public static final String PM_POSITION_REPORT_CAT_SERVICE = "positionReportCatService";
    public static final String PM_POSITION_REPORT_SUB_CAT_SERVICE = "positionReportSubCatService";
    public static final String PM_PSTN_RPT_GRP_SUB_CAT_SERVICE = "pstnRptGrpSubCatService";
    public static final String PM_SALARY_GROUP_SERVICE = "salaryGroupService";
    public static final String PM_POSITION_TYPE_SERVICE = "positionTypeService";
    
    public static InstitutionService getInstitutionService() {
    	return (InstitutionService) CONTEXT.getBean(PM_INSTITUTION_SERVICE);
    }
    
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
    
	public static PayStepService getPayStepService() {
		return (PayStepService) CONTEXT.getBean(PM_PAY_STEP_SERVICE);
	}
	
	public static SalaryGroupService getSalaryGroupService() {
    	return (SalaryGroupService) CONTEXT.getBean(PM_SALARY_GROUP_SERVICE);
    }
	
	public static PositionTypeService getPositionTypeService() {
		return (PositionTypeService) CONTEXT.getBean(PM_POSITION_TYPE_SERVICE);
	}
	
	
	
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		CONTEXT = arg0;
	}
}
